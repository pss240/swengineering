package com.example.swengineering

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.swengineering.FB.FBAuth
import com.example.swengineering.FB.FBRef
import com.example.swengineering.model.CommentModel
import com.example.swengineering.model.EssayModel
import kotlinx.android.synthetic.main.custom_list_essay_comment.view.*
import kotlinx.android.synthetic.main.fragment_essay_viewer.*

class Data_Essay_viewer_comment(val name : String, val content : String,val thumb: String,val commentkey : String, val uid : String)

class CustomViewHolder_Essay_viewer_comment(v: View) : RecyclerView.ViewHolder(v) {
    val name = v.textView_custom_essay_comment_userName
    val content = v.textView_custom_essay_comment_content
    val thumb = v.textView_comment_thumb
    val button_thumb = v.button_essay_viewer_comment_like
    val button_delete_comment = v.button_delete_comment
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
        holder.thumb.text = curData.thumb

        holder.button_thumb.setOnClickListener {

            FBRef.commentRef
                .child(essayKey)
                .child(curData.commentkey)
                .setValue(
                    CommentModel(curData.name,curData.content,(curData.thumb.toInt()+1).toString())
                )
        }
        holder.button_delete_comment.setOnClickListener {
            if(curData.uid == FBAuth.getUid()) {
                FBRef.commentRef
                    .child(essayKey)
                    .child(curData.commentkey)
                    .removeValue()
                Toast.makeText(it.context,"댓글이 삭제되었습니다", Toast.LENGTH_SHORT).show()

            }
            else
                Toast.makeText(it.context,"권한이 없습니다", Toast.LENGTH_SHORT).show()

        }
    }


    override fun getItemCount() : Int = DataList.size
}
