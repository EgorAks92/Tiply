package com.tiply.domain

import com.tiply.domain.usecase.CalculateTipUseCase
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateTipUseCaseTest {
    private val useCase = CalculateTipUseCase()

    @Test fun `5 percent of 10000`() = assertEquals(500, useCase.byPercent(10_000, 5))
    @Test fun `10 percent of 10000`() = assertEquals(1000, useCase.byPercent(10_000, 10))
    @Test fun `15 percent of 10000`() = assertEquals(1500, useCase.byPercent(10_000, 15))
    @Test fun `custom 0`() = assertEquals(0, useCase.custom(0))
    @Test fun `negative custom coerced to zero`() = assertEquals(0, useCase.custom(-1))
}
