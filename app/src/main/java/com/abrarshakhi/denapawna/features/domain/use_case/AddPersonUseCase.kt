package com.abrarshakhi.denapawna.features.domain.use_case

import com.abrarshakhi.denapawna.core.utils.Outcome
import com.abrarshakhi.denapawna.features.domain.repository.PersonRepository

class AddPersonUseCase(
    private val repository: PersonRepository
) {
    suspend fun addPerson(
        fullName: String, phone: String
    ): Outcome<Unit, Throwable> {

        val name = fullName.trim()
        val cleanedPhone = phone.trim()

        if (name.length < 4) {
            return Outcome.err(IllegalArgumentException("Full name must be at least 4 characters long"))
        }

        val validPhone = cleanedPhone.takeIf {
            it.isValidPhoneNumber()
        }

        if (cleanedPhone.isNotBlank() && validPhone == null) {
            return Outcome.err(IllegalArgumentException("Invalid phone number"))
        }

        return repository.addPerson(
            fullName = name, phone = validPhone
        )
    }
}

private fun String.isValidPhoneNumber(): Boolean {
    val phoneRegex = Regex("^\\+?[0-9]{10,15}$")
    return matches(phoneRegex)
}
