package com.agb.customer.billing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.agb.customer.billing.databinding.ItemChargesBinding
import com.agb.customer.billing.modelVO.ChargesVO
import com.agb.customer.billing.viewholder.BaseViewHolder
import com.agb.customer.billing.viewholder.ChargesViewHolder

class ChargesAdapter(start : String,end : String) : BaseAdapter<ChargesViewHolder, ChargesVO>() {

    private var _binding : ItemChargesBinding?= null
    private val binding get() = _binding!!
    val startDate = start
    val endDate = end

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ChargesVO> {
        _binding = ItemChargesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ChargesViewHolder(binding,startDate,endDate)
    }

}