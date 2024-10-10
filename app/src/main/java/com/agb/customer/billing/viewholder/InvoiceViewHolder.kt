package com.agb.customer.billing.viewholder

import android.view.View
import com.agb.customer.billing.R
import com.agb.customer.billing.databinding.ItemInvoicesBinding
import com.agb.customer.billing.delegate.InvoiceDelegate
import com.agb.customer.billing.modelVO.InvoiceVO

class InvoiceViewHolder(val binding: ItemInvoicesBinding, var mDelegate: InvoiceDelegate) :
    BaseViewHolder<InvoiceVO>(binding.root) {

    override fun setData(data: InvoiceVO) {
        binding.apply {
            tvPlanNo.text = data.invnumber
            tvPlanDate.text = "${root.resources.getString(R.string.lbl_due)}${data.expirationdate}"
            tvPlanInvoice.text = data.specialcode
            tvPlanAmount.text = data.totalcost
            tvPlanPrice.text = data.totalcost

            if (data.paid != 0) {
//                tvUnpaid.setBackgroundResource(R.drawable.bg_paid)
                tvUnpaid.visibility = View.GONE
                tvPaid.visibility = View.VISIBLE
                tvPaid.text = data.paidDesc
            } else {
//                tvUnpaid.setBackgroundResource(R.drawable.bg_unpaid)
                tvUnpaid.visibility = View.VISIBLE
                tvPaid.visibility = View.GONE
                tvUnpaid.text = data.paidDesc
            }
        }

        binding.root.setOnClickListener {
            mDelegate.onTapInvoice(data)
        }
    }

    override fun onClick(v: View?) {

    }
}