package com.tiply.di

import com.tiply.data.payment.*
import com.tiply.domain.payment.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier annotation class MockPay
@Qualifier annotation class IntentPay
@Qualifier annotation class AarPay
@Qualifier annotation class MockRead
@Qualifier annotation class IntentRead
@Qualifier annotation class AarRead

@Module @InstallIn(SingletonComponent::class)
object PaymentModule {
 @Provides fun provideSdk(): PaymentSdkFacade = AarPaymentSdkFacade()
 @Provides @MockPay fun provideMockPay(): PaymentApi = MockPaymentApi()
 @Provides @IntentPay fun provideIntentPay(): PaymentApi = IntentPaymentApi()
 @Provides @AarPay fun provideAarPay(sdk: PaymentSdkFacade): PaymentApi = AarPaymentApi(sdk)
 @Provides @MockRead fun provideMockRead(): CardReaderApi = MockCardReaderApi()
 @Provides @IntentRead fun provideIntentRead(): CardReaderApi = IntentCardReaderApi()
 @Provides @AarRead fun provideAarRead(sdk: PaymentSdkFacade): CardReaderApi = AarCardReaderApi(sdk)
 @Provides @Singleton fun providePayRouter(@MockPay mockPay: PaymentApi, @IntentPay intentPay: PaymentApi, @AarPay aarPay: PaymentApi): PaymentApiRouter = PaymentRouter(mockPay,intentPay,aarPay)
 @Provides @Singleton fun provideReadRouter(@MockRead mockRead: CardReaderApi, @IntentRead intentRead: CardReaderApi, @AarRead aarRead: CardReaderApi): CardReaderApiRouter = CardReaderRouter(mockRead,intentRead,aarRead)
 @Provides fun provideExtraBuilder(): PaymentExtraJsonBuilder = DefaultPaymentExtraJsonBuilder()
}
