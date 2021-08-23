package com.example.swengineering

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_list_profile.view.*



class Data_Profile(val Title:String)


class CustomViewHolder_Profile(v: View) : RecyclerView.ViewHolder(v){
    val Title = v.textView_Profile_TopicName
}


class CustomAdapter_Profile (val DataList:ArrayList<Data_Profile>,val context: Context): RecyclerView.Adapter<CustomViewHolder_Profile>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder_Profile {
        val cellForRow = LayoutInflater.from(context).inflate(R.layout.custom_list_profile,parent,false)
        return CustomViewHolder_Profile(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder_Profile, position: Int) {
        val curData = DataList[position]
        holder.Title.text = curData.Title
        holder.itemView.setOnClickListener{
            Toast.makeText(context,curData.Title, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = DataList.size

}