package com.example.opengldemo.openglutils

import android.opengl.GLES20
import android.opengl.GLES20.*
import android.util.Log
import java.nio.IntBuffer

/**
 * @author qinhaihang
 * @time   2020/3/7 19:15
 * @des
 *
 *@packgename com.example.opengldemo.openglutils
 */
object ShaderHelper {

    private const val TAG = "ShaderHelper"

    fun compileFragmentShader(fragmentShader: String): Int {
        return compileShader(GL_FRAGMENT_SHADER, fragmentShader)
    }

    fun compileVertexShader(vertexShader: String): Int {
        return compileShader(GL_VERTEX_SHADER, vertexShader)
    }

    fun compileShader(type: Int, shader: String): Int {

        var shaderObjectId = glCreateShader(type)

        getGLError("glCreateShader")

        if (shaderObjectId == 0) {
            Log.e(TAG, "create shader is failure")
            return 0
        }

        glShaderSource(shaderObjectId, shader)
        glCompileShader(shaderObjectId)

        if (checkShaderCompileStatus(shaderObjectId) == 0) {
            glDeleteShader(shaderObjectId)
            return 0
        }

        return shaderObjectId
    }

    fun checkShaderCompileStatus(shader: Int): Int {
        val compileStatus = IntArray(1)
        glGetShaderiv(shader, GL_COMPILE_STATUS, compileStatus, 0)
        return compileStatus[0]
    }

    fun getShaderCompileLog(shader: Int): String {
        return glGetShaderInfoLog(shader)
    }

    fun getGLError(method: String): Int {
        val error = glGetError()
        if(error != GL_NO_ERROR){
            Log.e(TAG,"$method ,error = $error")
        }
        return error
    }

    fun linkProgram(vertexShaderId: Int,fragmentShaderId: Int): Int{

        //创建OpenGL程序
        val programobjectId = glCreateProgram()

        if(programobjectId == 0){
            Log.e(TAG,"glCreateProgram fail")
            return 0
        }

        //链接着色器
        glAttachShader(programobjectId,vertexShaderId)
        glAttachShader(programobjectId,fragmentShaderId)

        glLinkProgram(programobjectId)

        return programobjectId
    }

    /**
     * 验证创建的openGL Program 是否正确
     */
    fun validateProgram(programId: Int): Boolean{
        glValidateProgram(programId)
        val result = IntArray(1)
        glGetShaderiv(programId, GL_VALIDATE_STATUS,result,0)
        
        Log.v(TAG,"result validate code is " + result[0] + "\n log : " + glGetProgramInfoLog(programId) )

        return result[0] != 0
    }

}