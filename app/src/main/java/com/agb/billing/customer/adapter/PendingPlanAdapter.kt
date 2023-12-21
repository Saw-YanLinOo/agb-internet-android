package com.agb.billing.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.agb.billing.customer.databinding.ItemPendingPlanBinding
import com.agb.billing.customer.delegate.PendingPlanDelegate
import com.agb.billing.customer.modelVO.PendingPlanVO
import com.agb.billing.customer.viewholder.BaseViewHolder
import com.agb.billing.customer.viewholder.PendingPlanViewHolder

class PendingPlanAdapter(delegate : PendingPlanDelegate) : BaseAdapter<PendingPlanViewHolder, PendingPlanVO>() {

    var mDelegate = delegate

    private var _binding : ItemPendingPlanBinding?= null
    private val binding get() = _binding!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<PendingPlanVO> {
        _binding = ItemPendingPlanBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingPlanViewHolder(binding,mDelegate)
    }

}