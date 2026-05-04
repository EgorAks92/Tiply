package com.tiply.domain

import com.tiply.domain.model.CurrencyCode
import com.tiply.domain.usecase.MoneyParseError
import com.tiply.domain.usecase.MoneyParseResult
import com.tiply.domain.usecase.MoneyParser
import org.junit.Assert.assertEquals
import org.junit.Test

class MoneyParserTest {
    private val parser = MoneyParser()

    @Test fun `RUB 1000 -> 100000`() = assertEquals(MoneyParseResult.Success(100000), parser.parse("1000", CurrencyCode.RUB))
    @Test fun `RUB 1000 dot 50 -> 100050`() = assertEquals(MoneyParseResult.Success(100050), parser.parse("1000.50", CurrencyCode.RUB))
    @Test fun `RUB 1000 comma 50 -> 100050`() = assertEquals(MoneyParseResult.Success(100050), parser.parse("1000,50", CurrencyCode.RUB))
    @Test fun `RUB too many decimals`() = assertEquals(MoneyParseResult.Error(MoneyParseError.TOO_MANY_DECIMALS), parser.parse("1000.555", CurrencyCode.RUB))
    @Test fun `AMD 1000 -> 1000`() = assertEquals(MoneyParseResult.Success(1000), parser.parse("1000", CurrencyCode.AMD))
    @Test fun `AMD decimal -> error`() = assertEquals(MoneyParseResult.Error(MoneyParseError.TOO_MANY_DECIMALS), parser.parse("1000.50", CurrencyCode.AMD))
    @Test fun `empty -> EMPTY`() = assertEquals(MoneyParseResult.Error(MoneyParseError.EMPTY), parser.parse("  ", CurrencyCode.RUB))
    @Test fun `invalid -> INVALID_FORMAT`() = assertEquals(MoneyParseResult.Error(MoneyParseError.INVALID_FORMAT), parser.parse("abc", CurrencyCode.RUB))
    @Test fun `zero allowZero false -> NON_POSITIVE`() = assertEquals(MoneyParseResult.Error(MoneyParseError.NON_POSITIVE), parser.parse("0", CurrencyCode.RUB, allowZero = false))
    @Test fun `zero allowZero true -> success`() = assertEquals(MoneyParseResult.Success(0), parser.parse("0", CurrencyCode.RUB, allowZero = true))
}
