package com.example.contactbook.ui.views.personal_contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactbook.R
import com.example.contactbook.databinding.FragmentContactsBinding
import com.example.contactbook.ui.viewModels.ContactsViewModel
import com.example.contactbook.ui.views.ContactsAdapter
import com.example.contactbook.ui.views.business_contacts.BusinessContactsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalFragment : Fragment() {
    private lateinit var binding: FragmentContactsBinding

    private val mContactViewModel: ContactsViewModel by viewModels()

    private lateinit var contactsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater, container, false)

        contactsRecyclerView = binding.contactListRecyclerView

        val adapter = ContactsAdapter { contact ->
            run {
                val action =
                    BusinessContactsFragmentDirections.actionContactsFragmentToContactDetailFragment(contact.id)
                findNavController().navigate(action)
            }
        }

        contactsRecyclerView.adapter = adapter
        contactsRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        binding.addContactBtn.setOnClickListener {
            findNavController().navigate(R.id.action_contactsFragment_to_addContactFragment)
        }

        mContactViewModel.getContacts(getString(R.string.personal_contacts))
            .observe(viewLifecycleOwner, { contacts ->
                adapter.setData(contacts)
            })

        //   setHasOptionsMenu(true)
        return binding.root
    }


}