package com.agb.billing.customer.viewholder

import android.view.View
import com.agb.billing.customer.R
import com.agb.billing.customer.databinding.NotificationItemBinding
import com.agb.billing.customer.delegate.NotiItemDelegates
import com.agb.billing.customer.modelVO.NotificationVO
import com.bumptech.glide.Glide

class NotificationItemViewHolder(val binding: NotificationItemBinding, var delegate: NotiItemDelegates) :
    BaseViewHolder<NotificationVO>(binding.root) {

    override fun setData(data: NotificationVO) {
        mData = data
        binding.apply {

            tvTitle.text = data.title
            tvDate.text = data.createdTime
            tvDescription.text = data.description
            Glide.with(binding.root)
                .load(data.notiTypeImage)
                .placeholder(R.drawable.ic_component_wifi)
                .error(R.drawable.ic_component_wifi)
                .circleCrop()
                .into(binding.ivNoti)
        }

    }

    override fun onClick(v: View?) {
        mData?.let { delegate.onTapItem(it) }
    }
}