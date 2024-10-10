package com.agb.customer.billing.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.agb.customer.billing.R
import com.agb.customer.billing.adapter.MyPlanVPAdapter
import com.agb.customer.billing.databinding.ActivityPlansBinding
import com.agb.customer.billing.utils.Constants.Companion.CURRENT_PLAN

class MyPlanActivity : BaseActivity() {

    lateinit var binding : ActivityPlansBinding
    companion object{
        fun newInstance(mContext : Context) : Intent{
            return Intent(mContext,MyPlanActivity::class.java)
        }
    }

    lateinit var mAdapter : MyPlanVPAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlansBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        clickEvent()
    }

    private fun initLayout() {
        mAdapter = MyPlanVPAdapter(supportFragmentManager,this)
        binding.apply {
            vpPlan.adapter = mAdapter
            tabPlan.setupWithViewPager(vpPlan)
        }

        binding.vpPlan.currentItem = CURRENT_PLAN
        CURRENT_PLAN = 0
    }

    private fun clickEvent() {
        binding.apply {
            ivBack.setOnClickListener { onBackPressed() }
        }
    }

    fun gotoActivePlan(){
        mAdapter.getActivePlanFragment().onRefreshList()
        binding.vpPlan.currentItem = 0
    }

    fun gotoPendingPlan(){
        mAdapter.getPendingPlanFragment().onrefreshList()
        binding.vpPlan.currentItem = 1
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.right_in, R.anim.right_out)
        finish()
    }

}