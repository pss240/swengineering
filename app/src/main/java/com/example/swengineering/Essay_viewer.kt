package com.example.swengineering

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.swengineering.FB.FBAuth
import com.example.swengineering.FB.FBRef
import com.example.swengineering.model.CommentModel
import com.example.swengineering.model.EssayModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_essay_viewer.*
import kotlinx.android.synthetic.main.fragment_welcome.button_welcome_drawmenu
import kotlinx.android.synthetic.main.fragment_welcome.layout_drawer_welcome
import kotlinx.android.synthetic.main.fragment_welcome.naviview_Welcome

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Essay_viewer.newInstance] factory method to
 * create an instance of this fragment.
 */
class Essay_viewer : Fragment(), NavigationView.OnNavigationItemSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var chk = 0

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
        return inflater.inflate(R.layout.fragment_essay_viewer, container, false)
    }
    //메뉴 네비게이션 코드 시작부
    lateinit var navController : NavController
    lateinit var item2 : EssayModel
    lateinit var comment_items : ArrayList<Data_Essay_viewer_comment>
    lateinit var RCAdapter : CustomAdapter_Essay_viewer_comment

    fun getdata(v : View){
        FBRef.essaysRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if(dataSnapshot.child(essayKey).hasChildren()) {
                    item2 = dataSnapshot.child(essayKey).getValue(EssayModel::class.java)!!

                    var tv1 = v.findViewById<TextView>(R.id.textView_essay_viewer_topic)
                    var tv2 = v.findViewById<TextView>(R.id.textView_essay_viewer_writer)
                    var tv3 = v.findViewById<TextView>(R.id.textView_essay_viewer_text)
                    var tv4 = v.findViewById<TextView>(R.id.textview_thumb)
                    tv1.setText(item2.title)
                    tv2.setText(item2.nickname)
                    tv3.setText(item2.body)
                    tv4.setText(item2.thumb)
                }
                else
                    chk = 1
            }


            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("WelcomeFragment", "Failed to read value.", error.toException())
            }
        })

        if(chk == 1)
            navController.popBackStack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        comment_items= arrayListOf()
        navController = Navigation.findNavController(view)
        button_welcome_drawmenu.setOnClickListener{layout_drawer_welcome.openDrawer(GravityCompat.START)}
        naviview_Welcome.setNavigationItemSelectedListener(this)


        getdata(view)

        textView_essay_viewer_writer.setOnClickListener {
            FBRef.essaysRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val item = dataSnapshot.child(essayKey).getValue(EssayModel::class.java)
                    if(item != null){
                        Uid = item!!.uid
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("WelcomeFragment", "Failed to read value.", error.toException())
                }
            })
            navController.navigate(R.id.action_essay_viewer_to_myEssayPage)
        }

        button_essay_update.setOnClickListener {
            if(FBAuth.getUid() == item2.uid) {
                navController.navigate(R.id.action_essay_viewer_to_writeEssay)
            }
            else
                Toast.makeText(it.context, "권한이 없습니다", Toast.LENGTH_SHORT).show()
        }

        button_essay_delete.setOnClickListener {
            if(FBAuth.getUid() == item2.uid) {
                FBRef.essaysRef.child(essayKey).removeValue()
                Toast.makeText(it.context, "삭제 성공", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
            else
                Toast.makeText(it.context, "권한이 없습니다", Toast.LENGTH_SHORT).show()
        }

        button_essay_viewer_like.setOnClickListener {

            FBRef.essaysRef.child(essayKey)
                .setValue(
                    EssayModel(
                        item2.title,
                        item2.body,
                        item2.uid,
                        item2.nickname,
                        item2.time,
                        (item2.thumb.toInt() + 1).toString()
                    )
                )

        }
        button_comment_plus.setOnClickListener {
            if(editText_essay_viewer_write_comment.text.toString() != "") {
                FBRef.commentRef
                    .child(essayKey)
                    .push()
                    .setValue(
                        CommentModel(
                            nickname,
                            editText_essay_viewer_write_comment.text.toString(),
                            "0"
                        )
                    )
                editText_essay_viewer_write_comment.setText("")
                Toast.makeText(it.context,"댓글이 등록되었습니다",Toast.LENGTH_SHORT).show()

            }
        }

        comment_getdata(view)

        RCAdapter = CustomAdapter_Essay_viewer_comment(comment_items,requireContext())
        recycleView_essay_viewer_comment.layoutManager = LinearLayoutManager(requireContext())
        recycleView_essay_viewer_comment.adapter = RCAdapter
    }


    fun comment_getdata(v : View){
        FBRef.commentRef.child(essayKey).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                comment_items.clear()
                for(dataModel in dataSnapshot.children){
                    val item = dataModel.getValue(CommentModel::class.java)
                    comment_items.add(Data_Essay_viewer_comment(item!!.commentNick,item!!.commentBody,item!!.commentThumb,dataModel.key!!, FBAuth.getUid()))
                }

                val cc = v.findViewById<TextView>(R.id.textView_essay_viewer_comment_count)

                cc.setText(dataSnapshot.childrenCount.toString())

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
                Uid = FBAuth.getUid()
                navController.navigate(R.id.action_todayTopicFragment_to_myEssayPage)
                layout_drawer_welcome.closeDrawer(GravityCompat.START)
            }
            R.id.button_welcome_Subscriber -> {
                navController.navigate(R.id.action_todayTopicFragment_to_subscriberFragment)
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
                auth.signOut()
                navController.popBackStack(R.id.loginFragment,true,false)
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
         * @return A new instance of fragment Essay_viewer.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Essay_viewer().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}