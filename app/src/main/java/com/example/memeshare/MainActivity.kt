package com.example.memeshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso



class MainActivity : AppCompatActivity() {
    var Currenturl: String? =null
    lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()

        imageView=findViewById(R.id.meme)
    }

    fun ShareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"

        intent.putExtra(Intent.EXTRA_TEXT,"Hey! Checkout this cool meme $Currenturl ")
        val chooser = Intent.createChooser(intent, "Share this meme using....")
        startActivity(chooser)
    }

    private fun loadMeme(){

        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.com/gimme"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                Currenturl = response.getString("url")
                Log.d("result", response.toString())

                Picasso.get().load(response.get("url").toString()).placeholder(R.drawable.loding).into(imageView);
            },
            {

                Log.e("error", it.toString())
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            })

        queue.add(request)
    }
    fun NextMeme(view: View) {
        loadMeme()
    }
}