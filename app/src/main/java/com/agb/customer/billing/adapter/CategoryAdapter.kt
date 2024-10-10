package com.agb.customer.billing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.agb.customer.billing.databinding.ItemCategoryBinding
import com.agb.customer.billing.delegate.CategoryDelegate
import com.agb.customer.billing.modelVO.CategoryVO
import com.agb.customer.billing.viewholder.BaseViewHolder
import com.agb.customer.billing.viewholder.CategoryViewHolder

class CategoryAdapter(delegate : CategoryDelegate) : BaseAdapter<CategoryViewHolder, CategoryVO>() {

    var mDelegate = delegate

    private var _binding : ItemCategoryBinding?= null
    private val binding get() = _binding!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CategoryVO> {
        _binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(binding,mDelegate)
    }

}