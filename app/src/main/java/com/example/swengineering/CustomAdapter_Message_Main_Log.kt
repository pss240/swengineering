package com.example.swengineering

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_list_message_log.view.*

class Data_Message_Main_Log(val name : String, val content_count: String, val uid : String)

class CustomViewHolder_Message_Main_Log(v : View) : RecyclerView.ViewHolder(v) {
    val name = v.textView_custom_message_log_userName
    val content_count = v.textView_custom_message_log_content_count
}

class CustomAdapter_Message_Main_Log(
    val DataList: ArrayList<Data_Message_Main_Log>, val context: Context, val view: View
) : RecyclerView.Adapter<CustomViewHolder_Message_Main_Log>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder_Message_Main_Log {
        val cellForRow = LayoutInflater.from(context).inflate(R.layout.custom_list_message_log,parent,false)
        return CustomViewHolder_Message_Main_Log(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder_Message_Main_Log, position: Int){
        val curData = DataList[position]
        holder.name.text = curData.name
        holder.content_count.text = curData.content_count

        holder.itemView.setOnClickListener{
            Uid = curData.uid
            var navController : NavController = Navigation.findNavController(view)//반드시 함수 안에서 네비 선언해줘야하나? 이거 계속 선언해줄텐데
            navController.navigate(R.id.action_message_main_to_message_chat_list)
        }
    }

    override fun getItemCount(): Int = DataList.size
}