package com.example.swengineering

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.swengineering.FB.FBAuth
import com.example.swengineering.FB.FBRef
import com.example.swengineering.model.EssayModel
import com.example.swengineering.model.UserModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_my_essay_page.*
import kotlin.math.log


class MyEssayPage : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var items: ArrayList<Data_My_essay_page>
    lateinit var RCAdapter: CustomAdapter_My_essay_page
    lateinit var arrayList: ArrayList<String>
    var Ecnt = 0
    var Tcnt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_essay_page, container, false)
    }

    //메뉴 네비게이션 코드 시작부
    lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        items = arrayListOf()
        arrayList = arrayListOf()



        navController = Navigation.findNavController(view)
        button_welcome_drawmenu.setOnClickListener { layout_drawer_welcome.openDrawer(GravityCompat.START) }
        naviview_Welcome.setNavigationItemSelectedListener(this)

        getData(view)
        getsubscribers()

        RCAdapter = CustomAdapter_My_essay_page(items, requireContext(), view)


        recyclerview_my_essay_todays_topic.layoutManager = LinearLayoutManager(requireContext())
        recyclerview_my_essay_todays_topic.adapter = RCAdapter

        button_mypage_editName.setOnClickListener {


            if (Uid == FBAuth.getUid()) {
                FBRef.UsersRef.child(Uid).setValue(
                    UserModel(
                        textView_mypage_user_id.text.toString(),
                        textView_mypage_user_name.text.toString(),
                        Uid
                    )
                )
                Toast.makeText(it.context, "정상적으로 변경되었습니다", Toast.LENGTH_SHORT).show()

            } else
                Toast.makeText(it.context, "권한이 없습니다", Toast.LENGTH_SHORT).show()


        }

        button_sendMsg.setOnClickListener {

            navController.navigate(R.id.action_myEssayPage_to_message_chat_list)

        }
        button_subscribe.setOnClickListener {

            if(arrayList.contains(Uid)){
                FBRef.subscribeRef.child(FBAuth.getUid()).child(Uid).removeValue()
            }
            else {
                FBRef.subscribeRef.child(FBAuth.getUid()).child(Uid).setValue("")
            }
        }
    }
        fun getsubscribers() {
            FBRef.subscribeRef.child(FBAuth.getUid())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        arrayList.clear()
                        for (dataModel in dataSnapshot.children) {
                            var item = dataModel.key

                            /*if(item!!.equals(Uid)){
                         return*/
                            arrayList.add(item.toString())
                        }
                    }
                    //                    FBRef.subscribeRef.child(FBAuth.getUid()).push().setValue(Uid)

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                        Log.w("WelcomeFragment", "Failed to read value.", error.toException())
                    }
                })
        }


        fun getInfo(v: View) {
            FBRef.UsersRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (dataModel in dataSnapshot.children) {
                        val item = dataModel.getValue(UserModel::class.java)

                        if (item!!.uid.equals(Uid)) {

                            var tv1 = v.findViewById<EditText>(R.id.textView_mypage_user_name)
                            var tv2 = v.findViewById<TextView>(R.id.textView_mypage_user_id)

                            tv1.setText(item!!.nickname)
                            tv2.setText(item!!.email)
                            break
                        }
                    }
                    var tv3 = v.findViewById<TextView>(R.id.textView_mypage_user_essay_count_value)
                    var tv4 = v.findViewById<TextView>(R.id.textView_mypage_user_like_count_value)

                    tv3.setText(Ecnt.toString())
                    tv4.setText(Tcnt.toString())

                    RCAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("WelcomeFragment", "Failed to read value.", error.toException())
                }
            })

        }


        fun getData(v: View) {
            FBRef.essaysRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    items.clear()
                    for (dataModel in dataSnapshot.children) {
                        val item = dataModel.getValue(EssayModel::class.java)
                        Log.d("UID", Uid)
                        Log.d("item's UID", item!!.uid)
                        if (item!!.uid.equals(Uid)) {
                            Ecnt++
                            Tcnt += item!!.thumb.toInt()
                            items.add(
                                0,
                                Data_My_essay_page(
                                    item!!.title,
                                    item!!.nickname,
                                    item!!.thumb,
                                    dataModel.key
                                )
                            )

                        }
                    }

                    getInfo(v)

                    RCAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("WelcomeFragment", "Failed to read value.", error.toException())
                }
            })
        }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.button_welcome_MyEssay -> {
                navController.navigate(R.id.action_myEssayPage_self)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_Subscribe -> {
                navController.navigate(R.id.action_myEssayPage_to_subscribeFragment)
                layout_drawer_welcome.closeDrawers()
            }
            R.id.button_welcome_Message -> {
                navController.navigate(R.id.action_myEssayPage_to_message_main)
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
        //메뉴 네비 끝부분

    }

    }