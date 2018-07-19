package com.boolin.input.bottomhat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.boolin.input.bottomhat.R.id.btn_open_course_select
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.FirebaseUser
import android.R.attr.password
import android.support.v4.app.FragmentActivity
import android.util.Log


class MainActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        //mAuth?.signOut()

        var currentUser = mAuth?.currentUser

        //button that opens the course selection page
        val openCourseSelector = btn_open_course_select
        //onclick listener for the button
        openCourseSelector.setOnClickListener {
            //starts new intent
            val i = Intent(this@MainActivity, CourseSelection::class.java)
            startActivity(i)

        }

        //sign out button
        val btnSignOut = btn_sign_out
        btnSignOut.setOnClickListener {
            signOut()
            val i = Intent(this@MainActivity, loginActivity::class.java)
            startActivity(i)
        }

        updateUI(currentUser)
    }

    fun updateUI(user: FirebaseUser?){
        val userNameTest = test_username
        userNameTest.text = user?.email
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        var currentUser = mAuth?.currentUser

        updateUI(currentUser)
        if (currentUser != null){

        } else { //if user is not signed in (null), open sign in activity
            val i = Intent(this@MainActivity, loginActivity::class.java)
            startActivity(i)
        }
    }

    private fun signOut(){
        mAuth?.signOut()
    }
}