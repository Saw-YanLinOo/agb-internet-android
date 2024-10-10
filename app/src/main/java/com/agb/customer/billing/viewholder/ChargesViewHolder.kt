package com.agb.customer.billing.viewholder

import android.view.View
import com.agb.customer.billing.databinding.ItemChargesBinding
import com.agb.customer.billing.modelVO.ChargesVO

class ChargesViewHolder (val binding:ItemChargesBinding,val startDate : String,val endDate : String) : BaseViewHolder<ChargesVO>(binding.root) {

    override fun setData(data: ChargesVO) {
        binding.apply {
//            if(adapterPosition == 0) {
//                tvDescription.text = "${data.invnumber}\n($startDate-$endDate)"
//
//            }else{
//                tvDescription.text = "${data.invnumber}"
//            }
            tvDescription.text = "${data.invnumber}"
            tvAmount.text = data.priceDesc
        }
    }

    override fun onClick(v: View?) {

    }
}