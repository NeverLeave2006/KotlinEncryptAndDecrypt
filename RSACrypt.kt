
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64
import java.io.ByteArrayOutputStream
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * 非对称加密解密RSA
 */
object RSACrypt {

    val transformation="RSA"
    val ENCRYPT_MAX_SIZE=117//加密每次最多117个字节
    val DECRYPT_MAX_SIZE=128//解密每次最多128个字节
    /**
     * 私钥加密
     * @param input 明文
     * @param privateKey 密钥
     */
    fun encryptByPrivateKey(input:String,privateKey: PrivateKey):String{

        val byteArray=input.toByteArray()

        //1.创建cipher对象
        val cipher = Cipher.getInstance(transformation)

        //2.初始化cipher
        cipher.init(Cipher.ENCRYPT_MODE,privateKey)
        //3.加密/解密
        //val encrypt = cipher.doFinal(input.toByteArray())

        var temp:ByteArray?=null
        var offset=0  //当前偏移的位置

        val bos = ByteArrayOutputStream()

        while (byteArray.size-offset>0){//没加密完
            //每次最大加密117个字节
            if(byteArray.size-offset>= ENCRYPT_MAX_SIZE){
                //剩余部分大于117
                //加密完整一块
                temp=cipher.doFinal(byteArray,offset, ENCRYPT_MAX_SIZE)
                //重新计算偏移的位置
                offset+= ENCRYPT_MAX_SIZE
            }else{
                //加密最后一块
                temp=cipher.doFinal(byteArray,offset,byteArray.size-offset)
                //重新计算偏移位置
                offset=byteArray.size
            }
            //存储到临时缓冲区
            bos.write(temp)

        }
        bos.close()

        return Base64.encode(bos.toByteArray())
    }

    /**
     * 私钥解密
     * @param input 密文
     * @param privateKey 密钥
     */
    fun decryptByPrivateKey(input:String,privateKey: PrivateKey):String{

        val byteArray=Base64.decode(input)

        //1.创建cipher对象
        val cipher = Cipher.getInstance(transformation)

        //2.初始化cipher
        cipher.init(Cipher.DECRYPT_MODE,privateKey)
        //3.加密/解密
        //val encrypt = cipher.doFinal(input.toByteArray())

        //分段解密
        var temp:ByteArray?=null
        var offset=0  //当前偏移的位置

        val bos = ByteArrayOutputStream()

        while (byteArray.size-offset>0){//没加密完
            //每次最大解密128个字节
            if(byteArray.size-offset>= DECRYPT_MAX_SIZE){
                //剩余部分大于128
                //加密完整一块
                temp=cipher.doFinal(byteArray,offset, DECRYPT_MAX_SIZE)
                //重新计算偏移的位置
                offset+= DECRYPT_MAX_SIZE
            }else{
                //加密最后一块
                temp=cipher.doFinal(byteArray,offset,byteArray.size-offset)
                //重新计算偏移位置
                offset=byteArray.size
            }
            //存储到临时缓冲区
            bos.write(temp)

        }
        bos.close()
        return String(bos.toByteArray())
    }

    /**
     * 共钥加密
     * @param input 明文
     * @param privateKey 密钥
     */
    fun encryptByPublicKey(input:String,publicKey: PublicKey): String {
        val byteArray=input.toByteArray()

        //1.创建cipher对象
        val cipher = Cipher.getInstance(transformation)

        //2.初始化cipher
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        //3.加密/解密
        //val encrypt = cipher.doFinal(input.toByteArray())

        var temp: ByteArray? = null
        var offset = 0  //当前偏移的位置

        val bos = ByteArrayOutputStream()

        while (byteArray.size - offset > 0) {//没加密完
            //每次最大加密117个字节
            if (byteArray.size - offset >= DECRYPT_MAX_SIZE) {
                //剩余部分大于117
                //加密完整一块
                temp = cipher.doFinal(byteArray, offset, DECRYPT_MAX_SIZE)
                //重新计算偏移的位置
                offset += DECRYPT_MAX_SIZE
            } else {
                //加密最后一块
                temp = cipher.doFinal(byteArray, offset, byteArray.size - offset)
                //重新计算偏移位置
                offset = byteArray.size
            }
            //存储到临时缓冲区
            bos.write(temp)


        }
        bos.close()
        return Base64.encode(bos.toByteArray())
    }

    /**
     * 共钥解密
     * @param input 明文
     * @param privateKey 密钥
     */
    fun decryptByPublicKey(input:String,publicKey: PublicKey): String {
        val byteArray = input.toByteArray()

        //1.创建cipher对象
        val cipher = Cipher.getInstance(transformation)

        //2.初始化cipher
        cipher.init(Cipher.DECRYPT_MODE, publicKey)
        //3.加密/解密
        //val encrypt = cipher.doFinal(input.toByteArray())

        var temp: ByteArray? = null
        var offset = 0  //当前偏移的位置

        val bos = ByteArrayOutputStream()

        while (byteArray.size - offset > 0) {//没加密完
            //每次最大加密117个字节
            if (byteArray.size - offset >= ENCRYPT_MAX_SIZE) {
                //剩余部分大于117
                //加密完整一块
                temp = cipher.doFinal(byteArray, offset, ENCRYPT_MAX_SIZE)
                //重新计算偏移的位置
                offset += ENCRYPT_MAX_SIZE
            } else {
                //加密最后一块
                temp = cipher.doFinal(byteArray, offset, byteArray.size - offset)
                //重新计算偏移位置
                offset = byteArray.size
            }
            //存储到临时缓冲区
            bos.write(temp)


        }
        return String(bos.toByteArray())
    }
}

fun main() {

//    val transformation="RSA"
//    //如何生成密钥对:密钥对
//
//    //密钥对生成器
//    val pairGenerator = KeyPairGenerator.getInstance("RSA")
//    //生成密钥对
//    val keyPair = pairGenerator.genKeyPair()
//    //公钥Base64转码
//    val publicKey = keyPair.public
//    //私钥
//    val privateKey = keyPair.private
//
//    println("publicKey="+Base64.encode(publicKey.encoded))
//    println("privateKey="+Base64.encode(privateKey.encoded))

    //保存密钥对
    val publicKeyStr="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg39OAZDhQzDk5yPaihZT04/Nuppv4UrZwDljWseFf6QtxGMBl29UpuCZJ7k7293Z1LRQ+TdCOdFTZvb8IyNV3Qx7QitHKsfOWBZU6MshsZuUtP8pBfP0o4y5WVQmEoP7sDQTtyct092UcIfYfY0EU/goJC5iqhm+AsWdr8ZzABlUbvTPaR1Seu9oawHAEO3MJiHazRuwAnLoC2lry3Rzovcc1LG3chdlwPRYieMb1Fy5qr9n4h+TydWvpjN1iHeibFwwBNsRgpyKP7Pz9BX4tpJKLCJinMvwwzuQnjq1T2N7/H/A71HLNqi3yTSFwRITvRvX5E1EAo9rB9dpo0nKkQIDAQAB"
    val privateKeyStr="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCDf04BkOFDMOTnI9qKFlPTj826mm/hStnAOWNax4V/pC3EYwGXb1Sm4JknuTvb3dnUtFD5N0I50VNm9vwjI1XdDHtCK0cqx85YFlToyyGxm5S0/ykF8/SjjLlZVCYSg/uwNBO3Jy3T3ZRwh9h9jQRT+CgkLmKqGb4CxZ2vxnMAGVRu9M9pHVJ672hrAcAQ7cwmIdrNG7ACcugLaWvLdHOi9xzUsbdyF2XA9FiJ4xvUXLmqv2fiH5PJ1a+mM3WId6JsXDAE2xGCnIo/s/P0Ffi2kkosImKcy/DDO5CeOrVPY3v8f8DvUcs2qLfJNIXBEhO9G9fkTUQCj2sH12mjScqRAgMBAAECggEAJUi1P3BqznhQjtD1p+2IuxWtCVm8my5z6zLaXuTvjy9n4qUH0H46jE5mXoMA2+hdWRf/VYo7dtVJRoqFFxlvwQtG744B0blxYjyCurbNyOQKF/+5C/0NwkmmTBO1784IUP+TD31GWyoaaoW59m9DaOOVzhxw0TYXUAXkHfCwrrgShrVWnhSDWAJ9utGurORK7nRC/1XuL53YxnwsLD7TfQ27cOSwYMWqaqauvrSDjbDImLluS8aSkBcesC+k30p10e/tlkQOqZQCSGF4Qm3kraPj9oKyy3925Pn+HIKBuKSePYhh8I8WXtgAerah8UD5Dx1INrClViTbzG0BxNDLGQKBgQD8HSDq2jUuP1PDIFtuhUkCV2yqXnMFGZq/ReXO63AxE8THJrmg272epYaPhKe4/ycBjPCxpmEhVGKMKrPCQD9e5rDb5rm1bbV5r8ljVfiTWxoILxy0gK/mKoRTCmqRt7dZYdKdlcjhHjAl/9GNsWiE9UkbtIX2bkLsHK5iv5GBrwKBgQCFhjV4HZBA77pQ/LyBkDtX5Auay2QtFCh48qI+RynRs6IVxjbkjre/HLx8ikxbATxbDB4ieYHMDcUnoACxAoS8snmLvuHyOde4IzGBqE66pFdPJ0e8TBOh1PaSNwgf6XllnMMF2r8un0kMhyHu/2SkWpGUiZdcCLW5O1R55oXHvwKBgA/EtqFZwofA+Dk1FeLkvTtDQo2pWKJSBqa99XCqsxqutNkL6AJSnBqHGdJyJpdghg0Hxqol0N9nyr4fkZytrgj+fsNSO/1H/li9EOiAoQp+YTNv1ujHP7kyoZIcDaxNhE832hd3HdhOrg5CYNXu6a2fNdJgyRwvq0nfRVzxxGULAoGAcB0EgFqyaRPhKWdNEKz+ZwoABHWLI56tbQCt4gT2xDFEg2vvY3HCLyH90cR94FvlRb2i/+duaNuXE3Jxoi8vLRHepMweA7k1vFMwmmGr2WNsO/YLcbufMF8UuiijFciAOiWqSxwlMwxj1bURdQ/UfXveiQ4OnSRjEakTgVdiNRkCgYEArik90gasrAxMby7JP7Ve/7VHFUIZAvPHUgSzcNDT5qQRiDeVgpZFeSA7TK3MXSXHcx5QrJqdB5jXaMcHTth2/ssTRmTw9s7W+plBF6gYbywrI2DshR61W0ReWzenm76w0KT4JqmWeF3A8SSQia+vBi06Ea0/0ez6h+K2ccG9nGU="

    //字符串转成密钥对对象
    val kf=KeyFactory.getInstance("RSA")
    val privateKey:PrivateKey=kf.generatePrivate(PKCS8EncodedKeySpec(Base64.decode(privateKeyStr)))
    val publicKey:PublicKey=kf.generatePublic(X509EncodedKeySpec(Base64.decode(publicKeyStr)))

    val input="黑马黑马黑马黑马黑马黑马黑马黑马黑马黑马黑马黑马黑马黑马黑马黑马黑马黑马黑马黑马欢迎来到黑马程序员"

    println(input.length)
    println("byte数组长度="+input.toByteArray().size)

    //私钥加密 加密不能超过117个字节
    val encrypt = RSACrypt.encryptByPrivateKey(input, privateKey)
    println("私钥加密="+encrypt)

    //公钥加密
    val encryptByPublicKey = RSACrypt.encryptByPublicKey(input, publicKey)
    println("公钥加密="+encryptByPublicKey)


//    val decryptByPrivateKey = RSACrypt.decryptByPrivateKey(encryptByPublicKey, privateKey)
//    println("私钥解密="+decryptByPrivateKey)

    val decryptByPublicKey = RSACrypt.decryptByPublicKey(encrypt, publicKey)
    println("私钥解密="+decryptByPublicKey)

}