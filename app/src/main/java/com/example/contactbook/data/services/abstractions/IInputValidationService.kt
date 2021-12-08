package com.example.contactbook.data.services.abstractions

interface IInputValidationService {

    fun loginInputValidation(
        email: String, password: String,
    ): Array<Boolean>

    fun addContactInputValidation(
        name : String, phoneNumber : String
    ): Array<Boolean>

    fun registerInputValidation(
        name: String, lastName: String,
        email: String, password: String,
        confirmedPassword : String
    ): Array<Boolean>
}