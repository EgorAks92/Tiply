package com.tiply.domain.usecase

import com.tiply.domain.model.CurrencyCode

sealed interface MoneyParseResult { data class Success(val amountMinor: Long): MoneyParseResult; data class Error(val reason: String): MoneyParseResult }
class MoneyParser { fun parse(input: String, currency: CurrencyCode): MoneyParseResult { val n = input.trim().replace(',', '.'); val major = n.toBigDecimalOrNull() ?: return MoneyParseResult.Error("invalid"); if (major <= java.math.BigDecimal.ZERO) return MoneyParseResult.Error("non_positive"); val minor = when(currency){ CurrencyCode.RUB -> major.movePointRight(2); CurrencyCode.AMD -> major.movePointRight(0)}; return MoneyParseResult.Success(minor.longValueExact()) } }
class MoneyFormatter { fun format(amountMinor: Long, currency: CurrencyCode): String = when(currency){ CurrencyCode.RUB -> "%.2f RUB".format(amountMinor / 100.0); CurrencyCode.AMD -> "$amountMinor AMD" } }
class CalculateTipUseCase { fun byPercent(billMinor: Long, percent: Int) = billMinor * percent / 100; fun custom(customMinor: Long) = customMinor.coerceAtLeast(0) }
