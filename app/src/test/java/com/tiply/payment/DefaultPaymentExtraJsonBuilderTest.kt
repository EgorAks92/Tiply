package com.tiply.payment

import com.tiply.data.payment.DefaultPaymentExtraJsonBuilder
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Test

class DefaultPaymentExtraJsonBuilderTest {
 @Test fun jsonFields() { val j=JSONObject(DefaultPaymentExtraJsonBuilder().build(1,"A","B",100,10,110,"RUB","T1","1.0",1)); listOf("waiterId","waiterFirstName","waiterLastName","billAmountMinor","tipAmountMinor","totalAmountMinor","currency","terminalId","appVersion","timestamp").forEach { assertTrue(j.has(it)) }; listOf("panSha256","waiterCardBindingHash","cardBindingHash").forEach { assertFalse(j.has(it)) } }
}
