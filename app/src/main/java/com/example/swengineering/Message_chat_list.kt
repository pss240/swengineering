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
import kotlinx.android.synthetic.main.fragment_message_chat_list.*
import kotlinx.android.synthetic.main.fragment_welcome.*
import kotlinx.android.synthetic.main.fragment_welcome.button_welcome_drawmenu
import kotlinx.android.synthetic.main.fragment_welcome.layout_drawer_welcome
import kotlinx.android.synthetic.main.fragment_welcome.naviview_Welcome

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Message_chat_list.newInstance] factory method to
 * create an instance of this fragment.
 */
class Message_chat_list : Fragment(), NavigationView.OnNavigationItemSelectedListener {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_chat_list, container, false)
    }
    //메뉴 네비게이션 코드 시작부
    lateinit var navController : NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        button_welcome_drawmenu.setOnClickListener{layout_drawer_welcome.openDrawer(GravityCompat.START)}
        naviview_Welcome.setNavigationItemSelectedListener(this)

        var item = arrayListOf(
            Data_Message_chat_list("send", "chatting"),
            Data_Message_chat_list("post", "chatting"),
            Data_Message_chat_list("send", "chatting"),
            Data_Message_chat_list("post", "chatting"),
            Data_Message_chat_list("send", "chatting"),
            Data_Message_chat_list("post", "chatting"),
            Data_Message_chat_list("send", "chatting"),
            Data_Message_chat_list("post", "chatting"),
            Data_Message_chat_list("send", "chatting"),
            Data_Message_chat_list("post", "chatting"),
            Data_Message_chat_list("send", "chatting"),
            Data_Message_chat_list("post", "chatting"),
            Data_Message_chat_list("send", "chatting"),
        )


        recyclerview_message_chat_list.layoutManager = LinearLayoutManager(requireContext())
        recyclerview_message_chat_list.adapter = CustomAdapter_Message_chat_list(item, requireContext(),view)


    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.button_welcome_MyEssay -> {
                navController.navigate(R.id.action_message_chat_list_to_myEssayPage)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_Anthology -> {
                navController.navigate(R.id.action_message_chat_list_to_anthologyFragment)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_Subscribe -> {
                navController.navigate(R.id.action_message_chat_list_to_subscribeFragment)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_Message -> {
                navController.navigate(R.id.action_message_chat_list_to_message_main)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_MyPage -> {
                navController.navigate(R.id.action_message_chat_list_to_mypage)
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

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Message_chat_list().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}