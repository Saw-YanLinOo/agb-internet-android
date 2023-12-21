package com.agb.billing.customer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.agb.billing.customer.R
import com.agb.billing.customer.databinding.ItemSpinnerPlanBinding
import com.agb.billing.customer.modelVO.PlanListVO

class PayPerSpinnerAdapter : ArrayAdapter<PlanListVO> {

    var mContext: Context
    var mData: MutableList<PlanListVO> = mutableListOf()

    private var _binding : ItemSpinnerPlanBinding ?= null
    private val binding get() = _binding!!

    constructor(context: Context, typeList: MutableList<PlanListVO>) : super(
        context, R.layout.item_spinner_plan,
        typeList
    ) {
        this.mContext = context
        mData = typeList
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        _binding = ItemSpinnerPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val row = binding.root
        binding.txtData.setTextColor(Color.parseColor("#000000"))
        binding.txtData.text = mData[position].duration

        return row
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        _binding = ItemSpinnerPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val row = binding.root
        binding.txtData.setTextColor(Color.parseColor("#707070"))
        binding.txtData.text = mData[position].duration

        return row

    }
//    fun getInvoiceType(pos : Int): Int{
//        return mData[pos].value!!
//    }

    fun getPlanVO(pos : Int):PlanListVO{
        return mData[pos]
    }
}