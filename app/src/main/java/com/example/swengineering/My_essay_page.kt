package com.example.swengineering

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_my_essay_page.*
import kotlinx.android.synthetic.main.fragment_my_essay_page.button_welcome_drawmenu
import kotlinx.android.synthetic.main.fragment_my_essay_page.layout_drawer_welcome
import kotlinx.android.synthetic.main.fragment_my_essay_page.naviview_Welcome


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
        var sub_button = view.findViewById<Button>(R.id.button_subscribe)



        navController = Navigation.findNavController(view)
        button_welcome_drawmenu.setOnClickListener { layout_drawer_welcome.openDrawer(GravityCompat.START) }
        naviview_Welcome.setNavigationItemSelectedListener(this)

        getData(view)
        getsubscribers(sub_button)

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
                sub_button.setText("구독")
            }
            else {
                FBRef.subscribeRef.child(FBAuth.getUid()).child(Uid).setValue("")
                sub_button.setText("구독 중")
            }
        }
        button_unregister.setOnClickListener {
            val inflater :LayoutInflater = LayoutInflater.from(it.context)
            val view = inflater.inflate(R.layout.unreg_warning,null)

            val alertDialog = AlertDialog.Builder(it.context)
                .setTitle("회원탈퇴")
                .create()
            val button_Positive = view.findViewById<Button>(R.id.button_Positive)
            button_Positive.setOnClickListener {
                alertDialog.dismiss()
                if (Uid == FBAuth.getUid()) {
                    val user = Firebase.auth.currentUser!!
                    user.delete()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(it.context, "정상적으로 탈퇴되었습니다", Toast.LENGTH_SHORT).show()
                                val context = getContext()
                                val packageManager = context?.packageManager
                                val intent = packageManager?.getLaunchIntentForPackage(context.packageName)
                                val componentName = intent!!.component
                                val mainIntent = Intent.makeRestartActivityTask(componentName)
                                context.startActivity(mainIntent)
                                Runtime.getRuntime().exit(0)
                            }
                        }
                } else
                    Toast.makeText(it.context, "권한이 없습니다", Toast.LENGTH_SHORT).show()
            }

            val button_Negative = view.findViewById<Button>(R.id.button_Negative)
            button_Negative.setOnClickListener {
                alertDialog.dismiss()
            }
            alertDialog.setView(view)
            alertDialog.show()
        }
    }
        fun getsubscribers(sub_button : Button) {
            FBRef.subscribeRef.child(FBAuth.getUid())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        arrayList.clear()
                        for (dataModel in dataSnapshot.children) {
                            var item = dataModel.key

                            arrayList.add(item.toString())
                        }
                        if(arrayList.contains(Uid))
                            sub_button.setText("구독 중")
                        else
                            sub_button.setText("구독")
                    }
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
                Uid = FBAuth.getUid()
                navController.navigate(R.id.action_todayTopicFragment_to_myEssayPage)
                layout_drawer_welcome.closeDrawer(GravityCompat.START)
            }
            R.id.button_welcome_Subscriber -> {
                navController.navigate(R.id.subscriberFragment)
                layout_drawer_welcome.closeDrawer(GravityCompat.START)
            }
            R.id.button_welcome_Message -> {
                navController.navigate(R.id.action_todayTopicFragment_to_message_main)
                layout_drawer_welcome.closeDrawer(GravityCompat.START)
            }

            R.id.button_welcome_to_main -> {
                navController.navigate(R.id.welcomeFragment)
                layout_drawer_welcome.closeDrawer(GravityCompat.START)
            }
            R.id.button_Logout -> {
                val context = getContext()
                val packageManager = context?.packageManager
                val intent = packageManager?.getLaunchIntentForPackage(context.packageName)
                val componentName = intent!!.component
                val mainIntent = Intent.makeRestartActivityTask(componentName)
                context.startActivity(mainIntent)
                Runtime.getRuntime().exit(0)
            }
        }
        return true
        //메뉴 네비 끝부분

    }

    }