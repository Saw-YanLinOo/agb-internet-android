package com.agb.customer.billing.com.kbzbank.payment.sdk.callback

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.agb.customer.billing.databinding.ActivityCallbackResultBinding
import com.kbzbank.payment.KBZPay

class CallbackResultActivity : AppCompatActivity() {

    lateinit var binding : ActivityCallbackResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallbackResultBinding.inflate(layoutInflater)
        val intent = intent
        val result = intent.getIntExtra(KBZPay.EXTRA_RESULT, 0)
        if (result == KBZPay.COMPLETED) {
            Log.d("KBZPay", "pay success!")
        } else {
            val failMsg = intent.getStringExtra(KBZPay.EXTRA_FAIL_MSG)
            Log.d("KBZPay", "pay fail, fail reason = $failMsg")
        }
    }

}