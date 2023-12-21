package com.agb.billing.customer.viewholder

import android.view.View
import com.agb.billing.customer.databinding.ItemPhoneBinding
import com.agb.billing.customer.delegate.SupportDelegate

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