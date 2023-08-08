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
    private var targetValue = newTargetValue()
    private var totalScore = 0
    private var currentRound = 1

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        startNewGame()

//        binding.targetTextView.text = getString(R.string.target_value_text, targetValue)
//        Can get rid of these because start new game already does it
//        binding.targetTextView.text = targetValue.toString()
//        binding.gameRoundsTextView?.text = currentRound.toString()

        binding.hitMeButton.setOnClickListener {
            Log.i("Button Click Event", "You clicked the Hit Me Button")
            showResult()
            totalScore += pointsForCurrentRound()
            binding.gameScoreTextView?.text = totalScore.toString()
        }

        binding.startOverButton?.setOnClickListener {
            Log.i("Button Click Event", "You clicked the Start Over Button")
            startNewGame()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sliderValue = progress
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
    }

    private fun differenceAmount() = abs(targetValue - sliderValue)

    private fun newTargetValue() = Random.nextInt(1, 100)

    private fun pointsForCurrentRound(): Int {
        val maxScore = 100
        val difference = differenceAmount()
        var bonusPoints = 0

        if (difference == 0){
            bonusPoints = 100
        }
        else if (difference == 1){
            bonusPoints = 50
        }

        return maxScore - difference + bonusPoints

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

    private fun startNewGame() {
        totalScore = 0
        currentRound = 1
        sliderValue = 50
        targetValue = newTargetValue()

        binding.gameScoreTextView?.text = totalScore.toString()
        binding.gameRoundsTextView?.text = currentRound.toString()
        binding.targetTextView.text = targetValue.toString()
        binding.seekBar.progress = sliderValue
    }

    private fun showResult(){
        val dialogTitle = alertTitle()
//        val dialogMessage = getString(R.string.result_dialog_message)
//        val dialogMessage = "The slider's value is $sliderValue"
        val dialogMessage =
            getString(R.string.result_dialog_message, sliderValue, pointsForCurrentRound())


        val builder = AlertDialog.Builder(this)

        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.setPositiveButton(R.string.result_dialog_button_text) {
            dialog, _ -> dialog.dismiss()
            targetValue = newTargetValue()
            binding.targetTextView.text = targetValue.toString()

            currentRound += 1
            binding.gameRoundsTextView?.text = currentRound.toString()
        }

        builder.create().show()
    }

    private fun alertTitle(): String {
        val difference = differenceAmount()

        val title: String = when {
            difference == 0 -> {
                getString(R.string.alert_title_1)
            }
            difference < 5 -> {
                getString(R.string.alert_title_2)
            }
            difference <= 10 -> {
                getString(R.string.alert_title_3)
            }
            else -> {
                getString(R.string.alert_title_4)
            }
        }

        return title
    }

}