package com.example.sevenly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import com.example.sevenly.databinding.ActivityExerciseBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList
import android.R.raw
import android.media.MediaPlayer
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager


class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    //binding variable that holds all the layout data
    lateinit var binding: ActivityExerciseBinding
    //countdown timer object that does the actual timekeeping
    var restTimer: CountDownTimer? = null
    //progress variable holds the time duration that has passed
    private var restProgress: Int = 0
    //arraylist variable that holds all the exercise details, including image and data
    private var exerciseList : ArrayList<ExerciseModel>? = null
    //this variable holds the position of current exercise
    private var currentExercisePosition: Int = -1
    //this is the text to speech variable that speaks out the name of the exercise next
    private var tts : TextToSpeech? = null
    //this variable holds all the raw field values
    private var mediaList: ArrayList<Int> = ArrayList<Int>()
    //this is used to play song when exercise starts
    private var mediaPlayer: MediaPlayer? = null
    //this is the adapter that populates the recycler view at the bottom of the screen
    private var exerciseViewAdapter : ExerciseViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //adding material toolbar support to the top of the screen that enables back button and title,
        //similar to navigation controller in iOS
        setSupportActionBar(binding.toolbarEx)

        //this means that pressing the back button does not return you to the root level layout at top,
        //rather it returns to the previous screen that is going to be useful later on when maintaining
        //many types of exercise activity
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarEx.setNavigationOnClickListener {
            //when pressed the top level back button, it functions similarly to the back button
            //being pressed, returning to the immediate higher level view hierarchy
            onBackPressed()
        }
        //this creates a text to speech object that says the name of the workout
        tts = TextToSpeech(this,this)

        //this returns all the constants from the exercise file
        exerciseList = Constants().defaultExercise()

        //this function holds the complete beginning of timer view before the exercise views start
        setRestView()

        setExerciseStatusRecyclerView()

    }
    //this function sets up the progress bar, it includes setting up an internal countdowntimer object
    //and updating the progress of the bar according to the timer

    private fun setRestProgressBar() {

        if(mediaPlayer != null) {
            if(mediaPlayer!!.isPlaying) {
                mediaPlayer!!.stop()
            }
        }

        binding.progressBar.progress = restProgress
        restTimer = object : CountDownTimer(10000,1000) {
            //the ontick function updates the text and progress bar every clock tick
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.progressBar.progress = 10 - restProgress
                binding.tvTimer.text = (10-restProgress).toString()
            }

            override fun onFinish() {
                //this will change the value to 0 so that it can start working as index for arraylist
                currentExercisePosition++
                //onfinish function hides the timer view once the countdown is over and displays a new linearlayout
                binding.llRestView.visibility = View.GONE
                //this new linear layout will display the exercise text and timer for 30 seconds
                binding.llExerciseView.visibility = View.VISIBLE
                //this function enables the exercise timer to start running
                setExerciseView()
            }
        }.start()
    }

    //this function sets up the exercise progress bar, and includes the internal countdowntimer object
    //and also updates the progress of the bar according to the timer

    private fun setExerciseProgressBar() {

        mediaPlayer = MediaPlayer.create(this,exerciseList!![currentExercisePosition].getMusic())
        binding.progressBarExercise.progress = restProgress
        restTimer = object : CountDownTimer(30000,1000) {
            //the ontick function updates the text and progress bar every clock tick
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.progressBarExercise.progress = 30 - restProgress
                binding.tvTimerExercise.text = (30-restProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition < exerciseList!!.size - 1) {
                    setRestView()
                } else {

                }
            }
        }.start()

        //starts playing the music as soon as the 30 second timer starts
        mediaPlayer!!.start()
    }

    private fun setExerciseView() {
        //if the rest timer exists, it clears out the timer and resets the progress
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        //it sets the progress bar now
        setExerciseProgressBar()


        //this sets up the image of the exercise posture and the name of the exercise that is to be
        //currently done
        binding.ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding.tvExerciseName.text = exerciseList!![currentExercisePosition].getName()

    }

    private fun setRestView() {

        //this is to ensure that there is 10 second break after every exercise
        binding.llRestView.visibility = View.VISIBLE
        binding.llExerciseView.visibility = View.INVISIBLE

        //this displays the name of the exercise that is queued to the next exercise view
        //and if the exercise name does not exist, it means there are no exercise and seven minutes
        //are almost up
        val exName: String = exerciseList!![currentExercisePosition+1].getName()
        if(exName != null) {
            binding.tvNextExerciseName.text = exName
        } else {
            binding.tvNextEx.text = ""
            binding.tvNextExerciseName.text = ""
            binding.tvGetReadyFor.text = "Almost there!"
        }
        //this speaks the name of the upcoming exercise
        speak(exName)


        //if the rest timer exists, it clears out the timer and resets the progress
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        //it sets the progress bar now
        setRestProgressBar()
    }

    private fun speak(text: String) {
        val stt = "Upcoming exercise, $text. Be prepared!"
        tts!!.speak(stt,TextToSpeech.QUEUE_FLUSH,null," ")
    }

    //if the activity is closed, the resttimer is cancelled and so is the text to speech, along with
    //the music that is playing
    override fun onDestroy() {

        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress = 0
        }

        if(tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        if(mediaPlayer?.isPlaying == true) {
            mediaPlayer!!.stop()
        }

        super.onDestroy()
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS) {
            //set up English language
            val result = tts!!.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this,"Text to speech language not supported!",Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this,"Text to speech initialization failed!", Toast.LENGTH_SHORT).show()
        }
    }

    //this function sets up the exercise status recycler view at the bottom of the screen
    private fun setExerciseStatusRecyclerView () {

        //this recyclerview needs a horizontal linear layout manager with reverselayout turned off
        //for ETS layout, only STE supported regardless if you use language like English or Arabic
        binding.rvExerciseStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)

        //this initiates the view adapter object that was declared in the beginning with exercise list array list
        exerciseViewAdapter = ExerciseViewAdapter(this,exerciseList!!)

        //this sets the adapter of recyclerview to the adapter object created above
        binding.rvExerciseStatus.adapter = exerciseViewAdapter
    }
}