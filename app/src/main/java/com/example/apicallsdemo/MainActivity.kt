package com.example.apicallsdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.apicallsdemo.databinding.ActivityMainBinding
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private fun processQuoteJson(jsonString: String): String {
        val jsonArray = JSONArray(jsonString)
        return jsonArray[0].toString()
    }

    fun getQuote(view: View){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}