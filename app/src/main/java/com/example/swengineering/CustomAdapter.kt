package com.example.swengineering

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_list_subscirbe.view.*

class Data(val profile:Int, val Title:String, val summary:String,val thumb:String,val comment:String)


class CustomViewHolder(v:View) : RecyclerView.ViewHolder(v){
    val profile = v.imageView_Subscribe_User
    val Title = v.textView_Subscribe_Title
    val summary = v.textView_Subscribe_summary
    val thumb = v.textView_Subscribe_thumbup
    val comment = v.textView_Subscribe_comment
}


class CustomAdapter (val DataList:ArrayList<Data>): RecyclerView.Adapter<CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val cellForRow = LayoutInflater.from(parent.context).inflate(R.layout.custom_list_subscirbe,parent,false)


        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = DataList.size


//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

//        val profile = view.findViewById<ImageView>(R.id.imageView_Subscribe_User)
//        val Title = view.findViewById<TextView>(R.id.textView_Subscribe_Title)
//        val summary = view.findViewById<TextView>(R.id.textView_Subscribe_summary)
//        val thumb = view.findViewById<TextView>(R.id.textView_Subscribe_thumbup)
//        val comment = view.findViewById<TextView>(R.id.textView_Subscribe_comment)
//        val data = DataList[position]
//
//        profile.setImageResource(data.profile)
//        Title.text = data.Title
//        summary.text = data.summary
//        thumb.text = data.thumb
//        comment.text = data.comment


    }
}