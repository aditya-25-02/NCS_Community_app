package com.example.ncscommunity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup_page.*

class signup_page : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)

        // Initialize Firebase Auth
        auth = Firebase.auth

        signup_btn.setOnClickListener{
            signUpUser()
        }
    }

    private fun signUpUser (){
        if(user_input.text.toString().isEmpty()){
            user_input.error="Please enter Email"
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
        auth.createUserWithEmailAndPassword(user_input.text.toString(),pass_input.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this,login_page::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Sign-up failed.",
                        Toast.LENGTH_SHORT).show()
                }

            }
    }

}
