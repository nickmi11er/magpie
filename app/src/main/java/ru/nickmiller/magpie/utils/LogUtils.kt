package ru.nickmiller.magpie.utils

import android.util.Log

private val TEST = "TEST_LOG"
private val MAIN = "MAIN_LOG"

fun testLog(msg: String) = Log.d(TEST, msg)

fun mainLog(msg: String) = Log.d(MAIN, msg)


 
