package com.example.ads_mob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.ads_mob.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var mAdView : AdView
    private var mInterstitialAd:InterstitialAd?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadBannerAd()    // banner ad
        loadInterAd()     //for interstitial ad

             // interstitial ad start from here
        binding.interAd.setOnClickListener {
            showInterAd()
        }
    }

    private fun showInterAd() {
        if (mInterstitialAd !=null){
            mInterstitialAd?.fullScreenContentCallback=object :FullScreenContentCallback(){

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    val intent = Intent(this@MainActivity, MainActivity2::class.java)
                    startActivity(intent)
                }
            }
            mInterstitialAd?.show(this)
        }else{
            val intent =Intent(this,MainActivity2::class.java)
            startActivity(intent)
        }
    }

    private fun loadInterAd() {
        var adRequest=AdRequest.Builder().build()

        InterstitialAd.load(this,
            "ca-app-pub-3940256099942544/1033173712",
        adRequest,object :InterstitialAdLoadCallback(){
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd=null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd=interstitialAd
                }
        })
    }// interstitial ad end here

    private fun loadBannerAd() {           // banner ad
        MobileAds.initialize(this){}

        mAdView=findViewById(R.id.ad_view)
        val adRequest=AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView.adListener=object: AdListener(){

            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Toast.makeText(this@MainActivity,"Ad Loaded",Toast.LENGTH_LONG).show()
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Toast.makeText(this@MainActivity,"Return to main app",Toast.LENGTH_LONG).show()
            }
        }
    }
}