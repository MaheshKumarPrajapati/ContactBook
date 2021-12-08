package com.example.contactbook.di

import com.example.contactbook.data.services.HashService
import com.example.contactbook.data.services.InputValidationService
import com.example.contactbook.data.services.abstractions.IHashService
import com.example.contactbook.data.services.abstractions.IInputValidationService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun bindInputValidationService(inputValidationService: InputValidationService): IInputValidationService

    @Binds
    abstract fun bindIHashService(hashService: HashService): IHashService
}