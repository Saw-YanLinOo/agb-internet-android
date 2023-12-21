package com.agb.billing.customer.viewholder

import android.view.View
import com.agb.billing.customer.databinding.ItemComplainHistoryBinding
import com.agb.billing.customer.delegate.ComplainDelegate
import com.agb.billing.customer.modelVO.ComplainVO

class ComplainViewHolder(val binding: ItemComplainHistoryBinding, var mDelegate: ComplainDelegate) :
    BaseViewHolder<ComplainVO>(binding.root) {

    override fun setData(data: ComplainVO) {
        mData=data
        binding.apply {
            tvSubmitDate.text = data.complainDate
            tvActivePlan.text = data.activePlanText
            tvCategory.text = data.complainCategoryText
            tvMessage.text = data.complainMessage
            tvStatus.text = data.ticketStatusDesc
            tvReplyMessage.text = data.callCenterAnswer
            tvTicketNo.text = data.ticketNumber


        }
    }

    override fun onClick(v: View?) {
        mData?.let { mDelegate.onTapComplain(it) }
    }
}