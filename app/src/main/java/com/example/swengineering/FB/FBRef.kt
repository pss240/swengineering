package com.example.swengineering.FB

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FBRef {
    companion object{
        val database = Firebase.database
        val essaysRef = database.getReference("essays")
        val UsersRef = database.getReference("users")
        val commentRef = database.getReference("comment")

    }
}