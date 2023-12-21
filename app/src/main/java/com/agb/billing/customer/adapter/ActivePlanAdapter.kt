package com.agb.billing.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.agb.billing.customer.databinding.ItemActivePlanBinding
import com.agb.billing.customer.delegate.ActivePlanDelegate
import com.agb.billing.customer.modelVO.ActivePlanVO
import com.agb.billing.customer.viewholder.ActivePlanViewHolder
import com.agb.billing.customer.viewholder.BaseViewHolder

class ActivePlanAdapter(delegate : ActivePlanDelegate) : BaseAdapter<ActivePlanViewHolder, ActivePlanVO>() {

    var mDelegate = delegate

    private var _binding : ItemActivePlanBinding?= null
    private val binding get() = _binding!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ActivePlanVO> {
        _binding = ItemActivePlanBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ActivePlanViewHolder(binding,mDelegate)
    }

}