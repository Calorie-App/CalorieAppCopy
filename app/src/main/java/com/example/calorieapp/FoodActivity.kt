package com.example.calorieapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class FoodActivity : AppCompatActivity() {
    private var totalCalories = 0.0
    private lateinit var name: String
    private lateinit var calories: String
    private lateinit var fat_saturated_g: String
    private lateinit var protein_g: String
    private lateinit var carbohydrates_total_g: String
    private lateinit var fiber_g: String

    private val REQUEST_CODE_FOOD_ACTIVITY = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food_item)

        // Retrieve total calories from MainActivity
        name = intent.getStringExtra("Name_").toString()
        calories = intent.getStringExtra("calories_").toString()
        fat_saturated_g = intent.getStringExtra("fat_Saturated_").toString()
        protein_g = intent.getStringExtra("protein_").toString()
        carbohydrates_total_g = intent.getDoubleExtra("carbohydrates_", 0.0).toString()
        fiber_g = intent.getDoubleExtra("fiber_", 0.0).toString()
        totalCalories = intent.getDoubleExtra("total_Calories", 0.0)


        val food_name = findViewById<TextView>(R.id.food_name_textView)
        food_name.text = name


        val food_calories = findViewById<TextView>(R.id.calories_textView)
        val food_fat_saturated = findViewById<TextView>(R.id.textView8)
        val food_protein = findViewById<TextView>(R.id.protein_labelView)
        val food_carbohydrates = findViewById<TextView>(R.id.carbohydrate_textView)
        val food_fiber = findViewById<TextView>(R.id.fiber_textView)

        food_calories.text = calories
        food_fat_saturated.text = fat_saturated_g
        food_protein.text = protein_g
        food_carbohydrates.text = carbohydrates_total_g
        food_fiber.text = fiber_g




        val noButton = findViewById<Button>(R.id.no_button)
        noButton.setOnClickListener {
            returnResultAndFinish()
        }

        val yesButton = findViewById<Button>(R.id.yes_button)
        yesButton.setOnClickListener {
            addCaloriesAndReturnResult()
        }
    }

    private fun returnResultAndFinish() {
        val resultIntent = Intent()
        resultIntent.putExtra("UPDATED_TOTAL_CALORIES", totalCalories)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun addCaloriesAndReturnResult() {
        // Add 10 calories to the total
        totalCalories += calories.toDouble()

        // Return the updated total calories to MainActivity
        returnResultAndFinish()
    }
}