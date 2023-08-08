package com.jdmacmil.bullseye

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import com.jdmacmil.bullseye.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var sliderValue = 0
    private var targetValue = Random.nextInt(1, 100)

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        binding.targetTextView.text = getString(R.string.target_value_text, targetValue)
        binding.targetTextView.text = targetValue.toString()

        binding.hitMeButton.setOnClickListener {
            Log.i("Button Click Event", "You clicked the Hit Me Button")
            showResult()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sliderValue = progress
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
    }

    private fun pointsForCurrentRound(): Int {
        val maxScore = 100
        val difference = abs(targetValue - sliderValue)
        return maxScore - difference

//        if (difference < 0){
//            difference *= -1
//        }
//        return maxScore - difference

//        var difference: Int = if (targetValue > sliderValue){
//            targetValue - sliderValue
//        } else if (sliderValue > targetValue){
//            sliderValue - targetValue
//        } else {
//            0
//        }
//        return maxScore - difference

    }

    private fun showResult(){
        val dialogTitle = getString(R.string.result_dialog_title)
//        val dialogMessage = getString(R.string.result_dialog_message)
//        val dialogMessage = "The slider's value is $sliderValue"
        val dialogMessage =
            getString(R.string.result_dialog_message, sliderValue, pointsForCurrentRound())


        val builder = AlertDialog.Builder(this)

        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.setPositiveButton(R.string.result_dialog_button_text) {
            dialog, _ -> dialog.dismiss()
        }

        builder.create().show()
    }


}