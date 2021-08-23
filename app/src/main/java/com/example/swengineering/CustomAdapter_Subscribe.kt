package com.example.swengineering

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_list_subscirbe.view.*

class Data_Subcribe(val profile:Int, val Title:String, val summary:String,val thumb:String,val comment:String)


class CustomViewHolder_Subscribe(v:View) : RecyclerView.ViewHolder(v){
    val profile = v.imageView_Subscribe_User
    val Title = v.textView_Subscribe_Title
    val summary = v.textView_Subscribe_summary
    val thumb = v.textView_Subscribe_thumbup
    val comment = v.textView_Subscribe_comment
}


class CustomAdapter_Subscribe (val DataList:ArrayList<Data_Subcribe>,val context:Context): RecyclerView.Adapter<CustomViewHolder_Subscribe>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder_Subscribe {
        val cellForRow = LayoutInflater.from(context).inflate(R.layout.custom_list_subscirbe,parent,false)
        return CustomViewHolder_Subscribe(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder_Subscribe, position: Int) {
        val curData = DataList[position]
        holder.profile.setImageResource(curData.profile)
        holder.Title.text = curData.Title
        holder.summary.text = curData.summary
        holder.thumb.text = curData.thumb
        holder.comment.text = curData.comment

        holder.itemView.setOnClickListener{
            Toast.makeText(context,curData.Title,Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = DataList.size

    }
