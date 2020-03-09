package com.example.opengldemo.activity

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.opengldemo.R
import com.example.opengldemo.openglutils.getOpenGLVersion
import com.example.opengldemo.render.VertexRender

class VertexActivity : AppCompatActivity() {

    companion object{
        const val TAG: String = "VertexActivity"
    }

    lateinit var glSurfaceView: GLSurfaceView
    lateinit var vertexRender: VertexRender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vertex)
        initView()
        initOpenGL()
    }

    private fun initView() {
        glSurfaceView = findViewById(R.id.gl_surface_view)
    }

    private fun initOpenGL() {
        //获取支持的版本
        val openGLVersion = getOpenGLVersion(this)
        Log.d(TAG, "openGLVersion is $openGLVersion")
        //配置gl使用的版本
        glSurfaceView.setEGLContextClientVersion(2)
        vertexRender = VertexRender(this)
        glSurfaceView.setRenderer(vertexRender)
        //配置 Render 中 onDrawFrame 的执行方式
        glSurfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY //根据需求请求
    }
}
