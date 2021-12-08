package com.example.contactbook.data.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "contactsTable",
)
data class Contact (
    @PrimaryKey
    @NonNull
    var id : String,

    var contactName : String,

    var phoneNumber : String,

    var birthday : Long,

    var gender : String,

    var email : String,

    var type : String
)