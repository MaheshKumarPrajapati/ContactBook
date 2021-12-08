package com.example.contactbook.ui.views.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.contactbook.R
import com.example.contactbook.databinding.FragmentEditContactBinding

import com.example.contactbook.ui.viewModels.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.contactbook.ui.views.detail.ContactDetailFragmentArgs
import com.hbb20.CountryCodePicker

@AndroidEntryPoint
class EditContactFragment : Fragment() {
    private lateinit var binding: FragmentEditContactBinding
    private val args: ContactDetailFragmentArgs by navArgs()

    private val mContactViewModel: ContactViewModel by viewModels()

    private var inputValidationFlags: Array<Boolean> = Array(2) { true }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditContactBinding.inflate(inflater, container, false)

        lifecycleScope.launch(Dispatchers.Main) {
            val contact = mContactViewModel.getContact(args.transferContactId)
            with(binding) {
                nameET.setText(contact.contactName)
                phoneNumberET.setText(contact.phoneNumber)
                emailET.setText(contact.email)
                if(contact.gender.equals( getString(R.string.male),true)){
                    binding.maleRB.isChecked=true
                    binding.othersRB.isChecked=false
                    binding.femaleRB.isChecked=false
                }else if(contact.gender.equals(getString(R.string.female),true)){
                    binding.femaleRB.isChecked=true
                    binding.othersRB.isChecked=false
                    binding.maleRB.isChecked=false
                }else{
                    binding.othersRB.isChecked=true
                    binding.maleRB.isChecked=false
                    binding.femaleRB.isChecked=false
                }

                if(contact.type.equals( getString(R.string.business_contacts),true)){
                    binding.Business.isChecked=true
                    binding.Personal.isChecked=false
                }else {
                    binding.Personal.isChecked=true
                    binding.Business.isChecked=false
                }
            }
        }

        binding.editBtn.setOnClickListener {
            editContact()
        }

        return binding.root
    }

    private suspend fun getGender(): String {
        var gender = when (binding.genderRG.checkedRadioButtonId) {
            binding.maleRB.id -> {
                getString(R.string.male)
            }
            binding.femaleRB.id -> {
                getString(R.string.female)
            }
            else -> "Others"
        }
        return gender
    }

    private suspend fun getType(): String {
        var type = when (binding.contactType.checkedRadioButtonId) {
            binding.Business.id -> {
                getString(R.string.business_contacts)
            }
            binding.Personal.id -> {
                getString(R.string.personal_contacts)
            }
            else -> getString(R.string.personal_contacts)
        }
        return type
    }

    private fun changeLayoutValidity() {
        with(binding) {
            if (!inputValidationFlags[0]) {
                nameError.isErrorEnabled = true
                nameError.error = getString(R.string.contactNameError)
            } else {
                nameError.isErrorEnabled = false
                nameError.error = null
            }
            if (!inputValidationFlags[1]) {
                phoneNumberError.isErrorEnabled = true
                phoneNumberError.error = getString(R.string.contactPhoneNumberError)
            } else {
                phoneNumberError.isErrorEnabled = false
                phoneNumberError.error = null
            }
        }
    }

    private fun editContact() {
        with(binding) {
            inputValidationFlags = mContactViewModel.checkInputValidation(
                nameET.text.toString(),
                phoneNumberET.text.toString()
            )

            lifecycleScope.launch(Dispatchers.Main) {
                val contact = mContactViewModel.getContact(args.transferContactId)
                    contact.contactName = nameET.text.toString()
                    contact.email = emailET.text.toString()
                    contact.phoneNumber = "${ccp.selectedCountryCodeWithPlus}-${phoneNumberET.text.toString()}"
                    contact.gender = getGender()
                    contact.type = getType()
                    if (!inputValidationFlags.contains(false)) {
                        mContactViewModel.editContact(
                            contact
                        )
                        val action =
                            EditContactFragmentDirections.actionEditContactFragmentToContactDetailFragment(
                                args.transferContactId
                            )
                        findNavController().navigate(action)
                    } else {
                        changeLayoutValidity()
                    }
            }
        }
    }
}