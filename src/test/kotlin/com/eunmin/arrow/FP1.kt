package com.eunmin.arrow

class FP1 {
    fun add(x: Int, y: Int): Int = x + y

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