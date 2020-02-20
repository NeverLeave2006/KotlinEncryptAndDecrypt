import MassageDigestUtil.md5
import MassageDigestUtil.sha1
import MassageDigestUtil.sha256
import java.security.MessageDigest
import kotlin.experimental.and

/**
 * 消息摘要
 */
object MassageDigestUtil {
    /**
     * md5加密工具
     */
    fun md5(input:String):String{
        val digest = MessageDigest.getInstance("MD5")
        val result = digest.digest(input.toByteArray())

//        val stringBuilder = StringBuilder()

//        //转成16进制
//        result.forEach {
//            val value=it
//            val hex=value.toInt() and 0xFF
//            val hexStr=Integer.toHexString(hex)
//            println(hexStr)
//            if(hexStr.length==1){
//                stringBuilder.append("0").append(hexStr)
//            }else{
//                stringBuilder.append(hexStr)
//            }
//        }
        return toHex(result)
    }

    /**
     * 转16进制加密工具
     */
    fun toHex(byteArray: ByteArray):String{
        return with(StringBuilder()){
            //转成16进制
            byteArray.forEach {
                val value=it
                val hex=value.toInt() and 0xFF
                val hexStr=Integer.toHexString(hex)
                println(hexStr)
                if(hexStr.length==1){
                    append("0").append(hexStr)
                }else{
                    append(hexStr)
                }
            }
            this.toString()
        }
    }

    /**
     * sha1摘要
     */
    fun sha1(input: String): String {
        val digest = MessageDigest.getInstance("SHA-1")
        val result = digest.digest(input.toByteArray())
        return toHex(result)
    }

    /**
     * sha256摘要
     */
    fun sha256(input: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val result = digest.digest(input.toByteArray())
        return toHex(result)
    }

}



fun main() {
    val input="黑马程序员"
    val s = md5(input)
    val sha1Str=sha1(input)
    val sha256Str=sha256(input)
    println(s.toString())
    println(s.toString().toByteArray().size)

    println(sha256Str)
    println(sha256Str.toString().toByteArray().size)

}