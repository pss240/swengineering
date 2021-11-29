package com.example.swengineering

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

lateinit var auth: FirebaseAuth
lateinit var nickname : String
lateinit var essayKey : String
lateinit var Uid : String
var topic= arrayOf<String>(
    "오늘 하루 기억에 남는 일이 있나요?","오늘은 어떤 음식을 드셨나요?", "하고 싶은 일이 있으신가요?"
)

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         auth = Firebase.auth

    }
}