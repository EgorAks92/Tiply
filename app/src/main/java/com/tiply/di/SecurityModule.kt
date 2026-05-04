package com.tiply.di

import com.tiply.data.security.AndroidKeystoreFieldEncryptor
import com.tiply.data.security.Pbkdf2PinHasher
import com.tiply.domain.security.FieldEncryptor
import com.tiply.domain.security.PinHasher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class)
object SecurityModule {
 @Provides fun providePinHasher(): PinHasher = Pbkdf2PinHasher()
 @Provides @Singleton fun provideFieldEncryptor(): FieldEncryptor = AndroidKeystoreFieldEncryptor()
}
