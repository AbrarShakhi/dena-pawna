package com.abrarshakhi.denapawna.features.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abrarshakhi.denapawna.features.domain.model.Person

@Entity(tableName = "persons")
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    val personId: Long = 0L,
    val name: String,
    val phone: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
