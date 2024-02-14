package com.loom.io.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loom.io.Adapter.TagAdapter
import com.loom.io.Constants.Constants
import com.loom.io.Constants.Constants.tagList
import com.loom.io.ModelClass.Tag
import com.loom.io.R

class MainActivity : AppCompatActivity(), TagAdapter.OnItemClickListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tagList = createTagList()

        val recyclerView: RecyclerView = findViewById(R.id.rvTagList)
        var adapter = TagAdapter(tagList,this,this)
        adapter.copyTagList()
        recyclerView.adapter =  adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val searchView: EditText = findViewById(R.id.searchViewTags)
        val tvFilter: TextView = findViewById(R.id.tvFilter)

        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                adapter.filter(charSequence.toString())
                if(adapter.getFilterSize() == 7){
                    tvFilter.text = getString(R.string.filter)
                }else {
                    var filterValue = "Filter(${(adapter.getFilterSize())})"
                    tvFilter.text = filterValue
                }

            }

            override fun afterTextChanged(editable: Editable) {}
        })

    }

    fun createTagList(): MutableList<Tag> {
        return mutableListOf(
            Tag(1, "BGSOOV_273", R.color.red,"3m","up", -2.44f, -0.83f, -1.27f,false),
            Tag(2, "HVGV_274", R.color.orange, "3m","down",-1.18f, 1.14f, -0.6f,true),
            Tag(3, "MI_275", R.color.orange, "5m","right",2.06f, 1.37f, 0.91f,true),
            Tag(4, "P&ID_276", R.color.blue, "5m","down",0.43f, -2.32f, -0.87f,false),
            Tag(5, "Equipmet 3", R.color.green, "5m","left",2.24f, -1.5f, 0.62f,false),
            Tag(6, "BGSOOV_278", R.color.yellow, "5m","up",1.33f, -0.13f, 1.44f,true),
            Tag(7, "BGSOOV_279", R.color.red, "6m","left",0.5f, 0.5f, 0.11f,false)
        )
    }

    override fun onItemClick(tag: Tag,position: Int) {
        Constants.position = position
        val intent = Intent(this, TagDetailActivity::class.java)
        startActivity(intent)
        finish()
    }
}