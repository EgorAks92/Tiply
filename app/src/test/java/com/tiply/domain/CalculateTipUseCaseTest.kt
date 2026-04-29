package com.tiply.domain

import com.tiply.domain.usecase.CalculateTipUseCase
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateTipUseCaseTest {
    @Test fun percentTip() { assertEquals(500, CalculateTipUseCase().byPercent(10_000, 5)) }
}
