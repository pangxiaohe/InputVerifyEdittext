package com.study.inputverifyedittext

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //输入完成监听
        verifyTv.setOnInputListener(object : VerificationCodeView.OnInputListener {
            override fun onInputOver() {
                Toast.makeText(this@MainActivity, "输入结果为：${verifyTv.getInputContent()}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
