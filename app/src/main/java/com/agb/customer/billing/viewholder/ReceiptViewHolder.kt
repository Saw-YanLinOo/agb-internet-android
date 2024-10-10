package com.agb.customer.billing.viewholder

import android.view.View
import com.agb.customer.billing.databinding.ItemReceiptBinding
import com.agb.customer.billing.delegate.ReceiptDelegate
import com.agb.customer.billing.modelVO.ReceiptVO

class ReceiptViewHolder (val binding:ItemReceiptBinding, var mDelegate : ReceiptDelegate) : BaseViewHolder<ReceiptVO>(binding.root) {

    override fun setData(data: ReceiptVO) {
        binding.apply {
            tvRecordNo.text = data.invnumber
            tvDate.text = "${data.paidDesc} : ${data.paiddate}"
            tvServiceNo.text = data.specialcode
            tvAmount.text = data.totalcost
        }

        binding.root.setOnClickListener {
            mDelegate.onTapReceipt(data)
        }
    }

    override fun onClick(v: View?) {

    }
}