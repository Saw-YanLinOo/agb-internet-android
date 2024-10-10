package com.agb.customer.billing.viewholder

import android.view.View
import com.agb.customer.billing.databinding.ItemPhoneBinding
import com.agb.customer.billing.delegate.SupportDelegate

class PhoneViewHolder (val binding:ItemPhoneBinding, var mDelegate : SupportDelegate) : BaseViewHolder<String>(binding.root) {

    override fun setData(data: String) {
        binding.apply {
            tvPhone1.text = data
        }

        binding.root.setOnClickListener {
            mDelegate.onTapPhone(data)
        }
    }

    override fun onClick(v: View?) {

    }
}