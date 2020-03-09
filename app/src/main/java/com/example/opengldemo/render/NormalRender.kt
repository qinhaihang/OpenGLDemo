package com.example.opengldemo.render

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.Log
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class NormalRender: GLSurfaceView.Renderer {

    companion object{
        const val TAG:String = "NormalRender"
    }

    override fun onDrawFrame(gl: GL10?) {
        Log.d(TAG,"onDrawFrame")
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.d(TAG,"onSurfaceChanged")
        GLES20.glViewport(0,0,width, height) // 设置视口尺寸
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.d(TAG,"onSurfaceCreated")
        GLES20.glClearColor(1.0f,0f,0f,0f) // 清空使用的颜色，并且根据设置填充颜色
    }
}