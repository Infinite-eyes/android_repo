package com.demo.coroutine.carman.remote

import com.demo.coroutine.carman.constant.HttpConstant
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import okio.GzipSource
import org.json.JSONObject
import java.lang.Exception

class ErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())
        val oldRequest = chain.request()
        val host = oldRequest.url.host

        if (HttpConstant.HTTP_SERVER != host) {
            return response
        }

        if (response.isSuccessful) {
            val headers = response.headers
            val responseStr = response.body?.let {
                val source = it.source()
                source.request(java.lang.Long.MAX_VALUE)
                var buffer = source.buffer

                if ("gzip".equals(headers.get("Content-Encoding"), ignoreCase = true)) {
                    GzipSource(buffer.clone()).use{ gzippedResponseBody->
                        buffer = Buffer()
                        buffer.writeAll(gzippedResponseBody)
                    }
                }
                buffer.clone().readString(Charsets.UTF_8)
            }
            if(!responseStr.isNullOrBlank()){
                val responseJson = try {
                    JSONObject(responseStr)
                }catch (e: Exception){
                    return response
                }

                when(responseJson.getInt("code")){
                    HttpConstant.TOKEN_EXPIRED -> {
                        return response
                    }

                    HttpConstant.INVALID_TOKEN ->{
                        return response
                    }
                }
            }
        }
        return response
    }
}