package com.example.contactbook.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactbook.data.entities.Contact
import com.example.contactbook.data.repositories.implementations.ContactRepository
import com.example.contactbook.data.services.InputValidationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactRepository: ContactRepository
) : ViewModel() {

    fun getContacts(type:String): LiveData<List<Contact>> {
        return contactRepository.getContacts(type)
    }

    fun deleteContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepository.deleteContacts()
        }
    }
    suspend fun addExternalContact(name: String, phone: String, ownerId: String): Boolean =
        withContext(Dispatchers.IO) {
            contactRepository.addContact(name, phone, 0, "Others", "","")
        }

}