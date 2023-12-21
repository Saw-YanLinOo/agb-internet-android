package com.agb.billing.customer.viewholder

import android.view.View
import com.agb.billing.customer.databinding.ItemReceiptBinding
import com.agb.billing.customer.delegate.ReceiptDelegate
import com.agb.billing.customer.modelVO.ReceiptVO

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