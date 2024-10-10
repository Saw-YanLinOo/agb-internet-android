package com.agb.customer.billing.viewholder

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import com.agb.customer.billing.R
import com.agb.customer.billing.databinding.ItemPaymentViewHolderBinding
import com.agb.customer.billing.delegate.PaymentMethodItemDelegates
import com.agb.customer.billing.modelVO.PaymentVO
import com.bumptech.glide.Glide

class PaymentMethodViewHolder (val binding:ItemPaymentViewHolderBinding, var mDelegate : PaymentMethodItemDelegates) : BaseViewHolder<PaymentVO>(binding.root) {

    override fun setData(data: PaymentVO) {
        binding.apply {
            tvPaymentName.text = data.name
            cvPay.setOnClickListener {
                data.isChecked=true
                mDelegate.onTapPaymentItem(data)  }

            if (data.isChecked)
                cvPay.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.shimmerColor
                    )
                )

            else
                cvPay.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )

            Glide.with(binding.root)
                .load(data.iconImage)
                .into(ivLogo)
        }


    }

    override fun onClick(v: View?) {

    }
}