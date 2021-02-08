package week5.assignment.game2048

/*
 * This function moves all the non-null elements to the beginning of the list
 * (by removing nulls) and merges equal elements.
 * The parameter 'merge' specifies the way how to merge equal elements:
 * it returns a new element that should be present in the resulting list
 * instead of two merged elements.
 *
 * If the function 'merge("a")' returns "aa",
 * then the function 'moveAndMergeEqual' transforms the input in the following way:
 *   a, a, b -> aa, b
 *   a, null -> a
 *   b, null, a, a -> b, aa
 *   a, a, null, a -> aa, a
 *   a, null, a, a -> aa, a
 *
 * You can find more examples in 'TestGame2048Helper'.
*/
fun <T : Any> List<T?>.moveAndMergeEqual(merge: (T) -> T): List<T> {

    val valuesWithoutNulls = this.filterNotNull().toList()

    val result: MutableList<T> = mutableListOf()

    var skipNext = false

    for(index in valuesWithoutNulls.indices) {
        when {
            skipNext -> {
                skipNext = false
            }
            index == valuesWithoutNulls.size-1 -> {
                result.add(valuesWithoutNulls[index])
            }
            valuesWithoutNulls[index] == valuesWithoutNulls[index+1] -> {
                result.add(merge(valuesWithoutNulls[index]))
                skipNext = true
            }
            valuesWithoutNulls[index] != valuesWithoutNulls[index+1] -> {
                result.add(valuesWithoutNulls[index])
            }
        }
    }

    return result
}

