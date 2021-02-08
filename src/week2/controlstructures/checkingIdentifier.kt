package week2.controlstructures

// Implement the function that checks whether a string is a valid identifier.
// A valid identifier is a non-empty string that starts with a letter or underscore and consists of only letters, digits and underscores.

fun isValidIdentifier(s: String): Boolean {
    fun validCharacter(ch: Char) = ch.isLetterOrDigit() || ch == '_'

    if(s.isEmpty() || s[0].isDigit() ) {
        return false
    }

    for(ch in s){
        if(!validCharacter(ch)) return false
    }

    return true
}

fun main(args: Array<String>) {
    println(isValidIdentifier("name"))   // true
    println(isValidIdentifier("_name"))  // true
    println(isValidIdentifier("_12"))    // true
    println(isValidIdentifier(""))       // false
    println(isValidIdentifier("012"))    // false
    println(isValidIdentifier("no$"))    // false
}

