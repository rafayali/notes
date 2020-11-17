package com.rafay.notes.api

import com.rafay.notes.BuildConfig
import com.rafay.notes.domain.AuthTokenStore
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

/**
 * Provides api service class instances using [Retrofit] and [OkHttpClient].
 */
class ApiProvider(private val tokenStore: AuthTokenStore) {

    private val httpLoggingInterceptor =
        HttpLoggingInterceptor { Timber.tag(TAG).d(it) }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    private val okHttpClient = OkHttpClient.Builder()
        .authenticator(Authenticator.JAVA_NET_AUTHENTICATOR)
        .addInterceptor {
            val request = if (it.request().headers[HEADER_AUTHORIZATION] != null) {
                it.call().request().newBuilder()
                    .removeHeader(HEADER_AUTHORIZATION)
                    .addHeader(HEADER_AUTHORIZATION, requireNotNull(tokenStore.token))
                    .build()
            } else {
                it.request()
            }

            it.proceed(request)
        }.run {
            if (BuildConfig.DEBUG) addInterceptor(httpLoggingInterceptor) else this
        }.build()

    private val retrofit = Retrofit.Builder().client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BuildConfig.API_BASE_URL)
        .build()

    val api: Api

    init {
        api = retrofit.create(Api::class.java)
    }

    companion object {
        private const val TAG = "OkHttp"
        private const val HEADER_AUTHORIZATION = "Authorization"
    }
}
