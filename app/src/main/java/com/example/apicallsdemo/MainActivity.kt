package com.example.apicallsdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.apicallsdemo.databinding.ActivityMainBinding
import org.json.JSONArray
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private fun processQuoteJson(jsonString: String): String {
        val jsonArray = JSONArray(jsonString)
        return jsonArray[0].toString()
    }

    private fun fetchData(urlString: String){
        val thread = Thread{
            //some long running task logic here
            try {
                val url = URL(urlString)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection;
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {

                    //see https://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string#answer-5445161
                    //for description of delimiter - basically this means that the next token the scanner reads is the entire stream
                    val scanner = Scanner(connection.inputStream).useDelimiter("\\A")
                    val text = if (scanner.hasNext()) scanner.next() else ""

                    val quote = processQuoteJson(text)
                    updateTextView(quote)
                } else {
                    //bad response from server
                    updateTextView("The server returned an error: $responseCode")
                }
            } catch (e: IOException) {
                //something went wrong
                updateTextView("An error occurred retrieving data from the server")
            }
        }
        thread.start()
    }

    private fun updateTextView(text: String) {
        runOnUiThread{
            binding.textView.text = text
        }
    }

    fun getQuote(view: View){
        fetchData("https://ron-swanson-quotes.herokuapp.com/v2/quotes")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}