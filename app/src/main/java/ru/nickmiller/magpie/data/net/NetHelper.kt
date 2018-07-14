package ru.magpie.magpie.data.net


import okhttp3.*
import java.io.IOException


class NetHelper {

    private val client = OkHttpClient()
    private lateinit var request: Request


    fun call(url: String, success: (call: Call?, resp: Response?) -> Unit, error: (call: Call?, e: Exception?) -> Unit) {
        request = Request.Builder()
                .url(url)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                success(call, response)
            }

            override fun onFailure(call: Call?, e: IOException?) {
                error(call, e)
            }
        })
    }

    fun call(url: String): Call {
        request = Request.Builder()
                .url(url)
                .build()
        return client.newCall(request)
    }
}