package com.agb.billing.customer.viewholder

import android.view.View
import com.agb.billing.customer.R
import com.agb.billing.customer.databinding.ItemPendingPlanBinding
import com.agb.billing.customer.delegate.PendingPlanDelegate
import com.agb.billing.customer.modelVO.PendingPlanVO

class PendingPlanViewHolder (val binding:ItemPendingPlanBinding, var mDelegate : PendingPlanDelegate) : BaseViewHolder<PendingPlanVO>(binding.root) {

    override fun setData(data: PendingPlanVO) {
        binding.apply {
            tvPlanInvoice.text = data.specialcode
            tvPlanPackage.text = data.packagename
            tvPlanSpeed.text = data.bandwidth
            tvPlanPrice.text = data.totalpriceDesc
            tvPlanDate.text = "${root.resources.getString(R.string.lbl_till)}${data.pendingrequestdate}"

            tvUndoChanges.setOnClickListener {
                mDelegate.onTapUndoPlan(data)
            }
        }
    }

    override fun onClick(v: View?) {

    }
}