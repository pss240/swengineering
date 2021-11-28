package com.example.swengineering

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.swengineering.FB.FBRef
import com.example.swengineering.model.EssayModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_today_topic.*
import kotlinx.android.synthetic.main.fragment_welcome.button_welcome_drawmenu
import kotlinx.android.synthetic.main.fragment_welcome.layout_drawer_welcome
import kotlinx.android.synthetic.main.fragment_welcome.naviview_Welcome
import java.time.LocalDate
import java.time.format.DateTimeFormatter


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class TodayTopicFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {
    private var param1: String? = null
    private var param2: String? = null

    @RequiresApi(Build.VERSION_CODES.O)
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
    lateinit var RCAdapter : CustomAdapter_TodayTopic
    lateinit var items : ArrayList<Data_TodayTopic>
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
        textView_TodayTopic.setText(topic[now.dayOfMonth%3]) // 토픽
        items = arrayListOf()

//
//        imageButton_PlusDay.setOnClickListener {
//            now = now.plusDays(1)
//            textView_TodayTopic_Day.setText("$now")
//            textView_TodayTopic.setText(topic[now.dayOfMonth%3])
//        }
//        imageButton_MinusDay.setOnClickListener {
//            now = now.minusDays(1)
//            textView_TodayTopic_Day.setText("$now")
//            textView_TodayTopic.setText(topic[now.dayOfMonth%3])
//        }

        getdata()

        RCAdapter = CustomAdapter_TodayTopic(items,requireContext(),view)
        recyclerView_TodayTopic.layoutManager = LinearLayoutManager(requireContext())
        recyclerView_TodayTopic.adapter = RCAdapter//리스트에서 에세이뷰어플래그먼트 열기위해서 view인자추가

        button_toWrite.setOnClickListener {
            essayKey = ""
            navController.navigate(R.id.action_todayTopicFragment_to_writeEssay)
        }


    }

    fun getdata(){
        FBRef.essaysRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                items.clear()
                for(dataModel in dataSnapshot.children){
                    val item = dataModel.getValue(EssayModel::class.java)
                    items.add(0,Data_TodayTopic(item!!.title,item!!.nickname,item!!.thumb,dataModel.key))
                }

                RCAdapter.notifyDataSetChanged()
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
                navController.navigate(R.id.action_todayTopicFragment_to_myEssayPage)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_Subscribe -> {
                navController.navigate(R.id.action_todayTopicFragment_to_subscribeFragment)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_Message -> {
                navController.navigate(R.id.action_todayTopicFragment_to_message_main)
                layout_drawer_welcome.closeDrawers()
            }

            R.id.button_welcome_Settings -> {
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_Notice -> {
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