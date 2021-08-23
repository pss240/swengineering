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
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_welcome.button_welcome_drawmenu
import kotlinx.android.synthetic.main.fragment_welcome.layout_drawer_welcome
import kotlinx.android.synthetic.main.fragment_welcome.naviview_Welcome

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {
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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    //메뉴 네비게이션 코드 시작부
    lateinit var navController : NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        button_welcome_drawmenu.setOnClickListener{layout_drawer_welcome.openDrawer(GravityCompat.START)}
        naviview_Welcome.setNavigationItemSelectedListener(this)
        var item = arrayListOf(
            Data_Profile("Title1"),
            Data_Profile("Title2"),
            Data_Profile("Title3"),
            Data_Profile("Title4"),
            Data_Profile("Title5"),
            Data_Profile("Title6"),
            Data_Profile("Title7"),
            Data_Profile("Title8"),
            Data_Profile("Title9"),
            Data_Profile("Title10"),
            Data_Profile("Title11"),
            Data_Profile("Title12"),
            Data_Profile("Title13")
        )
        recyclerview_profile.layoutManager = LinearLayoutManager(requireContext())
        recyclerview_profile.adapter = CustomAdapter_Profile(item,requireContext())
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
                navController.navigate(R.id.action_welcomeFragment_to_writeEssay)
            }
        }
        return true
    }
    companion object {
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}