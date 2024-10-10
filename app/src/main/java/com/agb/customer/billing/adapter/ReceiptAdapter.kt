package com.agb.customer.billing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.agb.customer.billing.databinding.ItemReceiptBinding
import com.agb.customer.billing.delegate.ReceiptDelegate
import com.agb.customer.billing.modelVO.ReceiptVO
import com.agb.customer.billing.viewholder.BaseViewHolder
import com.agb.customer.billing.viewholder.ReceiptViewHolder

class ReceiptAdapter(delegate : ReceiptDelegate) : BaseAdapter<ReceiptViewHolder, ReceiptVO>() {

    var mDelegate = delegate

    private var _binding : ItemReceiptBinding?= null
    private val binding get() = _binding!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ReceiptVO> {
        _binding = ItemReceiptBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReceiptViewHolder(binding,mDelegate)
    }

}