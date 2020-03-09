package com.example.opengldemo.utils

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception

/**
 * @author qinhaihang
 * @time   2020/2/29 23:44
 * @des
 *
 *@packgename com.example.opengldemo.utils
 */

fun readFileFromAssets(context: Context, fileName: String): String {
    val body = StringBuilder()

    var inputStream: InputStream? = null
    var inputStreamReader: InputStreamReader? = null
    var bufferedReader: BufferedReader? = null
    try {

        inputStream = context.assets.open(fileName)
        inputStreamReader = InputStreamReader(inputStream)
        bufferedReader = BufferedReader(inputStreamReader)

        var nextLine: String? = bufferedReader.readLine()

        while (nextLine != null){
            body.append(nextLine)
            body.append("\n")
            nextLine = bufferedReader.readLine()
        }

    }catch (e: Exception){
        e.printStackTrace()
    }finally {
        bufferedReader?.close()
        inputStreamReader?.close()
        inputStream?.close()
    }

    return body.toString()
}