package com.agb.customer.billing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.agb.customer.billing.databinding.NotificationItemBinding
import com.agb.customer.billing.delegate.NotiItemDelegates
import com.agb.customer.billing.modelVO.NotificationVO
import com.agb.customer.billing.viewholder.BaseViewHolder
import com.agb.customer.billing.viewholder.NotificationItemViewHolder

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