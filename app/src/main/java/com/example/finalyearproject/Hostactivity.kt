package com.example.finalyearproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Hostactivity: AppCompatActivity(){

    lateinit var access_token : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_activity)

        access_token = intent.getStringExtra("token").toString()


        var genre : Spinner = findViewById(R.id.genre_spin)
        ArrayAdapter.createFromResource(
                this,
                R.array.genre_list,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            genre.adapter = adapter
        }

        var subgenre : Spinner = findViewById(R.id.subgenre_spin)
        ArrayAdapter.createFromResource(
                this,
               R.array.empty,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            subgenre.adapter = adapter
        }

        genre.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long)
            {
             if(genre.selectedItem.toString() == "Rock")
             {
                 ArrayAdapter.createFromResource(
                         this@Hostactivity,
                         R.array.rock_sublist,
                         android.R.layout.simple_spinner_item
                 ).also { adapter ->
                     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                     subgenre.adapter = adapter
                 }
             }
                else if(genre.selectedItem.toString() == "Pop")
             {
                 ArrayAdapter.createFromResource(
                         this@Hostactivity,
                         R.array.pop_sublist,
                         android.R.layout.simple_spinner_item
                 ).also { adapter ->
                     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                     subgenre.adapter = adapter
                 }
             }
                else if(genre.selectedItem.toString() == "Electronic")
             {
                 ArrayAdapter.createFromResource(
                         this@Hostactivity,
                         R.array.elect_sublist,
                         android.R.layout.simple_spinner_item
                 ).also { adapter ->
                     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                     subgenre.adapter = adapter
                 }

             }
                else if (genre.selectedItem.toString()=="Rap")
             {
                 ArrayAdapter.createFromResource(
                         this@Hostactivity,
                         R.array.rap_sublist,
                         android.R.layout.simple_spinner_item
                 ).also { adapter ->
                     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                     subgenre.adapter = adapter
                 }
             }
                else if(genre.selectedItem.toString()=="Country")
             {
                 ArrayAdapter.createFromResource(
                         this@Hostactivity,
                         R.array.country_sublist,
                         android.R.layout.simple_spinner_item
                 ).also { adapter ->
                     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                     subgenre.adapter = adapter
                 }
             }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }


        fun startGame(v: View)
        {
            var sub : Spinner = findViewById(R.id.subgenre_spin)

            val i : Intent = Intent(this,Game::class.java)
            i.putExtra("genre",sub.selectedItem.toString())
            i.putExtra("token",access_token)
            startActivity(i)
        }

}






