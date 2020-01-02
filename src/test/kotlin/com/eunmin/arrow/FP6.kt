package com.eunmin.arrow

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import java.lang.IllegalArgumentException

class FP6 {
    fun add(x: Int, y: Int): Either<Throwable, Int> {
        if (x + y == 10) Left(IllegalArgumentException("x와 y 합이 10이면 안됩니다"))
        return Right(x + y)
    }

    fun run1() {
        val value = add(1, 2)
        println(value)
    }

    fun run2() {
        val value = Right(3)
        println(value)
    }

    fun run3() {
        val value = add(5, 5)
        println(value)
    }
    
    fun run4() {
        val value = Left(IllegalArgumentException("x와 y 합이 10이면 안됩니다"))
        println(value)
    }
}