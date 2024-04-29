package com.agb.billing.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.agb.billing.customer.databinding.NotificationItemBinding
import com.agb.billing.customer.delegate.NotiItemDelegates
import com.agb.billing.customer.modelVO.NotificationVO
import com.agb.billing.customer.viewholder.BaseViewHolder
import com.agb.billing.customer.viewholder.NotificationItemViewHolder

class NotificationAdapter(var delegate: NotiItemDelegates) :
    BaseAdapter<NotificationItemViewHolder, NotificationVO>() {

    private var _binding: NotificationItemBinding? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<NotificationVO> {
        _binding =
            NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationItemViewHolder(_binding!!, delegate)
    }

}