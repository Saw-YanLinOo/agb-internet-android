package com.agb.customer.billing.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.agb.customer.billing.R
import com.agb.customer.billing.fragments.ActivePlanFragment
import com.agb.customer.billing.fragments.PendingPlanFragment

class MyPlanVPAdapter(
    childFragmentManager: FragmentManager,context : Context
) :
    FragmentStatePagerAdapter(childFragmentManager) {

    var activefg : ActivePlanFragment ?= null
    var pendingfg : PendingPlanFragment ?= null
    var mContext = context

    init {
        activefg = ActivePlanFragment()
        pendingfg = PendingPlanFragment()
    }

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> activefg!!
            else -> return pendingfg!!
        }

    }

    override fun getPageTitle(position: Int): CharSequence? {

        return when (position) {
            0 -> {
                mContext.resources.getString(R.string.title_active_plans)
            }
            else ->{
                mContext.resources.getString(R.string.title_pending_plans)
            }
        }

    }

    override fun getCount(): Int {
        return 2
    }

    fun getActivePlanFragment() : ActivePlanFragment{
        return if(activefg != null){
            activefg!!
        }else{
            ActivePlanFragment()
        }
    }

    fun getPendingPlanFragment() : PendingPlanFragment{
        return if(pendingfg != null){
            pendingfg!!
        }else{
            PendingPlanFragment()
        }
    }

}