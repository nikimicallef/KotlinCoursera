fun String.isNice(): Boolean {
    val doesntContainForbiddenString =  listOf("bu", "ba", "be").none { this.contains(it) }

    val contains3Vowels = count{ it in "aeiou" } >= 3

    val consecutiveDoubleLetter = zipWithNext().any { pair: Pair<Char, Char> -> pair.first == pair.second };

    return if (doesntContainForbiddenString && contains3Vowels) {
        true
    } else if (doesntContainForbiddenString && consecutiveDoubleLetter) {
        true
    } else contains3Vowels && consecutiveDoubleLetter
}
