package com.agb.billing.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.agb.billing.customer.databinding.ItemCategoryBinding
import com.agb.billing.customer.delegate.CategoryDelegate
import com.agb.billing.customer.modelVO.CategoryVO
import com.agb.billing.customer.viewholder.BaseViewHolder
import com.agb.billing.customer.viewholder.CategoryViewHolder

class CategoryAdapter(delegate : CategoryDelegate) : BaseAdapter<CategoryViewHolder, CategoryVO>() {

    var mDelegate = delegate

    private var _binding : ItemCategoryBinding?= null
    private val binding get() = _binding!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CategoryVO> {
        _binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(binding,mDelegate)
    }

}