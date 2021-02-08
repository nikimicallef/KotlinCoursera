package week4.properties

var cnt = 0
val foo: Int
    get() = cnt++

fun main(args: Array<String>) {
    // The values should be different:
    println(foo)
    println(foo)
    println(foo)
}
