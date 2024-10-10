package com.agb.customer.billing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.agb.customer.billing.databinding.ItemPaymentViewHolderBinding
import com.agb.customer.billing.delegate.PaymentMethodItemDelegates
import com.agb.customer.billing.modelVO.PaymentVO
import com.agb.customer.billing.viewholder.BaseViewHolder
import com.agb.customer.billing.viewholder.PaymentMethodViewHolder

class PaymentMethodAdapter(var mDelegate : PaymentMethodItemDelegates) : BaseAdapter<PaymentMethodViewHolder, PaymentVO>() {

    private var _binding : ItemPaymentViewHolderBinding?= null
    private val binding get() = _binding!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<PaymentVO> {
        _binding = ItemPaymentViewHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PaymentMethodViewHolder(binding,mDelegate)
    }

}