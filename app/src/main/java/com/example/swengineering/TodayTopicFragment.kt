package com.example.swengineering

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_anthology.*
import kotlinx.android.synthetic.main.fragment_today_topic.*
import kotlinx.android.synthetic.main.fragment_welcome.*
import kotlinx.android.synthetic.main.fragment_welcome.button_welcome_drawmenu
import kotlinx.android.synthetic.main.fragment_welcome.layout_drawer_welcome
import kotlinx.android.synthetic.main.fragment_welcome.naviview_Welcome
import java.time.LocalDate


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TodayTopicFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {
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
        return inflater.inflate(R.layout.fragment_today_topic, container, false)
    }

    lateinit var navController : NavController
    @RequiresApi(Build.VERSION_CODES.O)
    var now = LocalDate.now()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        button_welcome_drawmenu.setOnClickListener{layout_drawer_welcome.openDrawer(GravityCompat.START)}
        naviview_Welcome.setNavigationItemSelectedListener(this)
        textView_TodayTopic_Day.setText("$now")
        imageButton_PlusDay.setOnClickListener {
            now = now.plusDays(1)
            textView_TodayTopic_Day.setText("$now")
        }
        imageButton_MinusDay.setOnClickListener {
            now = now.minusDays(1)
            textView_TodayTopic_Day.setText("$now")
        }
        var item = arrayListOf(
            Data_TodayTopic("Title1","User1","10"),
            Data_TodayTopic("Title2","User2","10"),
            Data_TodayTopic("Title3","User3","10"),
            Data_TodayTopic("Title4","User4","10"),
            Data_TodayTopic("Title5","User5","10"),
            Data_TodayTopic("Title6","User6","10"),
            Data_TodayTopic("Title7","User7","10"),
            Data_TodayTopic("Title8","User8","10"),
            Data_TodayTopic("Title9","User9","10"),
            Data_TodayTopic("Title10","User10","10"),
            Data_TodayTopic("Title11","User11","10"),
            Data_TodayTopic("Title12","User12","10"),
            Data_TodayTopic("Title13","User13","10"),
            Data_TodayTopic("Title14","User14","10"),
            )
        recyclerView_TodayTopic.layoutManager = LinearLayoutManager(requireContext())
        recyclerView_TodayTopic.adapter = CustomAdapter_TodayTopic(item,requireContext())
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.button_welcome_MyEssay -> {
                layout_drawer_welcome.closeDrawers()
                println("Myessay")
            }
            R.id.button_welcome_Anthology -> {
                layout_drawer_welcome.closeDrawers()
                navController.navigate(R.id.action_welcomeFragment_to_anthologyFragment)
            }
            R.id.button_welcome_Subscribe -> {
                layout_drawer_welcome.closeDrawers()
                println("Subscribe")
            }
            R.id.button_welcome_Message -> {
                layout_drawer_welcome.closeDrawers()
                println("Message")

            }
            R.id.button_welcome_MyPage -> {
                layout_drawer_welcome.closeDrawers()
                println("My Page")
            }
            R.id.button_welcome_Settings -> {
                layout_drawer_welcome.closeDrawers()
                println("Settings")

            }
            R.id.button_welcome_Notice -> {
                layout_drawer_welcome.closeDrawers()
                println("Notice")

            }
            R.id.button_welcome_test -> {
                layout_drawer_welcome.closeDrawers()
            }
        }
        return true
    }
    //메뉴 네비 끝부분
    companion object {
        fun newInstance(param1: String, param2: String) =
            TodayTopicFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}