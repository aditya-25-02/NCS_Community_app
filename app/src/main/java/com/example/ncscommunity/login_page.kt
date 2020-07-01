package com.example.ncscommunity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login_page.*

class login_page : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        loginbtn.setOnClickListener {
            loginUser()
        }
        forgotbtn.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Password reset link")
            val view = layoutInflater.inflate(R.layout.dailogue_forgot_pass,null)
            builder.setView(view)
            val username = view.findViewById<EditText>(R.id.forgot_email)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { _, _ ->
                forgotpass(username)
            })
            builder.setNegativeButton("close", DialogInterface.OnClickListener { _, _ ->  })
            builder.show()
        }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser!=null) {
           startActivity(Intent(this,Main2Activity::class.java))
            Toast.makeText(baseContext,"Logging you in",
                           Toast.LENGTH_SHORT).show()
            finish()
        }
        else {
            Toast.makeText(baseContext,"Please Log in",
                           Toast.LENGTH_SHORT).show()
        }
    }

    private fun forgotpass(username:EditText) {
        if(username.text.toString().isEmpty()){
            username.error="Please enter email"
            username.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {
            username.error = "Please enter valid email"
            username.requestFocus()
            return
        }
        auth.sendPasswordResetEmail(username.text.toString()).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(baseContext,"Reset link sent",
                      Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(baseContext, "Error occured , please try again",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

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
                    Toast.makeText(baseContext,"Logging you in",
                          Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,Main2Activity::class.java))
                    finish()
                }
                else {
                    updateUI(null)
                }
            }
    }
}
