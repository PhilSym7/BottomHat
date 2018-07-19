package com.boolin.input.bottomhat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.boolin.input.bottomhat.R.id.gone
import com.boolin.input.bottomhat.R.id.invisible
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import com.google.android.gms.tasks.OnFailureListener



class loginActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        //Text Fields
        val emailField = field_email
        val pwField = field_password
        val cpwField = field_password_confirm
        val firstNameField = field_first_name
        val lastNameField = field_last_name

        //buttons
        val btnSignIn = btn_sign_in
        val btnToCreateAccount = btn_to_create_account
        val btnCreateAccount = btn_create_account
        val btnToSignIn = btn_to_sign_in


        //sign in button
        btnSignIn.setOnClickListener{
            //sign in user with input from text fields
            var email: String = emailField.text.toString()
            var password: String = pwField.text.toString()
            signIn(email, password)
        }

        //to create account button
        btnToCreateAccount.setOnClickListener{
            //hide buttons
            btnSignIn.visibility = View.GONE
            btnToCreateAccount.visibility = View.GONE

            //make other text fields visible
            cpwField.visibility = View.VISIBLE
            firstNameField.visibility = View.VISIBLE
            lastNameField.visibility = View.VISIBLE

            //make other buttons visible
            btnCreateAccount.visibility = View.VISIBLE
            btnToSignIn.visibility = View.VISIBLE
        }

        //actual create account button
        btnCreateAccount.setOnClickListener{
            //create account with given inputs
            var email: String = emailField.text.toString()
            var password: String = pwField.text.toString()
            var confirmPassword: String = cpwField.text.toString()    //confirm password field

            if (password == confirmPassword){
                createAccount(email, password)
            } else {
                Toast.makeText(this, "passwords do not match", LENGTH_SHORT).show()
            }
        }

        //back to sign in button
        btnToSignIn.setOnClickListener {
            //hide extra text views
            cpwField.visibility = View.GONE
            firstNameField.visibility = View.GONE
            lastNameField.visibility = View.GONE

            //hide other buttons
            btnCreateAccount.visibility = View.GONE
            btnToSignIn.visibility = View.GONE

            //show original buttons
            btnSignIn.visibility = View.VISIBLE
            btnToCreateAccount.visibility = View.VISIBLE
        }
    }

    //Creates a new account with email and password
    private fun createAccount(email: String, password: String){
        mAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener(this) { task ->
            if (task.isSuccessful()){
                //Sign in was successful, signs in

                createDbUser()

                val i = (Intent(this@loginActivity, MainActivity::class.java))
                startActivity(i)
            } else {
                //Sign in failed
            }
        }
    }

    private fun signIn(email: String, password: String){
        mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, open main activity
                //val user = mAuth?.currentUser
                //opens main activity with signed in user
                val i = (Intent(this@loginActivity, MainActivity::class.java))
                startActivity(i)
            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //creates user object in firestore
    fun createDbUser(){
        //creates new user with first and last name
        var user = HashMap<String, Any>()
        user.put("first", field_first_name.text.toString())
        user.put("last", field_last_name.text.toString())

        //Add a new document with generated ID
        db.collection("users").add(user)
                .addOnSuccessListener{documentReference ->
                    Toast.makeText(this, "Document Added with ID: " + documentReference.id, LENGTH_SHORT)
                }
                .addOnFailureListener{documentReference ->
                    Toast.makeText(this, "Error adding document", LENGTH_SHORT)
                }
    }
}
