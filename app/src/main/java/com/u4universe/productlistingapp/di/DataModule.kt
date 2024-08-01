package com.u4universe.productlistingapp.di

import com.u4universe.productlistingapp.data.ProductRepoImpl
import com.u4universe.productlistingapp.domain.ProductRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
  @Binds
  abstract fun bindProductRepo(repoImpl: ProductRepoImpl): ProductRepo
}