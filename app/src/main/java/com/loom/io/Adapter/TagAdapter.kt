package com.loom.io.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loom.io.ModelClass.Tag
import com.loom.io.R
import java.util.Locale

class TagAdapter(private val tagList: MutableList<Tag>, private  val con: Context, private val listener: OnItemClickListener) : RecyclerView.Adapter<TagAdapter.ViewHolder>() {
    val tagListCopy = mutableListOf<Tag>()
    interface OnItemClickListener {
        fun onItemClick(tag: Tag,position: Int)
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {

        val tvTagName: TextView = itemView.findViewById(R.id.tvTagName)
        val tvPosition: TextView = itemView.findViewById(R.id.tvPosition)
        val imgPostion: ImageView = itemView.findViewById(R.id.imgPostion)
        val leftColorView: View = itemView.findViewById(R.id.leftColorView)
        val llView2: LinearLayout = itemView.findViewById(R.id.llView2)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(tagList[position],position)
            }
        }


    }

    fun copyTagList(): MutableList<Tag> {
        for (tag in tagList) {
            tagListCopy.add(tag.copy())
        }
        return tagListCopy
    }
    fun filter(query: String) {

        var query = query
        tagListCopy.clear()
        if (query.isEmpty()) {
            tagListCopy.addAll(tagList)
        } else {
            query = query.lowercase(Locale.getDefault())
            for (item in tagList) {
                if (item.tagName.toLowerCase().contains(query)) {
                    tagListCopy.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }

    fun getFilterSize(): Int {
        return tagListCopy.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adaptor_list_tags, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = tagListCopy[position]
        holder.tvTagName.text = currentItem.tagName

        holder.leftColorView.setBackgroundColor(con.resources.getColor(currentItem.tagColour,null))

        holder.tvPosition.text = currentItem.tagPosition
        if(currentItem.tagDirection.equals("up")){
            holder.imgPostion.setImageResource(R.drawable.arrow_up)
        }else if(currentItem.tagDirection.equals("down")){
            holder.imgPostion.setImageResource(R.drawable.arrow_down)
        }else if(currentItem.tagDirection.equals("right")){
            holder.imgPostion.setImageResource(R.drawable.arrow_right)
        }else if(currentItem.tagDirection.equals("left")){
            holder.imgPostion.setImageResource(R.drawable.arrow_back_)
        }
        if(currentItem.view2){
            holder.llView2.visibility = View.VISIBLE
        }else{
            holder.llView2.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return tagListCopy.size
    }
}
