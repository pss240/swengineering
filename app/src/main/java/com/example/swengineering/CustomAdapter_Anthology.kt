package com.example.swengineering

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_list_anthology.view.*



class Data_Anthology(val Title:String, val name:String,val thumb:String)


class CustomViewHolder_Anthology(v: View) : RecyclerView.ViewHolder(v){
    val Title = v.textView_Anthology_TopicName
    val name = v.textView_Anthology_UserName
    val thumb = v.textView_Anthology_thumb
}


class CustomAdapter_Anthology (val DataList:ArrayList<Data_Anthology>,val context: Context, var view:View): RecyclerView.Adapter<CustomViewHolder_Anthology>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder_Anthology {
        val cellForRow = LayoutInflater.from(context).inflate(R.layout.custom_list_anthology,parent,false)
        return CustomViewHolder_Anthology(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder_Anthology, position: Int) {
        val curData = DataList[position]
        holder.Title.text = curData.Title
        holder.name.text = curData.name
        holder.thumb.text = curData.thumb
        holder.itemView.setOnClickListener{
            var navController : NavController = Navigation.findNavController(view)//반드시 함수 안에서 네비 선언해줘야하나? 이거 계속 선언해줄텐데
            navController.navigate(R.id.action_anthologyFragment_to_essay_viewer)
        }
    }

    override fun getItemCount(): Int = DataList.size

}
