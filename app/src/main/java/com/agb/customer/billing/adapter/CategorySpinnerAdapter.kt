package com.agb.customer.billing.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.agb.customer.billing.R
import com.agb.customer.billing.databinding.CustomSpinnerItemBinding
import com.agb.customer.billing.databinding.ItemSpinnerPlanBinding
import com.agb.customer.billing.modelVO.ActivePlanVO
import com.agb.customer.billing.modelVO.BandWidthVO
import com.agb.customer.billing.modelVO.CategoryComplainVO
import com.agb.customer.billing.modelVO.CategoryVO

class CategorySpinnerAdapter : ArrayAdapter<CategoryComplainVO> {

    var mContext: Context
    var mData: MutableList<CategoryComplainVO> = mutableListOf()

    private var _binding : CustomSpinnerItemBinding ?= null
    private val binding get() = _binding!!

    constructor(context: Context, typeList: MutableList<CategoryComplainVO>) : super(
        context, R.layout.item_spinner_plan,
        typeList
    ) {
        this.mContext = context
        mData = typeList
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        _binding = CustomSpinnerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val row = binding.root
        binding.text.setTextColor(Color.parseColor("#000000"))
        binding.text.text = mData[position].categoryName

        return row
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        _binding = CustomSpinnerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val row = binding.root
        binding.text.setTextColor(Color.parseColor("#707070"))
        binding.text.text = mData[position].categoryName

        return row

    }

}