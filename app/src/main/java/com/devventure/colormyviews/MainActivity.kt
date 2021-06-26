package com.devventure.colormyviews

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.devventure.colormyviews.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var boxOne: TextView
    lateinit var boxTwo: TextView
    lateinit var boxThree: TextView
    lateinit var boxFour: TextView
    lateinit var boxFive: TextView

    private var boxOneColor = R.color.gray
    private var boxTwoColor = R.color.gray
    private var boxThreeColor = R.color.gray
    private var boxFourColor = R.color.gray
    private var boxFiveColor = R.color.gray

    private val sharedPreference: SharedPreferences
        get(){
            return this.getSharedPreferences("colors", Context.MODE_PRIVATE)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        boxOne = findViewById<TextView>(R.id.box_one_text)
        boxTwo = findViewById<TextView>(R.id.box_two_text)
        boxThree = findViewById<TextView>(R.id.box_three_text)
        boxFour = findViewById<TextView>(R.id.box_four_text)
        boxFive = findViewById<TextView>(R.id.box_five_text)

        boxOneColor = sharedPreference.getInt("boxOne", R.color.gray)
        boxTwoColor = sharedPreference.getInt("boxTwo", R.color.gray)
        boxThreeColor = sharedPreference.getInt("boxThree", R.color.gray)
        boxFourColor = sharedPreference.getInt("boxFour", R.color.gray)
        boxFiveColor = sharedPreference.getInt("boxFive", R.color.gray)
        boxOne.setBackgroundResource(boxOneColor)
        boxTwo.setBackgroundResource(boxTwoColor)
        boxThree.setBackgroundResource(boxThreeColor)
        boxFour.setBackgroundResource(boxFourColor)
        boxFive.setBackgroundResource(boxFiveColor)

        var changeColor = R.color.gray

        binding.buttonRed.setOnClickListener{
            changeColor = R.color.red
        }

        binding.buttonYellow.setOnClickListener{
            changeColor = R.color.yellow
        }

        binding.buttonGreen.setOnClickListener{
            changeColor = R.color.green
        }

        binding.boxOneText.setOnClickListener{
            boxOne.setBackgroundResource(changeColor)
            boxOneColor = changeColor
        }

        binding.boxTwoText.setOnClickListener{
            boxTwo.setBackgroundResource(changeColor)
            boxTwoColor = changeColor
        }

        binding.boxThreeText.setOnClickListener{
            boxThree.setBackgroundResource(changeColor)
            boxThreeColor = changeColor
        }

        binding.boxFourText.setOnClickListener{
            boxFour.setBackgroundResource(changeColor)
            boxFourColor = changeColor
        }

        binding.boxFiveText.setOnClickListener{
            boxFive.setBackgroundResource(changeColor)
            boxFiveColor = changeColor
        }
    }

    override fun onStop() {
        super.onStop()

        val sharedPreference = getSharedPreferences("colors", Context.MODE_PRIVATE)

        val editor = sharedPreference.edit()

        editor.putInt("boxOne", boxOneColor)
        editor.putInt("boxTwo", boxTwoColor)
        editor.putInt("boxThree", boxThreeColor)
        editor.putInt("boxFour", boxFourColor)
        editor.putInt("boxFive", boxFiveColor)

        editor.commit()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun shareScreenScreenshot(view: View) {
        var path = ""
        if (checkAppPermissions()) {
            val rootView = window.decorView.findViewById<View>(android.R.id.content)
            val bitmap = takeScreenshot(rootView)
            if (bitmap != null) {
                path = storeScreenshot(bitmap)
            }
            shareScreenshot(path)
        } else {
            Toast.makeText(this, R.string.ask_permissions, Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkAppPermissions(): Boolean {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), 1
            )
        }
        return permission == PackageManager.PERMISSION_GRANTED
    }

    private fun takeScreenshot(rootView: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(rootView.width, rootView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bgDrawable = rootView.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        rootView.draw(canvas)
        return bitmap
    }

    private fun storeScreenshot(bitmap: Bitmap): String {
        return MediaStore.Images.Media.insertImage(contentResolver, bitmap, imgName(), null)
    }

    private fun imgName(): String {
        return "Screenshot" + Date().time
    }

    private fun shareScreenshot(path: String) {
        val imgToShare: Uri = Uri.parse(path)
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_SUBJECT, "My art")
        intent.putExtra(Intent.EXTRA_TEXT, "my beautiful drawing")
        intent.putExtra(Intent.EXTRA_STREAM, imgToShare)

        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No app Avaliable", Toast.LENGTH_SHORT).show()
        }
    }
}