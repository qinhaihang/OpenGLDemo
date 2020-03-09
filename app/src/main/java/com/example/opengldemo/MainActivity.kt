package com.example.opengldemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.opengldemo.activity.NormalActivity
import com.example.opengldemo.activity.VertexActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun click(view: View) {

        when(view.id){
            R.id.btn_normal -> toNormalActivity()
            R.id.btn_vertex -> toVertexActivity()
        }

    }

    private fun toVertexActivity() {
        startActivity(Intent(this,VertexActivity::class.java))
    }

    fun toNormalActivity(){
        //startActivity(Intent(this,NormalActivity::class.java))
        startActivity(Intent(this,NormalActivity().javaClass))
    }
}
