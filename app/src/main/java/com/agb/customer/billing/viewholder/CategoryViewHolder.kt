package com.agb.customer.billing.viewholder

import android.view.View
import com.agb.customer.billing.databinding.ItemCategoryBinding
import com.agb.customer.billing.delegate.CategoryDelegate
import com.agb.customer.billing.modelVO.CategoryVO

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