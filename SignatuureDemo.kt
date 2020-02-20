import com.sun.org.apache.xml.internal.security.utils.Base64
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature


object SignatuureDemo {
    //签名
    fun sign(privateKey: PrivateKey, input: String): String? {
        //获取数字签名实例对象
        val signature = Signature.getInstance("SHA256withRSA")
        signature.initSign(privateKey)
        //设置数据源

        signature.update(input.toByteArray())
        //签名
        val sign = signature.sign()
        return Base64.encode(sign)
    }

    fun verify(publicKey: PublicKey, input: String, s: String): Boolean {
        //校验
        val signature = Signature.getInstance("SHA256withRSA")
        //初始化签名
        signature.initVerify(publicKey)
        //传入数据源
        signature.update(input.toByteArray())
        //校验签名消息
        val verify = signature.verify(Base64.decode(s))
        return verify
    }

}

/**
 * 数字签名演示
 */
fun main() {
    val transformation="RSA"
    val pairGenerator = KeyPairGenerator.getInstance("RSA")
//    //生成密钥对
    val keyPair = pairGenerator.genKeyPair()
//    //公钥Base64转码
    val publicKey = keyPair.public
//    //私钥
    val privateKey = keyPair.private
    val input="name=邵广杰"
    val s = SignatuureDemo.sign(privateKey, input)
    println("sign="+s)

//    //校验
//    val signature = Signature.getInstance("SHA256withRSA")
//    //初始化签名
//    signature.initVerify(publicKey)
//    //传入数据源
//    signature.update(input.toByteArray())
//    //校验签名消息
//    val verify = signature.verify(Base64.decode(s))
    if (s != null) {
        val verify = SignatuureDemo.verify(publicKey, "name=邵广杰", s)
        println(verify)
    }

}