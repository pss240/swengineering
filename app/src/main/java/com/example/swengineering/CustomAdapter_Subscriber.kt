package com.example.swengineering

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_list_subscriber.view.*


class Data_Subscriber(val profile:Int, val name:String)


class CustomViewHolder_Subscriber(v: View) : RecyclerView.ViewHolder(v){
    val profile = v.imageView_Subscriber_User
    val name = v.textView_Subscriber_Name
}


class CustomAdapter_Subscriber (val DataList:ArrayList<Data_Subscriber>,val context: Context): RecyclerView.Adapter<CustomViewHolder_Subscriber>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder_Subscriber {
        val cellForRow = LayoutInflater.from(context).inflate(R.layout.custom_list_subscriber,parent,false)
        return CustomViewHolder_Subscriber(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder_Subscriber, position: Int) {
        val curData = DataList[position]
        holder.profile.setImageResource(curData.profile)
        holder.name.text = curData.name

        holder.itemView.setOnClickListener{
            Toast.makeText(context,curData.name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = DataList.size

}
