package com.example.swengineering

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_list_message_chat.view.*

class Data_Message_chat_list(val send_or_recieve : String, val content : String)

class CustomViewHolder_Message_chat_list(v :View) : RecyclerView.ViewHolder(v){
    val send_or_recieve = v.textView_message_chat_list_send_recieve
    val content = v.textView_message_chat_list_content
}

class CustomAdapter_Message_chat_list (val DataList:ArrayList<Data_Message_chat_list>, val context: Context, var view: View):
    RecyclerView.Adapter<CustomViewHolder_Message_chat_list>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder_Message_chat_list {
        val cellForRow = LayoutInflater.from(context).inflate(R.layout.custom_list_message_chat,parent,false)
        return CustomViewHolder_Message_chat_list(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder_Message_chat_list, position: Int) {
        val curData = DataList[position]
        holder.send_or_recieve.text = curData.send_or_recieve
        holder.content.text = curData.content
        holder.itemView.setOnClickListener {
            var navController : NavController = Navigation.findNavController(view)//반드시 함수 안에서 네비 선언해줘야하나? 이거 계속 선언해줄텐데
            navController.navigate(R.id.action_message_chat_list_to_message_viewer)
        }
    }

    override fun getItemCount(): Int = DataList.size
}