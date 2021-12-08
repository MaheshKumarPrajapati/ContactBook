package com.example.contactbook.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.contactbook.data.entities.Contact

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addContact(contact: Contact)

    @Query("SELECT * FROM contactsTable WHERE type = :type ORDER BY contactName")
    fun getContacts(type: String): LiveData<List<Contact>>

    @Query("SELECT * FROM contactsTable ORDER BY contactName")
    fun getContactsAll(): LiveData<List<Contact>>

    @Query("SELECT * FROM contactsTable WHERE id = :id")
    fun findContact(id: String): Contact?

    @Query("SELECT * FROM contactsTable WHERE contactName = :contactName and phoneNumber = :phoneNumber")
    fun findContact(contactName: String, phoneNumber: String): Contact?

    @Query("DELETE FROM contactsTable")
    fun deleteContacts()

    @Query("DELETE FROM contactsTable WHERE id = :id")
    fun deleteContact(id: String)

    @Update
    suspend fun editContact(contact: Contact)
}