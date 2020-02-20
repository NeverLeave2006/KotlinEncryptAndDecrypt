
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64
import java.io.ByteArrayOutputStream
import java.security.Key
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher

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

    val transformation="RSA"
    //如何生成密钥对:密钥对

    //密钥对生成器
    val pairGenerator = KeyPairGenerator.getInstance("RSA")
    //生成密钥对
    val keyPair = pairGenerator.genKeyPair()
    //公钥Base64转码
    val publicKey = keyPair.public
    //私钥
    val privateKey = keyPair.private

    println("publicKey="+Base64.encode(publicKey.encoded))
    println("privateKey="+Base64.encode(privateKey.encoded))

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