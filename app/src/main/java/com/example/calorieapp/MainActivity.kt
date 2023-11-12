package com.example.calorieapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import okhttp3.Call
import okhttp3.Callback

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var foodName: String
    private lateinit var foodCalories: String
    private lateinit var foodFatSaturated: String
    private lateinit var foodprotein: String
    private lateinit var foodCarbohydrate: String
    private lateinit var foodFiber: String
    private lateinit var foodApiResult: String
    private var totalCalories = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.apiTester)
        val button = findViewById<Button>(R.id.search_button)
        button.setOnClickListener {
            val userInput = findViewById<EditText>(R.id.food_answer).text.toString()
            getNutritionInfo(textView, userInput)
        }
        val switchButton = findViewById<Button>(R.id.page_switch_tester)
        switchButton.setOnClickListener {
            openFoodActivity()
        }

    }

    private val REQUEST_CODE_FOOD_ACTIVITY = 1

    private fun openFoodActivity() {
        val intent = Intent(this, FoodActivity::class.java)
        intent.putExtra("Name_", foodName)
        intent.putExtra("calories_", foodCalories)
        intent.putExtra("fat_Saturated_", foodFatSaturated)
        intent.putExtra("protein_", foodprotein)
        intent.putExtra("carbohydrates_", foodCarbohydrate)
        intent.putExtra("fiber_", foodFiber)
        intent.putExtra("total_Calories", totalCalories)
        startActivityForResult(intent, REQUEST_CODE_FOOD_ACTIVITY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_FOOD_ACTIVITY && resultCode == RESULT_OK) {
            val updatedTotalCalories = data?.getIntExtra("UPDATED_TOTAL_CALORIES", 0) ?: 0
            totalCalories = updatedTotalCalories
            val calorieText = findViewById<TextView>(R.id.total_calories_text)
            calorieText.text = "total Calories: ${totalCalories}"
        }
    }

    private fun getNutritionInfo(textView: TextView, food: String) {
        val client = OkHttpClient()
        val url = "https://api.api-ninjas.com/v1/nutrition?query=${food}"
        val request = Request.Builder().url(url)
            .addHeader("x-api-key", "QpWHeJe+v61Szjf1tIYvPg==edPDwj9M04faGItV").build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                Log.d("Dog", "Failed")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    runOnUiThread {
                        foodApiResult = response.body!!.string()
                        FoodItemsAsDict(foodApiResult)
                        Log.d("Dog", "Success")
                    }
                }
            }
        })
    }

    private fun FoodItemsAsDict(word: String){

        val regex = Regex("[a-zA-Z_]+|\\d+\\.\\d+")
        val matches = regex.findAll(word)

        val result = matches.map { it.value }.toList()

        var hashMap : HashMap<String, String>
                = HashMap<String, String> ()

        for (i in 0 until result.size step 2) {
            val key = result[i]
            val value = result.getOrNull(i + 1) ?: ""
            hashMap[key] = value
        }

        val apiTesterView = findViewById<TextView>(R.id.apiTester)
        apiTesterView.text = hashMap.get("name")

        foodName = hashMap.get("name").toString()
        foodCalories = hashMap.get("calories").toString()
        foodCarbohydrate = hashMap.get("carbohydrates_total_g").toString()
        foodFiber = hashMap.get("fiber_g").toString()
        foodFatSaturated = hashMap.get("fat_saturated_g").toString()
        foodprotein = hashMap.get("protein_g").toString()

    }
}






/*
    private fun getNutritionInfo(textView: TextView, food: String) {
        val client = OkHttpClient()
        val url = "https://api.api-ninjas.com/v1/nutrition?query=${food}"
        val request = Request.Builder().url(url)
            .addHeader("x-api-key", "6F1/pYI1LJU4EpJ69LIlfA==xhRcFZ9T9BYaM9VD").build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                Log.d("Dog", "Didn't work hi:  $food")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    runOnUiThread {
                        Log.d("Message", "worked but not showing")
                        val responseBody = response.body
                        if (responseBody != null) {
                            textView.text = responseBody.string()
                        } else {
                            Log.e("Error", "Response body is null")
                        }
                    }
                }
            }
        })

    }

 */



                        // working code
/*
package com.example.calorieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var foodList : MutableList<Food>
    private var calorieGoal : Int = 0
    private lateinit var userInput : String

    private lateinit var foodApiResult: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        foodList = mutableListOf<Food>()
        val textView = findViewById<TextView>(R.id.apiTester)
        val button = findViewById<Button>(R.id.search_button)
        button.setOnClickListener(){
            val userInput = findViewById<EditText>(R.id.food_answer).text.toString()
            getNutritionInfo(textView, userInput)
        }
    }

    private fun getNutritionInfo(textView: TextView, food: String) {
        val client = OkHttpClient()
        val url = "https://api.api-ninjas.com/v1/nutrition?query=${food}"
        val request = Request.Builder().url(url)
            .addHeader("x-api-key", "QpWHeJe+v61Szjf1tIYvPg==edPDwj9M04faGItV").build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                Log.d("Dog", "Failed")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    runOnUiThread {
                        foodApiResult = response.body!!.string()
                        FoodItemsAsDict(foodApiResult)
                        Log.d("Dog", "Success")
                    }
                }
            }
        })
    }

    private fun FoodItemsAsDict(word: String){
        val regex = Regex("[a-zA-Z_]+|\\d+\\.\\d+")
        val matches = regex.findAll(word)

        val result = matches.map { it.value }.toList()

        var hashMap : HashMap<String, String>
                = HashMap<String, String> ()

        for (i in 0 until result.size step 2) {
            val key = result[i]
            val value = result.getOrNull(i + 1) ?: ""
            hashMap[key] = value
        }

        val apiTesterView = findViewById<TextView>(R.id.apiTester)
        apiTesterView.text = hashMap.get("protein_g")
    }
}

 */

