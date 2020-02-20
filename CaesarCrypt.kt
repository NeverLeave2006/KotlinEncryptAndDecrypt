import java.lang.StringBuilder

//凯撒加密算法
class CaesarCrypt{
    /**
     * 加密
     * @param command 要加密的字符串
     * @param key 凯撒偏移密钥
     */
    fun enCrypt(command:String,key:Int):String{
        val charArray=command.toCharArray()
        return with(StringBuilder()){
            charArray.forEach {
                //遍历每一个字符，对ascii偏移
                val c=it
                //获取字符ascii
                var ascii=c.toInt()

                //移动
                ascii+=key
                //转成字符
                val result=ascii.toChar()
                append(result)
            }
            toString()
        }

    }

    /**
     * 解密
     * @param command 密文字符串
     * @param key 凯撒偏移密钥
     */
    fun deCrypt(command:String,key:Int):String{
        val charArray=command.toCharArray()
        return with(StringBuilder()){
            charArray.forEach {
                //遍历每一个字符，对ascii偏移
                val c=it
                //获取字符ascii
                var ascii=c.toInt()

                //反方向移动
                ascii-=key
                //转成字符
                val result=ascii.toChar()
                append(result)
            }
            toString()
        }

    }
}

fun main() {
    //移动字符

    //命令
    val command="I love you"
    val key=2;//密钥
    val charArray=command.toCharArray()
    val encrypt=CaesarCrypt().enCrypt(command,key)
    println(encrypt)
    val decrypt=CaesarCrypt().deCrypt(encrypt,key)
    println(decrypt)

}