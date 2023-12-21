package com.agb.billing.customer.utils

import android.content.Context
import com.agb.billing.customer.R
import com.agb.billing.customer.modelVO.*

class SampleData {
    companion object{
//        fun bannerList() : MutableList<BannerVO>{
//            val list = mutableListOf<BannerVO>()
//            val item1 = BannerVO()
//            item1.id = 1
//            item1.image = R.drawable.sample_banner
//
//            val item2 = BannerVO()
//            item2.id = 2
//            item2.image = R.drawable.sample_banner
//
//            list.add(item1)
//            list.add(item2)
//
//            return list
//        }

        fun categoryList(mContext : Context) : MutableList<CategoryVO>{
            val list = mutableListOf<CategoryVO>()
            val item1 = CategoryVO()
            item1.id = 1
            item1.image = R.drawable.ic_plans
            item1.title = mContext.resources.getString(R.string.title_my_plans)

            val item2 = CategoryVO()
            item2.id = 2
            item2.image = R.drawable.ic_invoice
            item2.title = mContext.resources.getString(R.string.title_invoices)

            val item3 = CategoryVO()
            item3.id = 3
            item3.image = R.drawable.ic_receipt
            item3.title = mContext.resources.getString(R.string.title_receipts)

            val item4 = CategoryVO()
            item4.id = 4
            item4.image = R.drawable.ic_profile
            item4.title = mContext.resources.getString(R.string.title_profile)

            val item5 = CategoryVO()
            item5.id = 5
            item5.image = R.drawable.ic_term_and_condition
            item5.title = mContext.resources.getString(R.string.title_t_c)

            val item6 = CategoryVO()
            item6.id = 6
            item6.image = R.drawable.ic_support
            item6.title = mContext.resources.getString(R.string.title_contact)

            val item7 = CategoryVO()
            item7.id = 7
            item7.image = R.drawable.ic_home_complain
            item7.title = mContext.resources.getString(R.string.title_support)

            val item8 = CategoryVO()
            item8.id = 8
            item8.image = R.drawable.ic_logout
            item8.title = mContext.resources.getString(R.string.title_logout)

            list.add(item1)
            list.add(item2)
            list.add(item3)
            list.add(item4)
            list.add(item5)
            list.add(item6)
            list.add(item7)
            list.add(item8)

            return list
        }


        fun payPerList() : MutableList<PayPerVO>{
            val list = mutableListOf<PayPerVO>()
            val item1 = PayPerVO()
            item1.id = 1
            item1.name = "1 month"

            val item2 = PayPerVO()
            item2.id = 2
            item2.name = "2 month"

            val item3 = PayPerVO()
            item3.id = 3
            item3.name = "3 month"

            list.add(item1)
            list.add(item2)
            list.add(item3)

            return list
        }

//        fun invoiceList() : MutableList<InvoiceVO>{
//            val list = mutableListOf<InvoiceVO>()
//
//            val item1 = InvoiceVO()
//            item1.id = 1
//            item1.invoice = "SS-0001"
//            item1.plan_no = "202109000001"
//            item1.amount = "37,500 MMK"
//            item1.date = "12 Desc 2021"
//
//            val item2 = InvoiceVO()
//            item2.id = 1
//            item2.invoice = "SS-0002"
//            item2.plan_no = "202109000001"
//            item2.amount = "100,500 MMK"
//            item2.date = "12 Desc 2021"
//
//            list.add(item1)
//            list.add(item2)
//            list.add(item1)
//            list.add(item2)
//
//            return list
//        }

//        fun receiptList() : MutableList<ReceiptVO>{
//            val list = mutableListOf<ReceiptVO>()
//
//            val item1 = ReceiptVO()
//            item1.id = 1
//            item1.amount = "100,000 MMK"
//            item1.record_no = "REC-0001"
//            item1.service_no = "SS-0001"
//            item1.date = "12 Dec 2021"
//
//            val item2 = ReceiptVO()
//            item2.id = 1
//            item2.amount = "100,000 MMK"
//            item2.record_no = "REC-0002"
//            item2.service_no = "SS-0002"
//            item2.date = "12 Dec 2021"
//
//            list.add(item1)
//            list.add(item2)
//            list.add(item1)
//            list.add(item2)
//            list.add(item1)
//            list.add(item2)
//
//            return list
//        }
    }
}