package com.abrarshakhi.denapawna.features.data.auth

import android.content.Context
import com.abrarshakhi.denapawna.core.utils.Outcome
import com.abrarshakhi.denapawna.features.domain.model.AuthError
import com.abrarshakhi.denapawna.features.domain.model.AuthUser
import com.abrarshakhi.denapawna.features.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.UUID

class TestAuthRepository(context: Context) : AuthRepository {

    private val prefs = context.getSharedPreferences("test_auth", Context.MODE_PRIVATE)

    // email (lowercase) -> (password, user)
    private val store = mutableMapOf<String, Pair<String, AuthUser>>()

    private val _currentUser: MutableStateFlow<AuthUser?>

    init {
        loadStore()
        val userJson = prefs.getString(KEY_CURRENT_USER, null)
        _currentUser = MutableStateFlow(userJson?.let { runCatching { Json.decodeFromString<AuthUser>(it) }.getOrNull() })
    }

    override fun getCurrentUser(): StateFlow<AuthUser?> = _currentUser.asStateFlow()

    override suspend fun login(email: String, password: String): Outcome<AuthUser, AuthError> {
        val entry = store[email.lowercase()] ?: return Outcome.err(AuthError.InvalidCredentials)
        if (entry.first != password) return Outcome.err(AuthError.InvalidCredentials)
        setCurrentUser(entry.second)
        return Outcome.ok(entry.second)
    }

    override suspend fun register(
        email: String,
        password: String,
        displayName: String,
    ): Outcome<AuthUser, AuthError> {
        val key = email.lowercase()
        if (store.containsKey(key)) return Outcome.err(AuthError.UserAlreadyExists(email))
        val user = AuthUser(id = UUID.randomUUID().toString(), displayName = displayName, email = email)
        store[key] = Pair(password, user)
        saveStore()
        setCurrentUser(user)
        return Outcome.ok(user)
    }

    override suspend fun logout(): Outcome<Unit, AuthError> {
        setCurrentUser(null)
        return Outcome.ok(Unit)
    }

    private fun setCurrentUser(user: AuthUser?) {
        _currentUser.value = user
        prefs.edit().apply {
            if (user != null) putString(KEY_CURRENT_USER, Json.encodeToString(user))
            else remove(KEY_CURRENT_USER)
            apply()
        }
    }

    private fun saveStore() {
        val entries = store.map { (_, pair) -> StoredEntry(pair.first, pair.second) }
        prefs.edit().putString(KEY_STORE, Json.encodeToString(entries)).apply()
    }

    private fun loadStore() {
        val json = prefs.getString(KEY_STORE, null) ?: return
        runCatching { Json.decodeFromString<List<StoredEntry>>(json) }.getOrNull()
            ?.forEach { entry -> store[entry.user.email.lowercase()] = Pair(entry.password, entry.user) }
    }

    @Serializable
    private data class StoredEntry(val password: String, val user: AuthUser)

    private companion object {
        const val KEY_CURRENT_USER = "current_user"
        const val KEY_STORE = "store"
    }
}
