package com.example.swengineering

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
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
import kotlinx.android.synthetic.main.fragment_join.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class JoinFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var mainActivity: MainActivity
    lateinit var navController: NavController
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_join, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        button_Join_Join.setOnClickListener {
            val email = input_Join_Email.text.toString()
            val password = input_Join_Password.text.toString()
            val nickname = input_Join_NickName.text.toString()

            if(email.isEmpty() or password.isEmpty()){

            }
            else{
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(mainActivity) { task ->
                        if (task.isSuccessful) {

                            FBRef.UsersRef.push().setValue(UserModel(FBAuth.getUid(),nickname))
                            navController.navigate(R.id.action_joinFragment_to_loginFragment)

                            Toast.makeText(it.context,"회원가입 성공", Toast.LENGTH_SHORT).show()

                        } else {
                            Toast.makeText(it.context,"회원가입 실패",Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }

    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            JoinFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}