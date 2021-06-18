package com.devventure.colormyviews

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.devventure.colormyviews.databinding.ActivityMainBinding

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
}