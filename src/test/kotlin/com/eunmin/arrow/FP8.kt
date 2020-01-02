package com.eunmin.arrow

import arrow.fx.IO

class FP8 {
    fun add(x: Int, y: Int): IO<Int> = IO {
        println("x = $x, y = $y")
        x + y
    }

    fun run1() {
        val value = add(1, 2)
        println(value)
    }

    fun run2() {
        // val value = ... 1, 2를 출력하고 1, 2를 더한 결과를 리턴하겠다는 값
        // println(value)
    }

    fun run3() {
        val value = add(1, 2)
        println(value.unsafeRunSync()) // effect
    }
}