//package com.hollys.todoList.test
//
//import java.util.*
//
//object Test0622 {
//    @JvmStatic
//    fun main(args: Array<String>) {
//        val binaryReps = TreeMap<Char, String>();
//
//        for (c in 'A'..'F') {
//            val binary = Integer.toBinaryString(c.toInt());
//            binaryReps[c] = binary;
//        }
//
//        for ((key, value) in binaryReps) {
//            println("$key = $value");
//        }
//        print("binaryReps => $binaryReps")
//    }
//}