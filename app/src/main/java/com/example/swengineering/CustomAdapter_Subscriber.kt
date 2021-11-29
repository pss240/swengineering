package com.example.swengineering

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_list_subscriber.view.*
import java.util.concurrent.Flow


class Data_Subscriber(val name:String, val key : String)


class CustomViewHolder_Subscriber(v: View) : RecyclerView.ViewHolder(v){
    val name = v.textView_Subscriber_Name
}


class CustomAdapter_Subscriber (val DataList:ArrayList<Data_Subscriber>,val context: Context, var view: View): RecyclerView.Adapter<CustomViewHolder_Subscriber>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder_Subscriber {
        val cellForRow = LayoutInflater.from(context).inflate(R.layout.custom_list_subscriber,parent,false)
        return CustomViewHolder_Subscriber(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder_Subscriber, position: Int) {
        val curData = DataList[position]
        val Subscriberkey = curData.key
        holder.name.text = curData.name
        holder.itemView.setOnClickListener{
            Uid = Subscriberkey
            var navController : NavController = Navigation.findNavController(view)
            navController.navigate(R.id.action_subscriberFragment_to_myEssayPage)
        }
    }

    override fun getItemCount(): Int = DataList.size

}
