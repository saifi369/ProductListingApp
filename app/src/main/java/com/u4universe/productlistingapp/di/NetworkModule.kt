package com.u4universe.productlistingapp.di

import com.u4universe.productlistingapp.data.ProductRepoImpl
import com.u4universe.productlistingapp.data.remote.ProductApi
import com.u4universe.productlistingapp.domain.ProductRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  fun provideBaseUrl(): String = "https://api.escuelajs.co/"

  @Provides
  @Singleton
  fun provideOkHttp(): OkHttpClient {
    val client = OkHttpClient.Builder()
      .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
      }).build()
    return client
  }

  @Provides
  @Singleton
  fun providesRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

  @Provides
  @Singleton
  fun providesProductApi(retrofit: Retrofit): ProductApi =
    retrofit.create(ProductApi::class.java)

}