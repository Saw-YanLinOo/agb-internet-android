package com.agb.customer.billing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.agb.customer.billing.databinding.ItemActivePlanBinding
import com.agb.customer.billing.delegate.ActivePlanDelegate
import com.agb.customer.billing.modelVO.ActivePlanVO
import com.agb.customer.billing.viewholder.ActivePlanViewHolder
import com.agb.customer.billing.viewholder.BaseViewHolder

class ActivePlanAdapter(delegate : ActivePlanDelegate) : BaseAdapter<ActivePlanViewHolder, ActivePlanVO>() {

    var mDelegate = delegate

    private var _binding : ItemActivePlanBinding?= null
    private val binding get() = _binding!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ActivePlanVO> {
        _binding = ItemActivePlanBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ActivePlanViewHolder(binding,mDelegate)
    }

}