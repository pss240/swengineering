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
import com.example.swengineering.model.UserModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kotlinx.android.synthetic.main.fragment_message_main.*
import kotlinx.android.synthetic.main.fragment_message_main.button_welcome_drawmenu
import kotlinx.android.synthetic.main.fragment_message_main.layout_drawer_welcome
import kotlinx.android.synthetic.main.fragment_message_main.naviview_Welcome
import com.google.firebase.database.ValueEventListener


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Message_main : Fragment(), NavigationView.OnNavigationItemSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var item_log : ArrayList<Data_Message_Main_Log>
    lateinit var RCAdapter : CustomAdapter_Message_Main_Log
    var key = ""

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
        return inflater.inflate(R.layout.fragment_message_main, container, false)
    }

    //메뉴 네비게이션 코드 시작부
    lateinit var navController : NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        item_log = arrayListOf()


        RCAdapter = CustomAdapter_Message_Main_Log(item_log,requireContext(), view)

        getData(view)

        navController = Navigation.findNavController(view)
        button_welcome_drawmenu.setOnClickListener{layout_drawer_welcome.openDrawer(GravityCompat.START)}
        naviview_Welcome.setNavigationItemSelectedListener(this)

        recyclerview_message_log.layoutManager = LinearLayoutManager(requireContext())
        recyclerview_message_log.adapter = RCAdapter

    }

    fun getData(v:View){
        FBRef.logRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                item_log.clear()

                for (dataModel in dataSnapshot.children) {
                    if (FBAuth.getUid() == dataModel.key) {

                        for (dataModel2 in dataModel.children) {
                            val item = dataModel2.key
                            FBRef.UsersRef.addValueEventListener(object : ValueEventListener {

                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    for (dataModel3 in dataSnapshot.children) {

                                        if(dataModel2.value.toString() == dataModel3.getValue(UserModel::class.java)?.uid.toString()) {
                                            item_log.add(0, Data_Message_Main_Log(dataModel3.getValue(UserModel::class.java)!!.email, dataModel3.getValue(UserModel::class.java)!!.nickname,dataModel3.getValue(UserModel::class.java)!!.uid))
                                            break
                                        }
                                    }

                                    RCAdapter.notifyDataSetChanged()
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    // Failed to read value
                                    Log.w(
                                        "WelcomeFragment",
                                        "Failed to read value.",
                                        error.toException()
                                    )
                                }
                            })

                        }
                    }

                    RCAdapter.notifyDataSetChanged()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
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
                navController.navigate(R.id.subscriberFragment)
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
                auth.signOut()
                navController.popBackStack(R.id.loginFragment,true,false)
            }
        }
        return true
    }
    //메뉴 네비 끝부분

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Message_main().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}