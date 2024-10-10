package com.agb.customer.billing.viewholder

import android.view.View
import com.agb.customer.billing.R
import com.agb.customer.billing.databinding.ItemActivePlanBinding
import com.agb.customer.billing.delegate.ActivePlanDelegate
import com.agb.customer.billing.modelVO.ActivePlanVO

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