package com.example.contactbook.data.services.abstractions

interface IHashService {

    fun getSha256Hash(inputString : String): String

    fun toHex(byteArray : ByteArray): String
}