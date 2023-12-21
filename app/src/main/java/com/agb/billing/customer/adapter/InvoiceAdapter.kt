package com.agb.billing.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.agb.billing.customer.databinding.ItemInvoicesBinding
import com.agb.billing.customer.delegate.InvoiceDelegate
import com.agb.billing.customer.modelVO.InvoiceVO
import com.agb.billing.customer.viewholder.BaseViewHolder
import com.agb.billing.customer.viewholder.InvoiceViewHolder

class InvoiceAdapter(delegate : InvoiceDelegate) : BaseAdapter<InvoiceViewHolder, InvoiceVO>() {

    var mDelegate = delegate

    private var _binding : ItemInvoicesBinding?= null
    private val binding get() = _binding!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<InvoiceVO> {
        _binding = ItemInvoicesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return InvoiceViewHolder(binding,mDelegate)
    }

}