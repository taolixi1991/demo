package com.wuhandata.wxncovblackboard.common.utils

import okhttp3.FormBody
import java.io.IOException
import okhttp3.Request
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RequestUtil {
    private val CONNECT_TIMEOUT: Long = 6
    private val READ_TIMEOUT: Long = 20
    private val WRITE_TIMEOUT: Long = 60

    @Throws(IOException::class)
    fun post(url: String, params: HashMap<String, String>): String {
        val client = OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build()
        val requestBody = paramsToRequestBody(params)
        val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()
        val response = client.newCall(request).execute()
        if(response.body == null) {
            return ""
        }
        return response.body!!.string()
    }

    @Throws(IOException::class)
    fun get(url: String): String {
        val client = OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build()
        val request = Request.Builder()
                .url(url)
                .build()

        val response = client.newCall(request).execute()
        if(response.body == null) {
            return ""
        }
        return response.body!!.string()
    }

    /**
     * post同步请求，跳过证书验证
     */
    @Throws(IOException::class)
    fun postInSSLUnsafe(url: String, params: HashMap<String, String>): String {
        val client = OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .sslSocketFactory(createSSLSocketFactory(), buildTrustManagers()[0] as X509TrustManager)
                .hostnameVerifier(HostnameVerifier { hostname, session -> true })
                .build()
        val requestBody = paramsToRequestBody(params)
        val request = Request.Builder().url(url).post(requestBody).build()
        val response = client.newCall(request).execute()
        if(response.body == null) {
            return ""
        }
        return response.body!!.string()
    }

    /**
     * post同步请求，跳过证书验证
     */
    @Throws(IOException::class)
    fun postInSSLUnsafeWithXML(url: String, params: String): String {
        val client = OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .sslSocketFactory(createSSLSocketFactory(), buildTrustManagers()[0] as X509TrustManager)
                .hostnameVerifier(HostnameVerifier { hostname, session -> true })
                .build()
        val requestBody = params.toRequestBody()
        val request = Request.Builder().url(url).post(requestBody).build()
        val response = client.newCall(request).execute()
        if(response.body == null) {
            return ""
        }
        return response.body!!.string()
    }

    /**
     * get同步请求，跳过证书验证
     */
    @Throws(IOException::class)
    fun getInSSLUnsafe(url: String): String {
        val client = OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .sslSocketFactory(createSSLSocketFactory(), buildTrustManagers()[0] as X509TrustManager)
                .hostnameVerifier(HostnameVerifier { hostname, session -> true })
                .build()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        if(response.body == null) {
            return ""
        }
        return response.body!!.string()
    }

    @Throws(IOException::class)
    fun createSSLSocketFactory(): SSLSocketFactory {
        var sslFactory: SSLSocketFactory
        val sc = SSLContext.getInstance("TLS")
        sc.init(null, buildTrustManagers(), SecureRandom())
        sslFactory = sc.socketFactory
        return sslFactory
    }


    private fun buildTrustManagers(): Array<TrustManager> {
        return arrayOf(object : X509TrustManager {

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }

            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
        })
    }


    private fun paramsToRequestBody(params: HashMap<String, String>): FormBody {
        val bodyBuilder = FormBody.Builder()
        for(entry in params.entries) {
            bodyBuilder.add(entry.key, entry.value)
        }
        return bodyBuilder.build()
    }


}
