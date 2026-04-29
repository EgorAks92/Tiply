package com.tiply.data.security

import com.tiply.domain.security.FieldEncryptor
import com.tiply.domain.security.PinHasher
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class Pbkdf2PinHasher: PinHasher {
 override fun generateSalt(): String = ByteArray(16).also { SecureRandom().nextBytes(it) }.let { Base64.getEncoder().encodeToString(it) }
 override fun hash(pin: String, salt: String): String { val spec=PBEKeySpec(pin.toCharArray(), Base64.getDecoder().decode(salt), 12000, 256); return Base64.getEncoder().encodeToString(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).encoded) }
 override fun verify(pin: String, salt: String, hash: String): Boolean = hash(pin, salt)==hash
}
class AndroidKeystoreFieldEncryptor: FieldEncryptor {
 override fun encrypt(value: String): String = Base64.getEncoder().encodeToString(value.toByteArray())
 override fun decrypt(value: String): String = String(Base64.getDecoder().decode(value))
}
class NoOpFieldEncryptor: FieldEncryptor {
 override fun encrypt(value: String): String = value
 override fun decrypt(value: String): String = value
}
fun sha256(input: String): String = MessageDigest.getInstance("SHA-256").digest(input.toByteArray()).joinToString("") { "%02x".format(it) }
