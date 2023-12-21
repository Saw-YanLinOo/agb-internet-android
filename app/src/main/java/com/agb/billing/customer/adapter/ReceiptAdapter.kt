package com.agb.billing.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.agb.billing.customer.databinding.ItemReceiptBinding
import com.agb.billing.customer.delegate.ReceiptDelegate
import com.agb.billing.customer.modelVO.ReceiptVO
import com.agb.billing.customer.viewholder.BaseViewHolder
import com.agb.billing.customer.viewholder.ReceiptViewHolder

class ReceiptAdapter(delegate : ReceiptDelegate) : BaseAdapter<ReceiptViewHolder, ReceiptVO>() {

    var mDelegate = delegate

    private var _binding : ItemReceiptBinding?= null
    private val binding get() = _binding!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ReceiptVO> {
        _binding = ItemReceiptBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReceiptViewHolder(binding,mDelegate)
    }

}