package com.loom.io.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.material.imageview.ShapeableImageView
import com.loom.io.Constants.Constants
import com.loom.io.Constants.Constants.isAR
import com.loom.io.Constants.Constants.tagList
import com.loom.io.R

class TagDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_details)


        val tvTagName: TextView = findViewById(R.id.tvTagName)
        val imgTag: ShapeableImageView = findViewById(R.id.imgTag)
        val imgColorTag: ShapeableImageView = findViewById(R.id.imgColorTag)
        val imgBack: ImageView = findViewById(R.id.imgBack)
        val rlViewInAR: RelativeLayout = findViewById(R.id.rlViewInAR)

        tvTagName.text = tagList.get(Constants.position).tagName
        imgTag.setBackgroundColor(resources.getColor(tagList.get(Constants.position).tagColour,null))
        imgColorTag.setBackgroundColor(resources.getColor(tagList.get(Constants.position).tagColour,null))

        rlViewInAR.setOnClickListener {

            if(isAR){
                finish()
                isAR = false
            }else {
                val intent = Intent(this, ARviewActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        imgBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}