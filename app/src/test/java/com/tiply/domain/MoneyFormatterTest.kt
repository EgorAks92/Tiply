package com.tiply.domain

import com.tiply.domain.model.CurrencyCode
import com.tiply.domain.usecase.MoneyFormatter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class MoneyFormatterTest {
    private val formatter = MoneyFormatter()

    @Test fun `RUB 100050 stable`() = assertEquals("1000.50 RUB", formatter.format(100050, CurrencyCode.RUB))
    @Test fun `RUB 100000 stable`() = assertEquals("1000.00 RUB", formatter.format(100000, CurrencyCode.RUB))
    @Test fun `AMD 1000 stable`() = assertEquals("1000 AMD", formatter.format(1000, CurrencyCode.AMD))
    @Test fun `no float artifacts`() {
        val out = formatter.format(199, CurrencyCode.RUB)
        assertFalse(out.contains("E"))
    }
}
