package com.example.swengineering

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

lateinit var auth: FirebaseAuth
lateinit var nickname : String
lateinit var essayKey : String
var topic= arrayOf<String>(
    "Topic 1","Topic 2", "Topic 3"
)

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         auth = Firebase.auth

    }
}