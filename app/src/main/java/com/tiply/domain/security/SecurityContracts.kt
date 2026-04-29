package com.tiply.domain.security

interface PinHasher { fun generateSalt(): String; fun hash(pin: String, salt: String): String; fun verify(pin: String, salt: String, hash: String): Boolean }
interface FieldEncryptor { fun encrypt(value: String): String; fun decrypt(value: String): String }
