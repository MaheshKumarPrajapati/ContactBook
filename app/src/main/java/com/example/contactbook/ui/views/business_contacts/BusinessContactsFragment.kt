package com.example.contactbook.ui.views.business_contacts

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactbook.R
import com.example.contactbook.databinding.FragmentContactsBinding
import com.example.contactbook.ui.viewModels.ContactsViewModel
import com.example.contactbook.ui.views.ContactsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BusinessContactsFragment : Fragment() {
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

        mContactViewModel.getContacts(getString(R.string.business_contacts))
            .observe(viewLifecycleOwner, { contacts ->
                adapter.setData(contacts)
            })

     //   setHasOptionsMenu(true)
        return binding.root
    }

  /*  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_contacts_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) {
            deleteContacts()
        } else if (item.itemId == R.id.addExternalContactBtn) {
            addExternalContact()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addExternalContact() {
        if (checkContactPermission()) {
            getExternalContact()
        } else {
            requestContactPermission()
        }
    }

    private fun deleteContacts() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setPositiveButton(getString(R.string.positiveAnswYes)) { _, _ ->
            mContactViewModel.deleteContacts()
        }
        builder.setNegativeButton(getString(R.string.negativeAnswNO)) { _, _ ->
        }
        builder.setTitle(getString(R.string.deleteEverything))
        builder.setMessage(getString(R.string.deletingContactsMessage))
        builder.show()
    }

    private fun getExternalContact() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, CONTACT_PICK_CODE)
    }

    private fun checkContactPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            android.Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestContactPermission() {
        val permission = arrayOf(android.Manifest.permission.READ_CONTACTS)
        ActivityCompat.requestPermissions(requireActivity(), permission, CONTACT_PERMISSION_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CONTACT_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getExternalContact()
            } else {
                Toast.makeText(requireContext(), "Denied...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("Recycle", "Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == CONTACT_PICK_CODE) {
                val contactCursor: Cursor
                val contactPhonesCursor: Cursor?

                val uri = data!!.data

                contactCursor =
                    requireActivity().contentResolver.query(uri!!, null, null, null, null)!!

                if (contactCursor.moveToFirst()) {
                    val contactId =
                        contactCursor.getString((contactCursor.getColumnIndex(ContactsContract.Contacts._ID)))
                    val contactName =
                        contactCursor.getString((contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)))
                    val isPhoneExist =
                        contactCursor.getString((contactCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))

                    if (isPhoneExist.toInt() == 1) {
                        contactPhonesCursor = requireActivity().contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                            null,
                            null
                        )
                        if (contactPhonesCursor!!.moveToFirst()) {
                            val contactPhone =
                                contactPhonesCursor.getString(
                                    (contactPhonesCursor.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER
                                    ))
                                )
                            lifecycleScope.launch(Dispatchers.Main){
                                if (!mContactViewModel.addExternalContact(
                                        contactName,
                                        contactPhone,""
                                    )
                                ) {
                                    Toast.makeText(
                                        requireContext(),
                                        getString(R.string.ContactExistsToast),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        contactPhonesCursor.close()
                    }
                    contactCursor.close()
                }
            }
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.addExternalContactCanceledToast),
                Toast.LENGTH_SHORT
            ).show()
        }
    }*/
}