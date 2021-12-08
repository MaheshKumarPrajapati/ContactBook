package com.example.contactbook.data.repositories.implementations

import androidx.lifecycle.LiveData
import com.example.contactbook.data.daos.ContactDao
import com.example.contactbook.data.entities.Contact
import java.util.*

class ContactRepository(private val contactDao: ContactDao) {

    suspend fun addContact(
        contactName: String,
        phoneNumber: String,
        birthday: Long,
        gender: String,
        email: String,
        type: String
    ): Boolean {
        if (findContact(contactName, phoneNumber) != null) {
            return false
        }
        val id = UUID.randomUUID().toString()
        contactDao.addContact(
            Contact(
                id,
                contactName,
                phoneNumber,
                birthday,
                gender,
                email,
                type
            )
        )
        return true
    }

    private fun findContact(name: String, phoneNumber: String): Contact? {
        return contactDao.findContact(name, phoneNumber)
    }

    fun getContacts(type: String): LiveData<List<Contact>> {
        return if(type.isNullOrEmpty()){contactDao.getContactsAll()}else{contactDao.getContacts(type)}
    }

    fun findContact(id: String): Contact? {
        return contactDao.findContact(id)
    }

    fun deleteContacts() {
        contactDao.deleteContacts()
    }

    fun deleteContact(id: String) {
        contactDao.deleteContact(id)
    }

    suspend fun editContact(contact: Contact) {
        contactDao.editContact(contact)
    }
}