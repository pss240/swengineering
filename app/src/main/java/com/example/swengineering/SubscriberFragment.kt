package com.example.swengineering

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.swengineering.FB.FBAuth
import com.example.swengineering.FB.FBRef
import com.example.swengineering.model.EssayModel
import com.example.swengineering.model.UserModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_subscriber.*
import kotlinx.android.synthetic.main.fragment_subscriber.button_welcome_drawmenu
import kotlinx.android.synthetic.main.fragment_subscriber.layout_drawer_welcome
import kotlinx.android.synthetic.main.fragment_subscriber.naviview_Welcome
import kotlinx.android.synthetic.main.fragment_welcome.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SubscriberFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var subscribers : ArrayList<Data_Subscriber>
    lateinit var RCAdapter : CustomAdapter_Subscriber


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_subscriber, container, false)
    }


    lateinit var navController : NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        button_welcome_drawmenu.setOnClickListener{layout_drawer_welcome.openDrawer(GravityCompat.START)}
        naviview_Welcome.setNavigationItemSelectedListener(this)
        subscribers = arrayListOf()


        getdata()


        RCAdapter = CustomAdapter_Subscriber(subscribers,requireContext(), view)
        recyclerview_subscriber.layoutManager = LinearLayoutManager(requireContext())
        recyclerview_subscriber.adapter = RCAdapter
    }


    fun getdata(){
        FBRef.subscribeRef.child(FBAuth.getUid()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                subscribers.clear()
                for(dataModel in dataSnapshot.children){
                    val item = dataModel.key

                    //구독자 닉네임 찾기
                    getNickname(item.toString())

                }

                RCAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("WelcomeFragment", "Failed to read value.", error.toException())
            }
        })
    }

    fun getNickname(key : String){
        FBRef.UsersRef.child(key).child("nickname").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var name = dataSnapshot.getValue(String::class.java)
                subscribers.add(0,Data_Subscriber(name.toString(),key))
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
                navController.navigate(R.id.action_subscriberFragment_to_myEssayPage)
                layout_drawer_welcome.closeDrawer(GravityCompat.START)
            }
            R.id.button_welcome_Subscriber -> {
                navController.navigate(R.id.action_subscriberFragment_self)
                layout_drawer_welcome.closeDrawer(GravityCompat.START)
            }
            R.id.button_welcome_Message -> {
                navController.navigate(R.id.action_subscriberFragment_to_message_main)
                layout_drawer_welcome.closeDrawer(GravityCompat.START)
            }

            R.id.button_welcome_to_main -> {
                navController.navigate(R.id.welcomeFragment)
                layout_drawer_welcome.closeDrawer(GravityCompat.START)
            }
            R.id.button_Logout -> {
                auth.signOut()
                navController.popBackStack(R.id.loginFragment,true,false)
            }
        }
        return true
    }

}