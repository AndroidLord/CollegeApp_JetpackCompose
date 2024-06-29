package com.example.collegeapp_jetpackcompose

class test {

    companion object A{
        var counterA = 0
    }
    object B{
        var counterB = 0
    }

}

fun main() {

    println(test.B.counterB)

    test.B.counterB = 10

    println(test.B.counterB)

    println(test.counterA)

    test.counterA = 10

    println(test.counterA)



}

