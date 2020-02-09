package org.prep.example

import org.prep.example.model.Stack

const val BREAK: Int = 100

fun main(args: Array<String>) {
    args.iterator().forEach { word ->
        println("word is: ${word}")
        val permutations = providePermutations(word)
        var size: Int = 0
        val iterator = permutations.iterator()
        while(iterator.hasNext()) {
            val nextOne = iterator.next()
            if (size + nextOne.length > BREAK) {
                println()
                size = 0
            } else {
                size += (nextOne.length + 1)
            }
            print("${nextOne} ")
        }
        println()
    }
}

fun providePermutations(word: String): Set<String> {
    var listWords: MutableSet<String> = mutableSetOf()

    var stackFrag: Stack<String> = Stack()
    var frag = String(word.toCharArray())
    stackFrag.push(frag.substring(frag.length - 1))

    while (!stackFrag.isEmpty()) {
        frag = stackFrag.pop() ?: ""
        if (frag.length == 0) {
            // this should not happen
        } else {
            var charIndex = word.length - frag.length
            if (charIndex > 0) {
                var prevChar = word.substring(charIndex - 1, charIndex)
                for (j in 0..frag.length) {
                    var toPush = frag.substring(0, j) + prevChar + frag.substring(j, frag.length)
                    if (toPush.length == word.length) {
                        listWords.add(toPush)
                    } else {
                        stackFrag.push(toPush)
                    }
                }
            }
        }
    }

    return listWords
}


fun factorial(number: Int): Long {
    var counter: Long = 1
    for (a in 1..number) counter *= a
    println("factorial of ${number} = ${counter}")
    return counter
}