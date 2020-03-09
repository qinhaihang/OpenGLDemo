package com.example.opengldemo.render

import android.content.Context
import android.opengl.GLES10.glPointSize
import android.opengl.GLES20
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.util.Log
import com.example.opengldemo.activity.VertexActivity
import com.example.opengldemo.openglutils.ShaderHelper
import com.example.opengldemo.utils.readFileFromAssets
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class VertexRender: GLSurfaceView.Renderer {

    companion object{
        const val TAG: String = "VertexRender"
        const val U_COLOR = "u_Color"
        const val A_POSITION = "a_Position"
        const val POSITION_COMPONENT_COUNT = 2
    }

    //定义顶点,OpenGL中的坐标系都是 -1 到 1，显示在屏幕上需要切换坐标
    /*private val tableVertexs = floatArrayOf(
        //第一个三角形
        0.0f,0.0f,
        9.0f,14.0f,
        0.0f,14.0f,

        //第二个三角形
        0.0f,0.0f,
        9.0f,0.0f,
        9.0f,14.0f,

        //线
        0.0f,7.0f,
        9.0f,7.0f,

        //点
        4.5f,2.0f,
        4.5f,12.0f

    )*/

    //转化映射之后
    private val tableVertexs = floatArrayOf(
        //第一个三角形
        -0.5f,-0.5f,
        0.5f,0.5f,
        -0.5f,0.5f,

        //第二个三角形
        -0.5f,-0.5f,
        0.5f,-0.5f,
        0.5f,0.5f,

        //线
        -0.5f,0.0f,
        0.5f,0.0f,

        //点
        0f,-0.25f,
        0f,0.25f
    )

    private var mContext: Context
    private var program: Int = 0
    private var uColorLocation = 0
    private var aPositionLocation = 0
    private var tableVertexBuffer: FloatBuffer? = null

    constructor(context: Context){

        mContext = context

    }

    //需要在onCreate 回调中调用
    private fun createShader(context: Context) {
        tableVertexBuffer = ByteBuffer.allocateDirect(tableVertexs.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        tableVertexBuffer?.put(tableVertexs)
        tableVertexBuffer?.position(0)

        val vertexShader = readFileFromAssets(context, "simple_vertex_shader.glsl")
        Log.d(TAG, vertexShader)

        val vertexShaderId = ShaderHelper.compileVertexShader(vertexShader)
        val vertexShaderLog = ShaderHelper.getShaderCompileLog(vertexShaderId)
        Log.d(TAG, "vertexShaderId = $vertexShaderId , vertexLShaderLog = $vertexShaderLog")

        val fragmentShader = readFileFromAssets(context, "simple_fragment_shader.glsl")
        Log.d(TAG, fragmentShader)

        val fragmentShaderId = ShaderHelper.compileFragmentShader(fragmentShader)
        val fragmentShaderLog = ShaderHelper.getShaderCompileLog(fragmentShaderId)
        Log.d(TAG, "fragmentShaderId = $fragmentShaderId , fragmentShaderLog = $fragmentShaderLog")

        //着色器链接进入OpenGL
        program = ShaderHelper.linkProgram(vertexShaderId, fragmentShaderId)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(0f,0f,0f,0f) // 清空使用的颜色，并且根据设置填充颜色
        createShader(mContext)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) =
        glViewport(0,0,width, height) // 设置视口尺寸

    override fun onDrawFrame(gl: GL10?) {
        glClear(GLES20.GL_COLOR_BUFFER_BIT)

        //将程序加入到OpenGLES2.0环境
        glUseProgram(program)

        //获取属性的位置
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION)
        //关联属性和顶点数据
        glVertexAttribPointer(aPositionLocation,
            POSITION_COMPONENT_COUNT,GL_FLOAT,false,0,tableVertexBuffer)
        glEnableVertexAttribArray(aPositionLocation)

        //获得一个uniform的位置
        uColorLocation = glGetUniformLocation(program, U_COLOR)

        //两个分量一个顶点，绘制前六个顶点（组成两个三角形）
        glUniform4f(uColorLocation,1.0f,1.0f,1.0f,1.0f)
        glDrawArrays(GL_TRIANGLES,0,6)

        //画一条横线
        glUniform4f(uColorLocation,1.0f,0.0f,0.0f,1.0f)
        glDrawArrays(GL_LINES,6,2)

        //绘制两个点,点是需要设置大小的
        glUniform4f(uColorLocation,0.0f,0.0f,1.0f,1.0f)
        //glPointSize(10f) 设置点大小无效
        glDrawArrays(GL_POINTS,8,1)

        glUniform4f(uColorLocation,1.0f,0.0f,0.0f,1.0f)
        glDrawArrays(GL_POINTS,9,1)

        //禁止顶点
        //GLES20.glDisableVertexAttribArray(aPositionLocation)
    }

}