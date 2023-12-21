package com.agb.billing.customer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.agb.billing.customer.R
import com.agb.billing.customer.databinding.CustomSpinnerItemBinding
import com.agb.billing.customer.databinding.ItemSpinnerPlanBinding
import com.agb.billing.customer.modelVO.ActivePlanVO
import com.agb.billing.customer.modelVO.BandWidthVO

class ActivePlanSpinnerAdapter : ArrayAdapter<ActivePlanVO> {

    var mContext: Context
    var mData: MutableList<ActivePlanVO> = mutableListOf()

    private var _binding : CustomSpinnerItemBinding ?= null
    private val binding get() = _binding!!

    constructor(context: Context, typeList: MutableList<ActivePlanVO>) : super(
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
        binding.text.text = mData[position].packagename

        return row
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        _binding = CustomSpinnerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val row = binding.root
        binding.text.setTextColor(Color.parseColor("#707070"))
        binding.text.text = mData[position].packagename

        return row

    }

}