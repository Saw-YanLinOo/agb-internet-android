package com.agb.customer.billing.utils

import android.text.method.PasswordTransformationMethod
import android.view.View

object AsteriskPasswordTransformationMethod : PasswordTransformationMethod() {
    override fun getTransformation(
        source: CharSequence,
        view: View
    ): CharSequence {
        return PasswordCharSequence(source)
    }

    class PasswordCharSequence(source: CharSequence) : CharSequence {
        var mSource = source
        override val length: Int
            get() = mSource.length

        override fun get(index: Int): Char {
            return '\u25CF'
        }


        override fun subSequence(start: Int, end: Int): CharSequence {
            return mSource.subSequence(start, end) // Return default
        }
    }
}