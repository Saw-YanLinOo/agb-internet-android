package com.agb.billing.customer.viewholder

import android.view.View
import com.agb.billing.customer.R
import com.agb.billing.customer.databinding.ItemActivePlanBinding
import com.agb.billing.customer.delegate.ActivePlanDelegate
import com.agb.billing.customer.modelVO.ActivePlanVO

class ActivePlanViewHolder (val binding:ItemActivePlanBinding, var mDelegate : ActivePlanDelegate) : BaseViewHolder<ActivePlanVO>(binding.root) {

    override fun setData(data: ActivePlanVO) {
        binding.apply {
            tvPlanInvoice.text = data.specialcode
            tvPlanPackage.text = data.packagename
            tvPlanSpeed.text = data.bandwidth
            tvPlanPrice.text = data.totalpriceDesc
            tvPlanDate.text = "${root.resources.getString(R.string.lbl_till)}${data.srvexpirationdate}"

            tvChangePlan.setOnClickListener {
                mDelegate.onTapChangePlan(data)
            }
        }
    }

    override fun onClick(v: View?) {

    }
}