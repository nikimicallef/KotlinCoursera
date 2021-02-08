package week4.assignment

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

data class Rational(val n: BigInteger, val d: BigInteger) : Comparable<Rational> {
    private val numerator: BigInteger
    private val denominator: BigInteger

    init {
        require(d != ZERO) { "Denominator must not be 0" }

        val gcd = n.gcd(d)

        numerator = (n / gcd)
        denominator = (d / gcd)
    }

    operator fun plus(r2: Rational): Rational {
        val cd = denominator * r2.denominator
        val numerator = (numerator * (cd / denominator)) + (r2.numerator * (cd / r2.denominator))

        val gcd = numerator.gcd(cd)

        return Rational(numerator / gcd, cd / gcd)
    }

    operator fun minus(r2: Rational): Rational {
        val cd = denominator * r2.denominator
        val numerator = (numerator * (cd / denominator)) - (r2.numerator * (cd / r2.denominator))

        val gcd = numerator.gcd(cd)

        return Rational(numerator / gcd, cd / gcd)
    }

    operator fun times(r2: Rational): Rational {
        val numerator = this.numerator * r2.numerator
        val denominator = this.denominator * r2.denominator

        val gcd = numerator.gcd(denominator)

        return Rational(numerator / gcd, denominator / gcd)
    }

    operator fun div(r2: Rational): Rational {
        val numerator = this.numerator * r2.denominator;
        val denominator = this.denominator * r2.numerator

        val gcd = numerator.gcd(denominator)

        return Rational(numerator / gcd, denominator / gcd)
    }

    operator fun unaryMinus(): Rational {
        return Rational(numerator.negate(), denominator)
    }

    override fun compareTo(other: Rational): Int {
        val cd = denominator * other.denominator
        val r1Num = this.numerator * (cd / this.denominator)
        val r2Num = other.numerator * (cd / other.denominator)

        return if (r1Num == r2Num) 0 else if (r1Num > r2Num) 1 else -1
    }

    override fun toString(): String {
        if (denominator == ONE) {
            return "$numerator"
        }

        val gcd = numerator.gcd(denominator)

        val num = numerator / gcd
        val den = denominator / gcd

        return "$num/$den"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Rational) {
            return false
        }
        val thisGT0 = this.numerator >= ZERO && this.denominator > ZERO
        val otherGT0 = other.numerator >= ZERO && other.denominator > ZERO

        if (thisGT0 != otherGT0) {
            return false
        }

        return numerator.abs() == other.numerator.abs() && denominator.abs() == other.denominator.abs()
    }

    override fun hashCode(): Int {
        var result = numerator.hashCode()
        result = 31 * result + denominator.hashCode()
        return result
    }


}

infix fun Int.divBy(denominator: Int): Rational {
    return Rational(this.toBigInteger(), denominator.toBigInteger())
}

infix fun Long.divBy(denominator: Long): Rational {
    return Rational(toBigInteger(), denominator.toBigInteger())
}

infix fun BigInteger.divBy(denominator: BigInteger): Rational {
    return Rational(this, denominator)
}

fun String.toRational(): Rational {
    if (this.contains("/")) {
        val (numer, denom) = split("/")

        val gcd = numer.toBigInteger().gcd(denom.toBigInteger()).abs()

        return if (numer.toBigInteger() < ZERO && denom.toBigInteger() < ZERO) {
            Rational(numer.toBigInteger().abs() / gcd, denom.toBigInteger().abs() / gcd)
        } else if (denom.toBigInteger() < ZERO) {
            Rational(numer.toBigInteger().negate() / gcd, denom.toBigInteger().negate() / gcd)
        } else {
            Rational(numer.toBigInteger() / gcd, denom.toBigInteger() / gcd)
        }

    } else {
        return Rational(this.toBigInteger(), ONE)
    }
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println(
        "912016490186296920119201192141970416029".toBigInteger() divBy
                "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2
    )
}
