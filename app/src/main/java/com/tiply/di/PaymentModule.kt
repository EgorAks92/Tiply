package com.tiply.di

import com.tiply.data.payment.*
import com.tiply.domain.payment.CardReaderApiRouter
import com.tiply.domain.payment.PaymentApiRouter
import com.tiply.domain.payment.PaymentExtraJsonBuilder
import com.tiply.domain.payment.PaymentSdkFacade
import com.tiply.domain.result.CardReaderApi
import com.tiply.domain.result.PaymentApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class)
object PaymentModule {
 @Provides fun provideSdk(): PaymentSdkFacade = AarPaymentSdkFacade()
 @Provides fun provideMockPay(): PaymentApi = MockPaymentApi()
 @Provides fun provideIntentPay(): PaymentApi = IntentPaymentApi()
 @Provides fun provideAarPay(sdk: PaymentSdkFacade): PaymentApi = AarPaymentApi(sdk)
 @Provides fun provideMockRead(): CardReaderApi = MockCardReaderApi()
 @Provides fun provideIntentRead(): CardReaderApi = IntentCardReaderApi()
 @Provides fun provideAarRead(sdk: PaymentSdkFacade): CardReaderApi = AarCardReaderApi(sdk)
 @Provides @Singleton fun providePayRouter(mockPay: PaymentApi, intentPay: PaymentApi, aarPay: PaymentApi): PaymentApiRouter = PaymentRouter(mockPay,intentPay,aarPay)
 @Provides @Singleton fun provideReadRouter(mockRead: CardReaderApi, intentRead: CardReaderApi, aarRead: CardReaderApi): CardReaderApiRouter = CardReaderRouter(mockRead,intentRead,aarRead)
 @Provides fun provideExtraBuilder(): PaymentExtraJsonBuilder = DefaultPaymentExtraJsonBuilder()
}
