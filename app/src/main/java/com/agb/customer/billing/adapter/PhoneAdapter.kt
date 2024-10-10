package com.agb.customer.billing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.agb.customer.billing.databinding.ItemPhoneBinding
import com.agb.customer.billing.delegate.SupportDelegate
import com.agb.customer.billing.viewholder.BaseViewHolder
import com.agb.customer.billing.viewholder.PhoneViewHolder

class PhoneAdapter(delegate : SupportDelegate) : BaseAdapter<PhoneViewHolder, String>() {

    var mDelegate = delegate

    private var _binding : ItemPhoneBinding?= null
    private val binding get() = _binding!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {
        _binding = ItemPhoneBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PhoneViewHolder(binding,mDelegate)
    }

}