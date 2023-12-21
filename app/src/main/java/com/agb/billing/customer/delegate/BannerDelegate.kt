package com.agb.billing.customer.delegate

import com.agb.billing.customer.modelVO.BannerVO

interface BannerDelegate {
    fun onTapBanner(data : BannerVO)
}