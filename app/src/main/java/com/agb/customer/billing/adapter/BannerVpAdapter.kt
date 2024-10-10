package com.agb.customer.billing.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.agb.customer.billing.R
import com.agb.customer.billing.databinding.ItemBannerBinding
import com.agb.customer.billing.delegate.BannerDelegate
import com.agb.customer.billing.modelVO.BannerVO
import com.bumptech.glide.Glide

class BannerVpAdapter(context: Context,delegate : BannerDelegate) : PagerAdapter(){

    var imgList = mutableListOf<BannerVO>()
    var context: Context = context
    val mDelegate = delegate

    private var _binding : ItemBannerBinding ?= null
    private val binding get() = _binding!!

    override fun getCount(): Int {
        return imgList.size
    }

    override fun destroyItem( container: ViewGroup, position: Int,  `object`: Any) {
        container.removeView(`object` as View)
    }

    @NonNull
    override fun instantiateItem(@NonNull container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        _binding = ItemBannerBinding.inflate(inflater,container,false)
//        val bodyText = imgLayout.findViewById(R.id.tv_what_new_body) as TextView
//        val btnBook = imgLayout.findViewById(R.id.btn_what_new_book) as Button
//        bodyText.text = imgList[0].text
//        btnBook.setOnClickListener{
//            Toast.makeText(context,"Book Now",Toast.LENGTH_SHORT).show()
//        }
        binding.apply {
            Glide.with(context)
                .load(imgList[position].imagePath)
                .placeholder(R.drawable.sample_banner)
                .into(ivBanner)
//            ivBanner.setImageResource(imgList[position].imagePath)

            cvBanner.setOnClickListener {
                mDelegate.onTapBanner(imgList[position])
            }
        }


        container.addView(binding.root)
        return binding.root
    }

    fun setItem(bannerList: MutableList<BannerVO>) {
        this.imgList.clear()
        this.imgList.addAll(bannerList)
        notifyDataSetChanged()
    }

    override fun isViewFromObject(@NonNull view: View, @NonNull `object`: Any): Boolean {
        return view == `object`
    }
}