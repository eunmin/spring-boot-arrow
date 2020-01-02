package com.eunmin.arrow

import arrow.core.None
import arrow.core.Option
import arrow.core.Some

class FP5 {
    fun add(x: Int, y: Int): Option<Int> {
        if (x + y == 10) return None
        return Some(x + y)
    }

    fun run() {
        val x = add(1, 2)
        val y = x + 3
        println(y)
    }

    fun run2() {
        val x = add(5, 5)
        val y = add(x, 1)
        println(y)
    }
}