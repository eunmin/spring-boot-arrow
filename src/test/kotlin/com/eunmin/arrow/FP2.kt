package com.eunmin.arrow

import java.lang.IllegalArgumentException

class FP2 {
    fun add(x: Int, y: Int): Int {
        if (x + y == 10) throw IllegalArgumentException("x와 y 합이 10이면 안됩니다")
        return x + y
    }

    fun run1() {
        val value = add(1, 2)
        println(value)
    }

    fun run2() {
        val value = 3
        println(value)
    }

    fun run3() {
        println(3)
    }
}