package ru.skillbox.dependency_injection.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.skillbox.dependency_injection.data.ImagesRepository
import ru.skillbox.dependency_injection.data.ImagesRepositoryImpl

@InstallIn(ViewModelComponent:: class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideImagesRepository(impl: ImagesRepositoryImpl): ImagesRepository

}