fun main() {
    val ch:Char='A'
    //获取字符ascii码
    val ascii=ch.toInt()
    println(ascii)
    //二进制
    val binary=Integer.toBinaryString(ascii)
    println(binary)//八位，一个英文字符占位
}