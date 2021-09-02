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
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_welcome.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Message_send : Fragment(), NavigationView.OnNavigationItemSelectedListener {
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
        return inflater.inflate(R.layout.fragment_message_send, container, false)
    }
    //메뉴 네비게이션 코드 시작부
    lateinit var navController : NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        button_welcome_drawmenu.setOnClickListener{layout_drawer_welcome.openDrawer(GravityCompat.START)}
        naviview_Welcome.setNavigationItemSelectedListener(this)
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Message_send.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Message_send().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}