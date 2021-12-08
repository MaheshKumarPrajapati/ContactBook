package com.example.contactbook.ui.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactbook.R
import com.example.contactbook.data.entities.Contact

class ContactsAdapter (private val onItemClicked: (Contact) -> Unit): RecyclerView.Adapter<ContactsAdapter.MyViewHolder>() {

    private var contactsList = emptyList<Contact>()

    interface OnItemClickListener{
        fun onClickListener(contact: Contact)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.contact_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = contactsList[position]
        with(holder){
            itemView.findViewById<TextView>(R.id.nameTV).text = currentItem.contactName
            itemView.findViewById<TextView>(R.id.phoneNumberTV).text = currentItem.phoneNumber
            itemView.setOnClickListener{
                onItemClicked(currentItem)
            }
        }
    }

    fun setData(contacts: List<Contact>) {
        this.contactsList = contacts
        notifyDataSetChanged()
    }
}