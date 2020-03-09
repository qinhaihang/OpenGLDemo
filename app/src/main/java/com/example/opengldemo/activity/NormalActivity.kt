package com.example.opengldemo.activity

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.opengldemo.R
import com.example.opengldemo.openglutils.getOpenGLVersion
import com.example.opengldemo.render.NormalRender

class NormalActivity : AppCompatActivity() {

    companion object{
        const val TAG = "NormalActivity"
    }

    private lateinit var mGlSurfaceView: GLSurfaceView
    private var isSetRenderer:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal)
        initView()
        configGL()
    }

    private fun initView() {
        mGlSurfaceView = findViewById<GLSurfaceView>(R.id.gl_surface_view)
    }

    private fun configGL() {
        //获取支持的版本
        val openGLVersion = getOpenGLVersion(this)
        Log.d(TAG, "openGLVersion is $openGLVersion")
        //配置gl使用的版本
        mGlSurfaceView.setEGLContextClientVersion(openGLVersion)
        val normalRender = NormalRender()
        mGlSurfaceView.setRenderer(normalRender)
        isSetRenderer = true
        //配置 Render 中 onDrawFrame 的执行方式
        mGlSurfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY //根据需求请求
        //mGlSurfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY //一直绘制

    }

    override fun onResume() {
        super.onResume()
        if(isSetRenderer) mGlSurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        if(isSetRenderer) mGlSurfaceView.onResume()
    }

}
