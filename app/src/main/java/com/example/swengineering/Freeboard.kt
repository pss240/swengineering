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
import kotlinx.android.synthetic.main.fragment_freeboard.button_welcome_drawmenu


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Freeboard : Fragment(), NavigationView.OnNavigationItemSelectedListener {
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
        return inflater.inflate(R.layout.fragment_freeboard, container, false)
    }

    //메뉴 네비게이션 코드 시작부
    lateinit var navController : NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var item = arrayListOf(
            Data_Freeboard("Title1","User1","10"),
            Data_Freeboard("Title2","User2","10"),
            Data_Freeboard("Title3","User3","10"),
            Data_Freeboard("Title4","User4","10"),
            Data_Freeboard("Title5","User5","10"),
            Data_Freeboard("Title6","User6","10"),
            Data_Freeboard("Title7","User7","10"),
            Data_Freeboard("Title8","User8","10"),
            Data_Freeboard("Title9","User9","10"),
            Data_Freeboard("Title10","User10","10"),
            Data_Freeboard("Title11","User11","10"),
            Data_Freeboard("Title12","User12","10"),
            Data_Freeboard("Title13","User13","10"),
            Data_Freeboard("Title14","User14","10")
        )

        navController = Navigation.findNavController(view)
        button_welcome_drawmenu.setOnClickListener{layout_drawer_welcome.openDrawer(GravityCompat.START)}
        naviview_Welcome.setNavigationItemSelectedListener(this)

        recyclerview_Freeboard.layoutManager = LinearLayoutManager(requireContext())
        recyclerview_Freeboard.adapter = CustomAdapter_Freeboard(item,requireContext(),view)

        button_write_freetopic_essay.setOnClickListener {
            navController.navigate(R.id.action_freeboard_to_writeEssay)
        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.button_welcome_MyEssay -> {
                navController.navigate(R.id.action_freeboard_to_myEssayPage)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_Anthology -> {
                navController.navigate(R.id.action_freeboard_to_anthologyFragment)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_Subscribe -> {
                navController.navigate(R.id.action_freeboard_to_subscribeFragment)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_Message -> {
                navController.navigate(R.id.action_freeboard_to_message_main)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_MyPage -> {
                navController.navigate(R.id.action_freeboard_to_mypage)
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
            Freeboard().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}