package com.example.quizapp

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"

//A constant that will be the key for the key-value pair that will be stored
//in the bundle.
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    //Creating a ViewModel instance and giving it activity for lifecycle and giving it the
    // viewmodel class i.e. QuizViewModel
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    //Creating objects/instances/references/variables for each specific view.
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Displays the UI for a particular activity.
        setContentView(R.layout.activity_main)

        //Pulling up info saved in Bundle objected savedInstanceState during process death!
        //If there is no info saved in bundle then we set the default value to zero
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        //Wiring up each view to perform certain actions on them thru code.
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        //Setting onClickListener on trueButton whenever it is clicked.
        trueButton.setOnClickListener {
            //Calling checkAnswer function with arg true as boolean.
            checkAnswer(true)
        }

        //Setting onClickListener on falseButton whenever it is clicked.
        falseButton.setOnClickListener {
            //Calling checkAnswer function with arg false as boolean.
            checkAnswer(false)
        }

        //Setting onClickListener on nextButton whenever it is clicked.
        nextButton.setOnClickListener {
            //Getting moveToNext() method from model class quizViewModel.
            quizViewModel.moveToNext()
            updateQuestion()
        }

        //Calling this method to update the question.
        updateQuestion()
    }

    //Saving data in onSaveInstanceState.
    //onSaveInstanceState method is called just before an activity is about to be destroyed.
    //OnSaveInstanceState method saves data in activity record for process death.
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    //Updating question
    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}
