package com.app.myapp

import com.android.volley.toolbox.StringRequest
import com.android.volley.VolleyError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response

object DoApi {

    fun GET(    url: String , listener: Listener  ): StringRequest {

        val finalUrl: String = "https://data.covid19.go.id/public/api/$url"

        val stringRequest: StringRequest =
            object : StringRequest(Method.GET, finalUrl, Response.Listener { response: String ->
                listener.onSuccess(response)
            }, Response.ErrorListener { error: VolleyError? ->
                listener.onFail("Error Connection")
            }) {
            }
        stringRequest.retryPolicy = DefaultRetryPolicy(
            62000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        return stringRequest
    }

    interface Listener {
        fun onSuccess(data: String?)
        fun onFail(data: String?)
    }
}