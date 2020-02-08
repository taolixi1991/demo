package com.wuhandata.wxncovblackboard.common.utils

import org.apache.shiro.codec.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AesHelper {

    /**
     * 开放数据解密，AES解密（Base64)
     * @param encryptedData 加密数据
     * @param sessionKey 加密数据
     * @param iv 加密数据
     */
     fun decryptForWeChatData(encryptedData: String, sessionKey: String, iv: String): String {
        val decryptBytes = Base64.decode(encryptedData)
        val keyBytes = Base64.decode(sessionKey)
        val ivBytes = Base64.decode(iv)
        return String(decryptByAESBytes(decryptBytes, keyBytes, ivBytes))
    }

    private fun decryptByAESBytes(decryptBytes: ByteArray, keyBytes: ByteArray, ivBytes: ByteArray): ByteArray {
        val key = SecretKeySpec(keyBytes, "AES")
        val ivParam = IvParameterSpec(ivBytes)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, key, ivParam)
        return cipher.doFinal(decryptBytes)
    }

}
