package com.eunmin.arrow

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

class FP4 {
    fun add(x: Int, y: Int): Option<Int> {
        if (x + y == 10) return None
        return Some(x + y)
    }

    fun run1() {
        val value = add(1, 2)
        println(value)
    }

    fun run2() {
        val value = Some(3)
        println(value)
    }

    fun run3() {
        println(Some(3))
    }

    fun run4() {
        val value = add(5, 5)
        println(value)
    }

    fun run5() {
        val value = None
        println(value)
    }
}