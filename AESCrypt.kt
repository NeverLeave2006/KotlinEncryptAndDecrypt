
import java.security.Key
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.SecretKeySpec

object AESCrypt{
    /**
     * AES加密
     * @param input 明文
     * @param password 密钥
     */
    fun encrypt(input:String,password:String): String {
        //创建cipher对象
        val cipher = Cipher.getInstance("AES")
        //初始化cipher
        //通过密钥工厂生成key
        val keySpec:SecretKeySpec= SecretKeySpec(password.toByteArray(),"AES")
        cipher.init(Cipher.ENCRYPT_MODE,keySpec)
        //加密，解密
        val encrypt = cipher.doFinal(input.toByteArray())
        val result=Base64.getEncoder().encode(encrypt)
        return String(result)
    }

    /**
     * AES解密
     * @param input 明文
     * @param password 密钥
     */
    fun decrypt(input:String,password:String): String {
        //创建cipher对象
        val cipher = Cipher.getInstance("AES")
        //初始化cipher
        //通过密钥工厂生成key
        val keySpec:SecretKeySpec= SecretKeySpec(password.toByteArray(),"AES")
        cipher.init(Cipher.DECRYPT_MODE,keySpec)
        //加密，解密
        val decrypt = cipher.doFinal((Base64.getDecoder().decode(input)))
        return String(decrypt)
    }
}

fun main() {
    val password="0123456789abcdef"//密钥,长度至少16位
    val input="黑马"

    val encryptRes=AESCrypt.encrypt(input,password)
    println(encryptRes)
    val decrypt = AESCrypt.decrypt(encryptRes, password)
    println(decrypt)

}