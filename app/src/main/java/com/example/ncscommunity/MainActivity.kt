package com.example.ncscommunity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        skipbtn.setOnClickListener{
            val i = Intent (this,login_page::class.java)
            startActivity(i)
        }
        create_acc.setOnClickListener{
            val j = Intent (this,signup_page::class.java)
            startActivity(j)
        }
    }
}
