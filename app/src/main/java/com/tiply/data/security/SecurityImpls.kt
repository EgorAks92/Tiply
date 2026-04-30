package com.tiply.data.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.tiply.domain.security.FieldEncryptor
import com.tiply.domain.security.PinHasher
import java.security.KeyStore
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec

class Pbkdf2PinHasher: PinHasher {
 override fun generateSalt(): String = ByteArray(16).also { SecureRandom().nextBytes(it) }.let { Base64.getEncoder().encodeToString(it) }
 override fun hash(pin: String, salt: String): String { val spec=PBEKeySpec(pin.toCharArray(), Base64.getDecoder().decode(salt), 100_000, 256); return Base64.getEncoder().encodeToString(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).encoded) }
 override fun verify(pin: String, salt: String, hash: String): Boolean = MessageDigest.isEqual(Base64.getDecoder().decode(hash(pin, salt)), Base64.getDecoder().decode(hash))
}

class AndroidKeystoreFieldEncryptor: FieldEncryptor {
 private val alias = "tiply_aes_key"
 private fun keyStore() = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
 private fun getOrCreateKey() = (keyStore().getKey(alias, null) ?: run {
  val kg = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
  kg.init(KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_GCM).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE).build())
  kg.generateKey()
 })
 override fun encrypt(value: String): String { val c=Cipher.getInstance("AES/GCM/NoPadding"); c.init(Cipher.ENCRYPT_MODE, getOrCreateKey()); val iv=c.iv; val enc=c.doFinal(value.toByteArray()); return Base64.getEncoder().encodeToString(iv+enc) }
 override fun decrypt(value: String): String { val all=Base64.getDecoder().decode(value); val iv=all.copyOfRange(0,12); val enc=all.copyOfRange(12,all.size); val c=Cipher.getInstance("AES/GCM/NoPadding"); c.init(Cipher.DECRYPT_MODE, getOrCreateKey(), GCMParameterSpec(128, iv)); return String(c.doFinal(enc)) }
}

class NoOpFieldEncryptor: FieldEncryptor { override fun encrypt(value: String)=value; override fun decrypt(value: String)=value }
fun sha256(input: String): String = MessageDigest.getInstance("SHA-256").digest(input.toByteArray()).joinToString("") { "%02x".format(it) }
