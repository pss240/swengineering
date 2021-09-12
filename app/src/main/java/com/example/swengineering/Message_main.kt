package com.example.swengineering

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_freeboard.*
import kotlinx.android.synthetic.main.fragment_message_main.*
import kotlinx.android.synthetic.main.fragment_message_main.button_welcome_drawmenu
import kotlinx.android.synthetic.main.fragment_message_main.layout_drawer_welcome
import kotlinx.android.synthetic.main.fragment_message_main.naviview_Welcome


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Message_main : Fragment(), NavigationView.OnNavigationItemSelectedListener {
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
        return inflater.inflate(R.layout.fragment_message_main, container, false)
    }

    //메뉴 네비게이션 코드 시작부
    lateinit var navController : NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var item = arrayListOf(
            Data_Message_Main_Profile(R.drawable.ic_baseline_person_24,"User1"),
            Data_Message_Main_Profile(R.drawable.ic_baseline_person_24,"User2"),
            Data_Message_Main_Profile(R.drawable.ic_baseline_person_24,"User3"),
            Data_Message_Main_Profile(R.drawable.ic_baseline_person_24,"User4"),
            Data_Message_Main_Profile(R.drawable.ic_baseline_person_24,"User5"),
            Data_Message_Main_Profile(R.drawable.ic_baseline_person_24,"User6"),
            Data_Message_Main_Profile(R.drawable.ic_baseline_person_24,"User7"),
            Data_Message_Main_Profile(R.drawable.ic_baseline_person_24,"User8"),
            Data_Message_Main_Profile(R.drawable.ic_baseline_person_24,"User9"),
            Data_Message_Main_Profile(R.drawable.ic_baseline_person_24,"User10"),
            Data_Message_Main_Profile(R.drawable.ic_baseline_person_24,"User11"),
            Data_Message_Main_Profile(R.drawable.ic_baseline_person_24,"User12")
        )

        var item_log = arrayListOf(
            Data_Message_Main_Log("User1", "1 message"),
            Data_Message_Main_Log("User2", "2 message"),
            Data_Message_Main_Log("User3", "3 message"),
            Data_Message_Main_Log("User4", "4 message"),
            Data_Message_Main_Log("User5", "5 message"),
            Data_Message_Main_Log("User6", "6 message"),
            Data_Message_Main_Log("User7", "7 message"),
            Data_Message_Main_Log("User8", "8 message"),
            Data_Message_Main_Log("User9", "9 message"),
            Data_Message_Main_Log("User10", "10 message"),
            Data_Message_Main_Log("User11", "11 message"),
            Data_Message_Main_Log("User12", "12 message"),
        )

        navController = Navigation.findNavController(view)
        button_welcome_drawmenu.setOnClickListener{layout_drawer_welcome.openDrawer(GravityCompat.START)}
        naviview_Welcome.setNavigationItemSelectedListener(this)

        recyclerview_message_subscriber.layoutManager = LinearLayoutManager(requireContext()).also { it.orientation = LinearLayoutManager.HORIZONTAL }
        recyclerview_message_subscriber.adapter = CustomAdapter_Message_Main_Profile(item,requireContext(),view)

        recyclerview_message_log.layoutManager = LinearLayoutManager(requireContext())
        recyclerview_message_log.adapter = CustomAdapter_Message_Main_Log(item_log,requireContext(),view)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.button_welcome_MyEssay -> {
                navController.navigate(R.id.action_message_main_to_myEssayPage)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_Anthology -> {
                navController.navigate(R.id.action_message_main_to_anthologyFragment)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_Subscribe -> {
                navController.navigate(R.id.action_message_main_to_subscribeFragment)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_Message -> {
                navController.navigate(R.id.action_message_main_self)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_MyPage -> {
                navController.navigate(R.id.action_message_main_to_mypage)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_Settings -> {
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_Notice -> {
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_test -> {
                layout_drawer_welcome.closeDrawers()
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