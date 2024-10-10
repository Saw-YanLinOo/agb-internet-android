package com.agb.customer.billing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.agb.customer.billing.databinding.ItemActivePlanBinding
import com.agb.customer.billing.databinding.ItemComplainHistoryBinding
import com.agb.customer.billing.delegate.ActivePlanDelegate
import com.agb.customer.billing.delegate.ComplainDelegate
import com.agb.customer.billing.modelVO.ActivePlanVO
import com.agb.customer.billing.modelVO.ComplainVO
import com.agb.customer.billing.viewholder.ActivePlanViewHolder
import com.agb.customer.billing.viewholder.BaseViewHolder
import com.agb.customer.billing.viewholder.ComplainViewHolder

class ComplainAdapter(delegate : ComplainDelegate) : BaseAdapter<ActivePlanViewHolder, ComplainVO>() {

    var mDelegate = delegate
    private var _binding : ItemComplainHistoryBinding?= null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ComplainVO> {
        _binding = ItemComplainHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ComplainViewHolder(binding,mDelegate)
    }

}