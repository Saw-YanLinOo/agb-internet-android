package com.agb.customer.billing.delegate

import com.agb.customer.billing.modelVO.BannerVO

interface BannerDelegate {
    fun onTapBanner(data : BannerVO)
}