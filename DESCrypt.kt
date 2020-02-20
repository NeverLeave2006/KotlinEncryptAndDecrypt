import java.security.Key

import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec

object DESCrypt {
    //算法/工作模式/填充模式
//    val transformation="DES/ECB/PKCS5Padding"
    val transformation="DES/CBC/PKCS5Padding"
    val algorithm="DES"


    /**
     * des加密
     * @param 明文
     * @param 密钥
     */
    fun encrypt(input:String,password:String): ByteArray? {
        //创建cipher对象;查看学习api文档
        val cipher=Cipher.getInstance(transformation)
        //初始化cipher
        val kf=SecretKeyFactory.getInstance(algorithm)
        val keySpec=DESKeySpec(password.toByteArray())

        val key: Key?=kf.generateSecret(keySpec)
        val iv=IvParameterSpec(password.toByteArray())
        cipher.init(Cipher.ENCRYPT_MODE,key,iv)//CBC要额外参数
        //加密/解密
        val encyptRes=cipher.doFinal(input.toByteArray())
        return encyptRes
    }

    /**
     * des解密
     * @param 明文
     * @param 密钥
     */
    fun decrypt(input:ByteArray?,password:String): ByteArray? {
        //创建cipher对象;查看学习api文档
        val cipher=Cipher.getInstance(transformation)
        //初始化cipher
        val kf=SecretKeyFactory.getInstance(algorithm)
        val keySpec=DESKeySpec(password.toByteArray())

        val key: Key?=kf.generateSecret(keySpec)
        val iv=IvParameterSpec(password.toByteArray())
        cipher.init(Cipher.DECRYPT_MODE,key,iv)
        //加密/解密
        val decyptRes=cipher.doFinal(input)
        return decyptRes
    }
}

fun main() {
    /**
     * DES加密和解密
     *
     */
    //原文w
    val input="欢迎来到天堂"
    val password="12345678" //密钥长度是8位

//    //创建cipher对象;查看学习api文档
//    val cipher=Cipher.getInstance("DES")
//    //初始化cipher
//    val kf=SecretKeyFactory.getInstance("DES")
//    val keySpec=DESKeySpec(password.toByteArray())
//
//    val key: Key?=kf.generateSecret(keySpec)
//    cipher.init(Cipher.ENCRYPT_MODE,key)
//    //加密/解密
//    val encypt=cipher.doFinal(input.toByteArray())
    val encypt=DESCrypt.encrypt(input,password)
    println("des加密"+encypt)
    val decrypt=DESCrypt.decrypt(encypt,password)
    println("des解密"+ decrypt?.let { String(it) })
}