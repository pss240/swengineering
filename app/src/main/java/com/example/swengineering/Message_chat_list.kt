package com.example.swengineering

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.swengineering.FB.FBAuth
import com.example.swengineering.FB.FBRef
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_message_chat_list.*
import kotlinx.android.synthetic.main.fragment_welcome.button_welcome_drawmenu
import kotlinx.android.synthetic.main.fragment_welcome.layout_drawer_welcome
import kotlinx.android.synthetic.main.fragment_welcome.naviview_Welcome

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Message_chat_list.newInstance] factory method to
 * create an instance of this fragment.
 */
class Message_chat_list : Fragment(), NavigationView.OnNavigationItemSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_chat_list, container, false)
    }
    //메뉴 네비게이션 코드 시작부
    lateinit var navController : NavController
    lateinit var RCAdapter : CustomAdapter_Message_chat_list
    lateinit var items : ArrayList<Data_Message_chat_list>
    var logKey : String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        button_welcome_drawmenu.setOnClickListener{layout_drawer_welcome.openDrawer(GravityCompat.START)}
        naviview_Welcome.setNavigationItemSelectedListener(this)

        items = arrayListOf()

        FBRef.logRef.child(FBAuth.getUid()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(dataModel in dataSnapshot.children){
                    if(Uid.equals(dataModel.value)){
                        logKey = dataModel.key.toString()
                        Log.d("log key first",logKey)
                        getdata(logKey)
                        return
                    }
                }

                push()

            }



            override fun onCancelled(error: DatabaseError) {
                Log.w("WelcomeFragment", "Failed to read value.", error.toException())
            }
        })



        button_send.setOnClickListener {

            if(editText_msg.text.toString() != "") {
                FBRef.msgRef.child(logKey).push().setValue(nickname + ": " + editText_msg.text)
                editText_msg.setText("")
            }
            else
                Toast.makeText(it.context,"메시지을 입력해주세요", Toast.LENGTH_SHORT).show()

        }

        RCAdapter = CustomAdapter_Message_chat_list(items, requireContext(),view)
        recyclerview_message_chat_list.layoutManager = LinearLayoutManager(requireContext())
        recyclerview_message_chat_list.adapter = RCAdapter


    }

    fun push(){

        val pushedPostRef: DatabaseReference = FBRef.logRef.child(FBAuth.getUid()).push()
        logKey = pushedPostRef.key.toString()

        FBRef.logRef.child(FBAuth.getUid()).child(logKey).setValue(Uid)
        FBRef.logRef.child(Uid).child(logKey).setValue(FBAuth.getUid())

    }

    fun getdata(key : String) {
        Log.d("getdata logkey", key)

        FBRef.msgRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                items.clear()
                for (dataModel in dataSnapshot.children) {
                    if(dataModel.key == key){
                        for (dataModel2 in dataModel.children) {
                            val content = dataModel2.value
                            items.add(0,Data_Message_chat_list(content.toString()))
                        }
                    }
                }

                RCAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("WelcomeFragment", "Failed to read value.", error.toException())
            }
        })

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.button_welcome_MyEssay -> {
                Uid = FBAuth.getUid()
                navController.navigate(R.id.action_todayTopicFragment_to_myEssayPage)
                layout_drawer_welcome.closeDrawer(GravityCompat.START)
            }
            R.id.button_welcome_Subscriber -> {
                navController.navigate(R.id.action_todayTopicFragment_to_subscriberFragment)
                layout_drawer_welcome.closeDrawer(GravityCompat.START)
            }
            R.id.button_welcome_Message -> {
                navController.navigate(R.id.action_todayTopicFragment_to_message_main)
                layout_drawer_welcome.closeDrawer(GravityCompat.START)
            }

            R.id.button_welcome_to_main -> {
                navController.navigate(R.id.welcomeFragment)
                layout_drawer_welcome.closeDrawer(GravityCompat.START)
            }
            R.id.button_Logout -> {
                val context = getContext()
                val packageManager = context?.packageManager
                val intent = packageManager?.getLaunchIntentForPackage(context.packageName)
                val componentName = intent!!.component
                val mainIntent = Intent.makeRestartActivityTask(componentName)
                context.startActivity(mainIntent)
                Runtime.getRuntime().exit(0)
            }
        }
        return true
    }
    //메뉴 네비 끝부분

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Message_chat_list().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}