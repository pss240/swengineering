package com.example.swengineering

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_message_subscriber_profile.view.*

class Data_Message_Main_Profile(val profile: Int, val name: String)

class CustomViewHolder_Message_Main_Profile(v:View) : RecyclerView.ViewHolder(v) {
    val profile = v.imageView_custom_message_profile
    val name = v.custom_message_profile_userName
}

class CustomAdapter_Message_Main_Profile (
    val DataList: ArrayList<Data_Message_Main_Profile>, val context: Context, val view: View
    ) : RecyclerView.Adapter<CustomViewHolder_Message_Main_Profile>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder_Message_Main_Profile {
        val cellForRow = LayoutInflater.from(context).inflate(R.layout.custom_message_subscriber_profile,parent,false)
        return CustomViewHolder_Message_Main_Profile(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder_Message_Main_Profile, position: Int) {
        val curData = DataList[position]
        holder.profile.setImageResource(curData.profile)
        holder.name.text = curData.name

        holder.itemView.setOnClickListener{
            var navController : NavController = Navigation.findNavController(view)
            //navController.navigate(R.id.action_message_main_to_profileFragment)
        }
    }

    override fun getItemCount(): Int = DataList.size
}
