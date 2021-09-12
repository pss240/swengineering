package com.example.swengineering

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_list_essay_comment.view.*

class Data_Essay_viewer_comment(val name : String, val content : String)

class CustomViewHolder_Essay_viewer_comment(v: View) : RecyclerView.ViewHolder(v) {
    val name = v.textView_custom_essay_comment_userName
    val content = v.textView_custom_essay_comment_content
}
class CustomAdapter_Essay_viewer_comment (val DataList : ArrayList<Data_Essay_viewer_comment>, val context: Context)
    : RecyclerView.Adapter<CustomViewHolder_Essay_viewer_comment>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder_Essay_viewer_comment {
        val cellForRow = LayoutInflater.from(context).inflate(R.layout.custom_list_essay_comment, parent, false)
        return CustomViewHolder_Essay_viewer_comment(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder_Essay_viewer_comment, position: Int) {
        val curData = DataList[position]
        holder.name.text = curData.name
        holder.content.text = curData.content
    }

    override fun getItemCount() : Int = DataList.size
}
