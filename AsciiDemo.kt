import java.lang.StringBuilder

fun main() {
    //获取字符ascii码
    val c:Char='a'
    //字符转十进制
    val value:Int=c.toInt()
    println(value)

    //获取多个字符
    val str ="I Love You"
    //遍历每一个字符的ascii
    val array=str.toCharArray()
    val stringBuilder=StringBuilder()
//    for(ch in array){
//        val result=ch.toInt()
//        stringBuilder.append(result.toString()+" ")
//    }
    with(stringBuilder){
        for(ch in array){
            val result=ch.toInt()
            stringBuilder.append(result.toString()+" ")
        }
    }
    println(stringBuilder)
}