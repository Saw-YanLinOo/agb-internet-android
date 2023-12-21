package com.agb.billing.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.agb.billing.customer.databinding.ItemActivePlanBinding
import com.agb.billing.customer.databinding.ItemComplainHistoryBinding
import com.agb.billing.customer.delegate.ActivePlanDelegate
import com.agb.billing.customer.delegate.ComplainDelegate
import com.agb.billing.customer.modelVO.ActivePlanVO
import com.agb.billing.customer.modelVO.ComplainVO
import com.agb.billing.customer.viewholder.ActivePlanViewHolder
import com.agb.billing.customer.viewholder.BaseViewHolder
import com.agb.billing.customer.viewholder.ComplainViewHolder

class ComplainAdapter(delegate : ComplainDelegate) : BaseAdapter<ActivePlanViewHolder, ComplainVO>() {

    var mDelegate = delegate
    private var _binding : ItemComplainHistoryBinding?= null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ComplainVO> {
        _binding = ItemComplainHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ComplainViewHolder(binding,mDelegate)
    }

}