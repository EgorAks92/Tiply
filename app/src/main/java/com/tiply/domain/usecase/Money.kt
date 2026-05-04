package com.tiply.domain.usecase

import com.tiply.domain.model.CurrencyCode
import java.math.BigDecimal
import java.math.RoundingMode

sealed interface MoneyParseError {
    data object EMPTY : MoneyParseError
    data object INVALID_FORMAT : MoneyParseError
    data object TOO_MANY_DECIMALS : MoneyParseError
    data object NON_POSITIVE : MoneyParseError
}

sealed interface MoneyParseResult {
    data class Success(val amountMinor: Long) : MoneyParseResult
    data class Error(val reason: MoneyParseError) : MoneyParseResult
}

class MoneyParser {
    fun parse(input: String, currency: CurrencyCode, allowZero: Boolean = false): MoneyParseResult {
        val trimmed = input.trim()
        if (trimmed.isEmpty()) return MoneyParseResult.Error(MoneyParseError.EMPTY)

        val normalized = trimmed.replace(',', '.')
        val major = normalized.toBigDecimalOrNull() ?: return MoneyParseResult.Error(MoneyParseError.INVALID_FORMAT)

        if (!allowZero && major <= BigDecimal.ZERO) return MoneyParseResult.Error(MoneyParseError.NON_POSITIVE)
        if (allowZero && major < BigDecimal.ZERO) return MoneyParseResult.Error(MoneyParseError.NON_POSITIVE)

        val scaleAllowed = if (currency == CurrencyCode.RUB) 2 else 0
        if (major.scale() > scaleAllowed) return MoneyParseResult.Error(MoneyParseError.TOO_MANY_DECIMALS)

        val minor = major.movePointRight(scaleAllowed).setScale(0, RoundingMode.UNNECESSARY)
        return try {
            MoneyParseResult.Success(minor.longValueExact())
        } catch (_: ArithmeticException) {
            MoneyParseResult.Error(MoneyParseError.INVALID_FORMAT)
        }
    }
}

class MoneyFormatter {
    fun format(amountMinor: Long, currency: CurrencyCode): String = when (currency) {
        CurrencyCode.RUB -> {
            val sign = if (amountMinor < 0) "-" else ""
            val abs = kotlin.math.abs(amountMinor)
            val whole = abs / 100
            val fraction = abs % 100
            "$sign$whole.${fraction.toString().padStart(2, '0')} RUB"
        }
        CurrencyCode.AMD -> "$amountMinor AMD"
    }
}

class CalculateTipUseCase {
    fun byPercent(billMinor: Long, percent: Int): Long = billMinor * percent / 100
    fun custom(customMinor: Long): Long = customMinor.coerceAtLeast(0)
}
