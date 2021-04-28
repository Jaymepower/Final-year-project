package com.jamie.finalyearproject

import android.app.Dialog
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.android.billingclient.api.*
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.anko.find


class Store : AppCompatActivity(), PurchasesUpdatedListener {


    lateinit var id : String

    lateinit var user_ref : DatabaseReference

    private val pop_list = listOf("pop_subgenre_playlist_id")
    private val rock_list = listOf("rock_subgenre_playlist_id")
    private val country_list = listOf("country_subgenre_playlist_id")
    private val electro_list = listOf("electronic_subgenre_playlist_id")
    private val rap_list = listOf("rap_subgenre_playlist_id")


    lateinit var billingClient: BillingClient

    lateinit var popButton : Button
    lateinit var rockButton : Button
    lateinit var countryButton: Button
    lateinit var rapButton : Button
    lateinit var electronicButton : Button

    lateinit var popIcon : ImageView
    lateinit var rockIcon : ImageView
    lateinit var countryIcon : ImageView
    lateinit var rapIcon : ImageView
    lateinit var electroIcon : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.store_layout)

        val dialog = Dialog(this)
        dialog.show()

        id = intent.getStringExtra("uid").toString()

        billingClient = BillingClient
                .newBuilder(this)
                .enablePendingPurchases()
                .setListener(this)
                .build()


        popButton = findViewById<Button>(R.id.buy_pop)
        rockButton = findViewById<Button>(R.id.buy_rock)
        countryButton = findViewById<Button>(R.id.buy_country)
        electronicButton = findViewById<Button>(R.id.buy_elect)
        rapButton = findViewById<Button>(R.id.buy_rap)

        popIcon = findViewById(R.id.pop_icon)
        rockIcon = findViewById(R.id.rock_icon)
        countryIcon = findViewById(R.id.country_icon)
        electroIcon = findViewById(R.id.electronic_icon)
        rapIcon = findViewById(R.id.rap_icon)

        user_ref = FirebaseDatabase.getInstance().getReference("Users")


        user_ref.addListenerForSingleValueEvent(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
              for(snap in snapshot.children)
              {
                  val currentID = snap.child("user_id").value.toString()

                  if(id == currentID)
                  {
                      if(snap.child("pop").value.toString().toBoolean())
                      {

                          popButton.background = resources.getDrawable(R.drawable.spotifybutton)
                          popIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_check))
                      }
                      else if (snap.child("rock").value.toString().toBoolean())
                      {

                          rockButton.background = resources.getDrawable(R.drawable.spotifybutton)
                          rockIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_check))
                      }
                      else if (snap.child("country").value.toString().toBoolean())
                      {
                          countryButton.background = resources.getDrawable(R.drawable.spotifybutton)
                          countryIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_check))
                      }
                      else if (snap.child("electronic").value.toString().toBoolean())
                      {
                          electronicButton.background = resources.getDrawable(R.drawable.spotifybutton)
                      } else if (snap.child("rap").value.toString().toBoolean())
                      {
                          rapButton.background = resources.getDrawable(R.drawable.spotifybutton)
                          rapIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_check))
                      }

                      dialog.dismiss()

                  }


              }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        billingClient.startConnection(object : BillingClientStateListener {

            override fun onBillingSetupFinished(p0: BillingResult) {
                if (p0.responseCode == BillingClient.BillingResponseCode.OK) {
                    Log.i("Billing Client", "Connected")
                } else {

                }
            }

            override fun onBillingServiceDisconnected() {
                Log.i("Billing Client", "disconnected")
            }
        })


    }


    fun BuyPop(v: View) {
        val params = SkuDetailsParams
                .newBuilder()
                .setSkusList(pop_list)
                .setType(BillingClient.SkuType.INAPP)
                .build()
        billingClient.querySkuDetailsAsync(params) { responseCode, skuDetailsList ->
            if (responseCode.responseCode == BillingClient.BillingResponseCode.OK) {
                Log.i("SKU List", skuDetailsList.toString())

                val billingFlowParams = skuDetailsList?.get(0)?.let {
                    BillingFlowParams
                            .newBuilder()
                            .setSkuDetails(it)
                            .build()
                }

                if (billingFlowParams != null) {
                    billingClient.launchBillingFlow(this, billingFlowParams)
                }


            } else {
                println("Can't querySkuDetailsAsync, responseCode: ${responseCode.responseCode}")
                Log.i("SKU List", billingClient.isReady.toString()
                )

            }

        }

    }



    fun BuyRock(v: View) {
        val params = SkuDetailsParams
                .newBuilder()
                .setSkusList(rock_list)
                .setType(BillingClient.SkuType.INAPP)
                .build()
        billingClient.querySkuDetailsAsync(params) { responseCode, skuDetailsList ->
            if (responseCode.responseCode == BillingClient.BillingResponseCode.OK) {
                Log.i("SKU List", skuDetailsList.toString())

                val billingFlowParams = skuDetailsList?.get(0)?.let {
                    BillingFlowParams
                            .newBuilder()
                            .setSkuDetails(it)
                            .build()
                }
                if (billingFlowParams != null) {
                    billingClient.launchBillingFlow(this, billingFlowParams)
                }


            } else {
                println("Can't querySkuDetailsAsync, responseCode: ${responseCode.responseCode}")
                Log.i("SKU List", billingClient.isReady.toString()
                )

            }

        }

    }





    fun BuyCountry(v: View) {
        val params = SkuDetailsParams
                .newBuilder()
                .setSkusList(country_list)
                .setType(BillingClient.SkuType.INAPP)
                .build()
        billingClient.querySkuDetailsAsync(params) { responseCode, skuDetailsList ->
            if (responseCode.responseCode == BillingClient.BillingResponseCode.OK) {
                Log.i("SKU List", skuDetailsList.toString())

                val billingFlowParams = skuDetailsList?.get(0)?.let {
                    BillingFlowParams
                            .newBuilder()
                            .setSkuDetails(it)
                            .build()
                }
                if (billingFlowParams != null) {
                    billingClient.launchBillingFlow(this, billingFlowParams)
                }


            } else {
                println("Can't querySkuDetailsAsync, responseCode: ${responseCode.responseCode}")
                Log.i("SKU List", billingClient.isReady.toString()
                )

            }

        }

    }


    fun BuyElectronic(v: View) {
        val params = SkuDetailsParams
                .newBuilder()
                .setSkusList(electro_list)
                .setType(BillingClient.SkuType.INAPP)
                .build()
        billingClient.querySkuDetailsAsync(params) { responseCode, skuDetailsList ->
            if (responseCode.responseCode == BillingClient.BillingResponseCode.OK) {
                Log.i("SKU List", skuDetailsList.toString())

                val billingFlowParams = skuDetailsList?.get(0)?.let {
                    BillingFlowParams
                            .newBuilder()
                            .setSkuDetails(it)
                            .build()
                }
                if (billingFlowParams != null) {
                    billingClient.launchBillingFlow(this, billingFlowParams)
                }


            } else {
                println("Can't querySkuDetailsAsync, responseCode: ${responseCode.responseCode}")
                Log.i("SKU List", billingClient.isReady.toString()
                )

            }

        }

    }


    fun BuyRap(v: View) {
        val params = SkuDetailsParams
                .newBuilder()
                .setSkusList(rap_list)
                .setType(BillingClient.SkuType.INAPP)
                .build()
        billingClient.querySkuDetailsAsync(params) { responseCode, skuDetailsList ->
            if (responseCode.responseCode == BillingClient.BillingResponseCode.OK) {
                Log.i("SKU List", skuDetailsList.toString())

                val billingFlowParams = skuDetailsList?.get(0)?.let {
                    BillingFlowParams
                            .newBuilder()
                            .setSkuDetails(it)
                            .build()
                }
                if (billingFlowParams != null) {
                    billingClient.launchBillingFlow(this, billingFlowParams)
                }


            } else {
                println("Can't querySkuDetailsAsync, responseCode: ${responseCode.responseCode}")
                Log.i("SKU List", billingClient.isReady.toString()
                )

            }

        }

    }



    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {

        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {

            val product = purchases[0].sku

            var playlist = ""

            when(product)
            {
                "pop_subgenre_playlist_id" ->{
                    playlist = "pop"
                    popButton.background = resources.getDrawable(R.drawable.spotifybutton)
                    popIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_check))
                }
                "rock_subgenre_playlist_id" -> {
                    playlist = "rock"
                    rockButton.background = resources.getDrawable(R.drawable.spotifybutton)
                    rockIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_check))
                }
                "country_subgenre_playlist_id" ->{
                    playlist = "country"
                    countryButton.background = resources.getDrawable(R.drawable.spotifybutton)
                    countryIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_check))
                }
                "electronic_subgenre_playlist_id" -> {
                    playlist = "electronic"
                    electronicButton.background = resources.getDrawable(R.drawable.spotifybutton)
                    electroIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_check))
                }
                "rap_subgenre_playlist_id" ->{
                    playlist = "rap"
                    rapButton.background = resources.getDrawable(R.drawable.spotifybutton)
                    rapIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_check))
                }
            }


            user_ref.addListenerForSingleValueEvent(object : ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {

                    for(snap in snapshot.children)
                    {
                        val currentID = snap.child("user_id").value.toString()

                        if (currentID == id)
                        {
                            user_ref.child(snap.key.toString()).child(playlist).setValue(true)
                        }
                    }


                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })





        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {


        } else {
            // Handle any other error codes.
        }
    }






}