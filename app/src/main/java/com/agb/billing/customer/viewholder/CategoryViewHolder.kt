package com.agb.billing.customer.viewholder

import android.view.View
import com.agb.billing.customer.databinding.ItemCategoryBinding
import com.agb.billing.customer.delegate.CategoryDelegate
import com.agb.billing.customer.modelVO.CategoryVO

class CategoryViewHolder (val binding:ItemCategoryBinding, var mDelegate : CategoryDelegate) : BaseViewHolder<CategoryVO>(binding.root) {

    override fun setData(data: CategoryVO) {
        binding.apply {
            ivItemCategory.setImageResource(data.image)
            tvItemCategory.text = data.title
        }

        binding.root.setOnClickListener {
            mDelegate.onTapCategory(data)
        }
    }

    override fun onClick(v: View?) {

    }
}