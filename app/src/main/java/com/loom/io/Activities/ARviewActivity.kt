package com.loom.io.Activities

import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.ArSceneView
import com.google.ar.sceneform.HitTestResult
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Texture
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.BaseArFragment.OnTapArPlaneListener
import com.google.ar.sceneform.ux.TransformableNode
import com.loom.io.Constants.Constants
import com.loom.io.Constants.Constants.tagList
import com.loom.io.R

class ARviewActivity : AppCompatActivity(), View.OnClickListener,Node.OnTapListener {

    private lateinit var arFragment: ArFragment
    private lateinit var tvName: TextView
    private lateinit var tagDirectionPos: TextView
    private lateinit var imgClose: ImageView
    private lateinit var imgTag: ShapeableImageView
    private lateinit var llPosition: LinearLayout
    private lateinit var rlMenu: RelativeLayout
    private lateinit var fabMenu: FloatingActionButton
    private lateinit var fabModel: FloatingActionButton
    private lateinit var closeAR: FloatingActionButton
    private lateinit var cvTagInfo: CardView
    private lateinit var tagObj: com.loom.io.ModelClass.Tag
    private lateinit var scene: Scene
    val modelMap: HashMap<AnchorNode, Int> = HashMap()
    private lateinit var arSceneView: ArSceneView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arview)



        arFragment = supportFragmentManager.findFragmentById(R.id.sceneform_fragment) as ArFragment
        tvName = findViewById(R.id.tvTagName)
        tagDirectionPos = findViewById(R.id.tagDirectionPos)
        imgTag = findViewById(R.id.imgTag)
        cvTagInfo = findViewById(R.id.cvTagInfo)
        imgClose = findViewById(R.id.imgClose)
        rlMenu = findViewById(R.id.rlMenu)
        llPosition = findViewById(R.id.llPosition)
        fabMenu = findViewById(R.id.menu)
        fabModel = findViewById(R.id.newModel)
        closeAR = findViewById(R.id.closeAR)


        arFragment.planeDiscoveryController.hide()
        arFragment.planeDiscoveryController.setInstructionView(null)
        scene = arFragment.arSceneView.scene
        arSceneView = arFragment.arSceneView

        imgClose.setOnClickListener(this)
        fabMenu.setOnClickListener(this)
        fabModel.setOnClickListener(this)
        closeAR.setOnClickListener(this)
        cvTagInfo.setOnClickListener(this)


    }

    fun setNameAndTag(){
        cvTagInfo.visibility =  View.VISIBLE
        rlMenu.visibility =  View.VISIBLE
        llPosition.visibility =  View.VISIBLE

        tvName.text = tagObj.tagName
        tagDirectionPos.text = tagObj.tagPosition.toString() +" "+tagObj.tagDirection.toString()
        imgTag.setBackgroundColor(resources.getColor(tagObj.tagColour,null))
    }
    override fun onResume() {
        super.onResume()
        tagObj = tagList.get(Constants.position)

        setNameAndTag()
        projectModel()
    }


    fun projectModel(){
        ViewRenderable.builder()
            .setView(this, R.layout.ar_view)
            .build()
            .thenAccept { renderable ->
                val imgView = renderable.view as ShapeableImageView
                val node = AnchorNode()
                val model = TransformableNode(arFragment.transformationSystem)
                model.renderable = renderable
                model.setParent(node)
                val position = Vector3(tagObj.x, tagObj.y, tagObj.z)
                node.localPosition = position
                modelMap.put(node,Constants.position)
                scene.addChild(node)

                model.setOnTapListener(this)


            }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                imgClose.id ->{
                    cvTagInfo.visibility =  View.GONE
                    llPosition.visibility =  View.GONE
                    rlMenu.visibility = View.VISIBLE
                }

                fabMenu.id ->{

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
            }
                fabModel.id ->{

                    cvTagInfo.visibility =  View.GONE
                    llPosition.visibility =  View.GONE
                    rlMenu.visibility = View.VISIBLE
                }
                closeAR.id ->{
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Constants.isAR = true;
                }
                cvTagInfo.id ->{
                    val intent = Intent(this, TagDetailinAR::class.java)
                    startActivity(intent)
                    Constants.isAR = true;

                }

            }
        }
    }


    override fun onTap(hitest: HitTestResult?, p1: MotionEvent?) {
        var  value: Int? = modelMap.get(hitest?.node)
        tagObj = tagList.get(value!!)
        setNameAndTag()
    }

}