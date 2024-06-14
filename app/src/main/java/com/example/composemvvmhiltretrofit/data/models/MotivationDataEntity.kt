package com.example.composemvvmhiltretrofit.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "motiviation_table")
data class MotivationDataEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "category") val category: String = "",
    @ColumnInfo(name = "text") val text: String = "",
)