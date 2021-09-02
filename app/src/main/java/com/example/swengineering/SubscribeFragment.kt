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
import kotlinx.android.synthetic.main.fragment_subscribe.*
import kotlinx.android.synthetic.main.fragment_subscribe.button_welcome_drawmenu
import kotlinx.android.synthetic.main.fragment_subscribe.layout_drawer_welcome
import kotlinx.android.synthetic.main.fragment_subscribe.naviview_Welcome
import kotlinx.android.synthetic.main.fragment_welcome.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SubscribeFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {
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
        return inflater.inflate(R.layout.fragment_subscribe, container, false)
    }
    lateinit var navController : NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        button_welcome_drawmenu.setOnClickListener{layout_drawer_welcome.openDrawer(GravityCompat.START)}
        naviview_Welcome.setNavigationItemSelectedListener(this)
        var item = arrayListOf(
            Data_Subcribe(R.drawable.ic_baseline_person_24,"Title1","Lorem hello my name is haha nnice to meet you how are you im fine thank you and you??","51","42"),
            Data_Subcribe(R.drawable.ic_baseline_person_24,"Title2","Lorem hello my name is haha nnice to meet you how are you im fine thank you and you??","51","42"),
            Data_Subcribe(R.drawable.ic_baseline_person_24,"Title3","Lorem hello my name is haha nnice to meet you how are you im fine thank you and you??","51","42"),
            Data_Subcribe(R.drawable.ic_baseline_person_24,"Title4","Lorem hello my name is haha nnice to meet you how are you im fine thank you and you??","51","42"),
            Data_Subcribe(R.drawable.ic_baseline_person_24,"Title5","Lorem hello my name is haha nnice to meet you how are you im fine thank you and you??","51","42"),
            Data_Subcribe(R.drawable.ic_baseline_person_24,"Title6","Lorem hello my name is haha nnice to meet you how are you im fine thank you and you??","51","42"),
            Data_Subcribe(R.drawable.ic_baseline_person_24,"Title7","Lorem hello my name is haha nnice to meet you how are you im fine thank you and you??","51","42"),
            Data_Subcribe(R.drawable.ic_baseline_person_24,"Title8","Lorem hello my name is haha nnice to meet you how are you im fine thank you and you??","51","42"),
            Data_Subcribe(R.drawable.ic_baseline_person_24,"Title9","Lorem hello my name is haha nnice to meet you how are you im fine thank you and you??","51","42"),
            Data_Subcribe(R.drawable.ic_baseline_person_24,"Title10","Lorem hello my name is haha nnice to meet you how are you im fine thank you and you??","51","42"),
            Data_Subcribe(R.drawable.ic_baseline_person_24,"Title11","Lorem hello my name is haha nnice to meet you how are you im fine thank you and you??","51","42"),
            Data_Subcribe(R.drawable.ic_baseline_person_24,"Title12","Lorem hello my name is haha nnice to meet you how are you im fine thank you and you??","51","42"),
        )
        recyclerview_subscribe.layoutManager = LinearLayoutManager(requireContext())
        recyclerview_subscribe.adapter = CustomAdapter_Subscribe(item,requireContext())
        imageButton_toSubscriber.setOnClickListener {
            navController.navigate(R.id.action_subscribeFragment_to_subscriberFragment)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.button_welcome_MyEssay -> {
                navController.navigate(R.id.action_subscribeFragment_to_myEssayPage)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_Anthology -> {
                navController.navigate(R.id.action_subscribeFragment_to_anthologyFragment)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_Subscribe -> {
                navController.navigate(R.id.action_subscribeFragment_self)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_Message -> {
                navController.navigate(R.id.action_subscribeFragment_to_message_main)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_MyPage -> {
                navController.navigate(R.id.action_subscribeFragment_to_mypage)
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
    companion object {
        fun newInstance(param1: String, param2: String) =
            SubscribeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

}