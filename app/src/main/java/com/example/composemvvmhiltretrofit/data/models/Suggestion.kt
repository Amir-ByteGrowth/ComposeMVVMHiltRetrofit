package com.example.composemvvmhiltretrofit.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suggestions")
data class Suggestion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val handle: String,
)