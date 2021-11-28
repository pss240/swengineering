package com.example.swengineering

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.swengineering.FB.FBAuth
import com.example.swengineering.FB.FBRef
import com.example.swengineering.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment(){

    lateinit var mainActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_login,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)


        button_login.setOnClickListener {
            val email = input_ID?.text.toString()
            val password = input_Password?.text.toString()

            if(email.isEmpty() or password.isEmpty()){
                Toast.makeText(it.context,"아이디 또는 비밀번호를 입력하시오!!",Toast.LENGTH_SHORT).show()

            }
            else{
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(mainActivity) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(it.context,"로그인 성공",Toast.LENGTH_SHORT).show()

                            FBRef.UsersRef.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {

                                    for(dataModel in dataSnapshot.children){

                                        val item = dataModel.getValue(UserModel::class.java)

                                        if(FBAuth.getUid().equals(item?.uid)){
                                            nickname = item!!.nickname
                                            Toast.makeText(it.context, nickname,Toast.LENGTH_SHORT).show()
                                            break
                                        }


                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    // Failed to read value
                                    Log.w("WelcomeFragment", "Failed to read value.", error.toException())
                                }
                            })

                            it.findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment)
                        } else {
                            Toast.makeText(it.context,"로그인 실패",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        button_join.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.joinFragment,null))
    }



}

