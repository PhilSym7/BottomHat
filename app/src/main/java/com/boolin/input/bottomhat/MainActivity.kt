package com.boolin.input.bottomhat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.widget.Toast.LENGTH_SHORT
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.FirebaseUser
import android.R.attr.password
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.ActionBar
import android.util.Log
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_course.*
import android.widget.ArrayAdapter



class MainActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null

    var db = FirebaseFirestore.getInstance()

    lateinit var toolbar: ActionBar

    //list of course names
    val courses = mutableListOf<String?>()

    //stupid bools

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Start BottomNavBar
        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = navigationView

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        //End BottomNavBar

        mAuth = FirebaseAuth.getInstance()
        //mAuth?.signOut()

        var currentUser = mAuth?.currentUser

        //sign out button
//        val btnSignOut = btn_sign_out
//        btnSignOut.setOnClickListener {
//            signOut()
//        }

        initializeHome()

        updateUI(currentUser)
    }

    //starts app on home page
    private fun initializeHome(){
        toolbar.title = "Home"
        val homeFragment = HomeFragment.newInstance()
        openFragment(homeFragment)
    }

    //Listener for bottom Navigation Bar
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                initializeHome()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_courses -> {
                //changes title of action bar
                toolbar.title = "Course Select"
                //changes fragment
                val courseFragment = CourseFragment.newInstance()
                openFragment(courseFragment)
                getCourses()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun updateUI(user: FirebaseUser?){
//        val userNameTest = test_username
//        userNameTest.text = user?.email
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
        val i = Intent(this@MainActivity, loginActivity::class.java)
        startActivity(i)
    }

    fun checkDb(){
        db.collection("users")
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {


                        }
                    } else {

                    }
                })
    }

    fun initializeCourses(){
        val courseAdapter = ArrayAdapter<String>(this,
                R.layout.lv_course_item, courses)

        val listView: ListView = findViewById(R.id.course_listview)
        listView.adapter = courseAdapter
    }

    fun getCourses(){
        db.collection("subjects")
                .get()
                .addOnCompleteListener( { task ->
                    if (task.isSuccessful) {
                            for (document in task.result) {
                                var courseName: String? = document.id
                                courses.add(courseName)
                                //Toast.makeText(this, document.id, LENGTH_SHORT).show()
                                //Toast.makeText(this, courses.size.toString(), Toast.LENGTH_SHORT).show()
                            }
                        initializeCourses()
                    } else {
                        //Toast.makeText(this, "it didn't work", Toast.LENGTH_SHORT).show()
                    }
                })
    }

}
