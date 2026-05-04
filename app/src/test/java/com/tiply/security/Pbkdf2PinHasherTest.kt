package com.tiply.security

import com.tiply.data.security.Pbkdf2PinHasher
import org.junit.Assert.*
import org.junit.Test

class Pbkdf2PinHasherTest {
 @Test fun basic() { val h=Pbkdf2PinHasher(); val s1=h.generateSalt(); val s2=h.generateSalt(); assertTrue(s1.isNotBlank()); val a=h.hash("1234",s1); assertTrue(h.verify("1234",s1,a)); assertFalse(h.verify("9999",s1,a)); assertNotEquals(a,h.hash("1234",s2)); assertNotEquals("1234",a)}
}
