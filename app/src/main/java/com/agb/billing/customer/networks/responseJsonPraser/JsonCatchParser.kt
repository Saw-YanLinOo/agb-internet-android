package com.agb.billing.customer.networks.responseJsonPraser

import com.agb.billing.customer.networks.responses.BaseResponse
import com.agb.billing.customer.utils.Constants
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonParser

open class JsonCatchParser {
    companion object {

        fun getApiErrorJsonData(errorString: String): String {
            var value = ""
            try {
                val mJson = JsonParser.parseString(errorString)
                val errorResponse = Gson().fromJson(mJson, BaseResponse::class.java)
                value = if (errorResponse.responseCode == Constants.INVALID_SESSION_CODE) {
                    Constants.INVALID_SESSION_CODE
                } else {
                    if (errorResponse.error != null) {
                        if (errorResponse.error!!.size > 0) {
                            errorResponse.error!![0].errorMessage.toString()
                        } else {
                            errorResponse.responseMessage.toString()
                        }
                    } else {
                        errorResponse.responseMessage.toString()
                    }

                }

            } catch (ex: JsonParseException) {
                value = ex.message.toString()
            }

            return value

        }

        fun getApiErrorCodeJsonData(errorString: String): MutableList<String> {
            var value = ""
            var code = "500"
            try {
                val mJson = JsonParser.parseString(errorString)
                val errorResponse = Gson().fromJson(mJson, BaseResponse::class.java)
                if (errorResponse.error != null) {
                    if (errorResponse.error!!.size > 0) {
                        code = errorResponse.responseMessage.toString()
                    }else{
                        code = errorResponse.responseMessage.toString()
                    }
                }
                value = if (errorResponse.responseCode == Constants.INVALID_SESSION_CODE) {
                    Constants.INVALID_SESSION_CODE
                } else {
                    if (errorResponse.error != null) {
                        if (errorResponse.error!!.size > 0) {
//                            code = errorResponse.error!![0].fieldErrorCode.toString()
                            errorResponse.error!![0].errorMessage.toString()
                        } else {
                            errorResponse.responseMessage.toString()
                        }
                    } else {
                        errorResponse.responseMessage.toString()
                    }

                }

            } catch (ex: JsonParseException) {
                value = ex.message.toString()
            }

            val list = mutableListOf<String>()
            list.add(value)
            list.add(code)
            return list

        }
    }

}