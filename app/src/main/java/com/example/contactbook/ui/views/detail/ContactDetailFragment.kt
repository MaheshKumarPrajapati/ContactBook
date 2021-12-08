package com.example.contactbook.ui.views.detail

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.contactbook.R
import com.example.contactbook.data.entities.Contact
import com.example.contactbook.databinding.FragmentContactDetailBinding
import com.example.contactbook.ui.viewModels.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactDetailFragment : Fragment() {
    private lateinit var binding: FragmentContactDetailBinding
    private val args: ContactDetailFragmentArgs by navArgs()

    private val mContactViewModel: ContactViewModel by viewModels()
    private var contact: Contact? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        lifecycleScope.launch(Dispatchers.Main){
            contact = mContactViewModel.getContact(args.transferContactId)
            with(binding.contactDetailColumn){
                if(contact != null){
                    contactNameTV.text = "${getString(R.string.contact_name)} : ${contact!!.contactName}"
                    phoneNumberTV.text =  "${getString(R.string.phoneNumber)} : ${contact!!.phoneNumber}"
                    instagramTV.text = "${getString(R.string.email)} : ${contact!!.email}"
                    genderTV.text = "${getString(R.string.gender )} : ${contact!!.gender}"
                    if(contact!!.gender.equals(getString(R.string.male))){
                        binding.avatarIV.setImageResource(R.drawable.male)
                    }else if(contact!!.gender.equals(getString(R.string.female))){
                        binding.avatarIV.setImageResource(R.drawable.female)
                    }else{
                        binding.avatarIV.setImageResource(R.drawable.other)
                    }
                }
            }
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_contactdetail_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) {
            deleteContact()
        } else if (item.itemId == R.id.edit) {
            editContact()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun editContact() {
        val action =
            ContactDetailFragmentDirections.actionContactDetailFragmentToEditContactFragment(args.transferContactId)
        findNavController().navigate(action)
    }

    private fun deleteContact() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setPositiveButton(R.string.positiveAnswYes) { _, _ ->
            mContactViewModel.deleteContact(args.transferContactId)
            findNavController().navigate(R.id.action_contactDetailFragment_to_contactsFragment)
        }
        builder.setNegativeButton(R.string.negativeAnswNO) { _, _ ->
        }
        builder.setTitle(getString(R.string.delete) + " ${contact?.contactName}?")
        builder.setMessage(getString(R.string.deleteQstnContact) +" ${contact?.contactName}")
        builder.show()
    }
}