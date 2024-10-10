package com.agb.customer.billing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.agb.customer.billing.databinding.ItemPendingPlanBinding
import com.agb.customer.billing.delegate.PendingPlanDelegate
import com.agb.customer.billing.modelVO.PendingPlanVO
import com.agb.customer.billing.viewholder.BaseViewHolder
import com.agb.customer.billing.viewholder.PendingPlanViewHolder

class PendingPlanAdapter(delegate : PendingPlanDelegate) : BaseAdapter<PendingPlanViewHolder, PendingPlanVO>() {

    var mDelegate = delegate

    private var _binding : ItemPendingPlanBinding?= null
    private val binding get() = _binding!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<PendingPlanVO> {
        _binding = ItemPendingPlanBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingPlanViewHolder(binding,mDelegate)
    }

}