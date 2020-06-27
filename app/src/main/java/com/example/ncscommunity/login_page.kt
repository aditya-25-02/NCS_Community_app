package com.example.ncscommunity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login_page.*

class login_page : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        // Initialize Firebase Auth
        auth = Firebase.auth
        loginbtn.setOnClickListener{
            loginUser()
        }
        //Check if already login
        /*public override fun onStart() {
            super.onStart()
            // Check if user is signed in (non-null) and update UI accordingly.
            val currentUser = auth.currentUser
            updateUI(currentUser)
        }*/
    }/*
    private fun updateUI(currentUser : FirebaseUser?) {

    }*/
    private fun loginUser (){
        if(user_input.text.toString().isEmpty()){
            user_input.error="Please enter Username/Email"
            user_input.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(user_input.text.toString()).matches()){
            user_input.error="Please enter valid email"
            user_input.requestFocus()
            return
        }
        if(pass_input.text.toString().isEmpty()){
            pass_input.error="Please enter the password"
            pass_input.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(user_input.text.toString(), pass_input.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Logging-in",
                        Toast.LENGTH_SHORT).show()
                    val i= Intent(this,Main2Activity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}
