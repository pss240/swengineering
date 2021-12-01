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
import com.example.swengineering.FB.FBAuth
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_welcome.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class WelcomeFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {
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
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }
    lateinit var navController : NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        button_welcome_drawmenu.setOnClickListener{layout_drawer_welcome.openDrawer(GravityCompat.START)}
        naviview_Welcome.setNavigationItemSelectedListener(this)



        textView.setText(nickname)

        button_todaytopic.setOnClickListener {
            navController.navigate(R.id.action_welcomeFragment_to_todayTopicFragment)
        }

        button_yourtopic.setOnClickListener {
            Uid = FBAuth.getUid()
            navController.navigate(R.id.action_welcomeFragment_to_myEssayPage)
        }
    }
    companion object {
        fun newInstance(param1: String, param2: String) =
            WelcomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.button_welcome_MyEssay -> {
                Uid = FBAuth.getUid()
                navController.navigate(R.id.action_welcomeFragment_to_myEssayPage)
                layout_drawer_welcome.closeDrawer(GravityCompat.START)
            }
            R.id.button_welcome_Subscriber -> {
                navController.navigate(R.id.action_welcomeFragment_to_subscriberFragment)
                layout_drawer_welcome.closeDrawer(GravityCompat.START)
            }
            R.id.button_welcome_Message -> {
                navController.navigate(R.id.action_welcomeFragment_to_message_main)
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
