package com.loom.io.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.material.imageview.ShapeableImageView
import com.loom.io.Constants.Constants
import com.loom.io.Constants.Constants.isAR
import com.loom.io.Constants.Constants.tagList
import com.loom.io.R

class TagDetailinAR : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_details)

        val tvTagName: TextView = findViewById(R.id.tvTagName)
        val llminimize: LinearLayout = findViewById(R.id.llminimize)
        val imgTag: ShapeableImageView = findViewById(R.id.imgTag)
        val imgColorTag: ShapeableImageView = findViewById(R.id.imgColorTag)
        val rlViewInAR: RelativeLayout = findViewById(R.id.rlViewInAR)

        tvTagName.text = tagList.get(Constants.position).tagName
        imgTag.setBackgroundColor(resources.getColor(tagList.get(Constants.position).tagColour,null))
        imgColorTag.setBackgroundColor(resources.getColor(tagList.get(Constants.position).tagColour,null))

        rlViewInAR.visibility = View.GONE

        llminimize.setOnClickListener {
            finish()
        }


    }
}