package com.agb.billing.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.agb.billing.customer.databinding.ItemPaymentViewHolderBinding
import com.agb.billing.customer.delegate.PaymentMethodItemDelegates
import com.agb.billing.customer.modelVO.PaymentVO
import com.agb.billing.customer.viewholder.BaseViewHolder
import com.agb.billing.customer.viewholder.PaymentMethodViewHolder

class PaymentMethodAdapter(var mDelegate : PaymentMethodItemDelegates) : BaseAdapter<PaymentMethodViewHolder, PaymentVO>() {

    private var _binding : ItemPaymentViewHolderBinding?= null
    private val binding get() = _binding!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<PaymentVO> {
        _binding = ItemPaymentViewHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PaymentMethodViewHolder(binding,mDelegate)
    }

}