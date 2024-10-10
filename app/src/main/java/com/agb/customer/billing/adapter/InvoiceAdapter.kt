package com.agb.customer.billing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.agb.customer.billing.databinding.ItemInvoicesBinding
import com.agb.customer.billing.delegate.InvoiceDelegate
import com.agb.customer.billing.modelVO.InvoiceVO
import com.agb.customer.billing.viewholder.BaseViewHolder
import com.agb.customer.billing.viewholder.InvoiceViewHolder

class InvoiceAdapter(delegate : InvoiceDelegate) : BaseAdapter<InvoiceViewHolder, InvoiceVO>() {

    var mDelegate = delegate

    private var _binding : ItemInvoicesBinding?= null
    private val binding get() = _binding!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<InvoiceVO> {
        _binding = ItemInvoicesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return InvoiceViewHolder(binding,mDelegate)
    }

}