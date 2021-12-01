package com.example.swengineering

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.swengineering.FB.FBAuth
import com.example.swengineering.FB.FBRef
import com.example.swengineering.model.EssayModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_welcome.button_welcome_drawmenu
import kotlinx.android.synthetic.main.fragment_welcome.layout_drawer_welcome
import kotlinx.android.synthetic.main.fragment_welcome.naviview_Welcome
import kotlinx.android.synthetic.main.fragment_write_essay.*
import java.time.LocalDate

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class WriteEssay : Fragment(), NavigationView.OnNavigationItemSelectedListener {
    private var param1: String? = null
    private var param2: String? = null
    private var thumb : String? = null

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
        return inflater.inflate(R.layout.fragment_write_essay, container, false)
    }
    //메뉴 네비게이션 코드 시작부
    lateinit var navController : NavController
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        button_welcome_drawmenu.setOnClickListener{layout_drawer_welcome.openDrawer(GravityCompat.START)}
        naviview_Welcome.setNavigationItemSelectedListener(this)
        var item : EssayModel
        var chk = 0

        var now = LocalDate.now()
        input_TopicName.setText(topic[now.dayOfMonth%3])

        if(essayKey != ""){

            FBRef.essaysRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(chk == 0) {
                        item = dataSnapshot.child(essayKey).getValue(EssayModel::class.java)!!

                        input_TopicName.setText(item.title)
                        input_TopicContents.setText(item.body)
                        thumb = item.thumb
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("WelcomeFragment", "Failed to read value.", error.toException())
                }
            })
        }

        button_write_essay_save.setOnClickListener {
            val title = input_TopicName.text.toString()
            val body = input_TopicContents.text.toString()
            var uid = FBAuth.getUid()
            val time = FBAuth.getTime()
            chk = 1

            if(essayKey == "")
                FBRef.essaysRef.push().setValue(EssayModel(title, body, uid, nickname, time, "0"))
            else
                FBRef.essaysRef.child(essayKey).setValue(EssayModel(title, body, uid, nickname, time,thumb!!))
            navController.popBackStack()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.button_welcome_MyEssay -> {
                Uid = FBAuth.getUid()
                navController.navigate(R.id.action_writeEssay_to_myEssayPage)
                layout_drawer_welcome.closeDrawer(GravityCompat.START)
            }
            R.id.button_welcome_Subscriber -> {
                navController.navigate(R.id.action_writeEssay_to_subscriberFragment)
                layout_drawer_welcome.closeDrawer(GravityCompat.START)
            }
            R.id.button_welcome_Message -> {
                navController.navigate(R.id.action_writeEssay_to_message_main)
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
        fun newInstance(param1: String, param2: String) =
            WriteEssay().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}