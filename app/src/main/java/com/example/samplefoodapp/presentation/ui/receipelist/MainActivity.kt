package com.example.samplefoodapp.presentation.ui.receipelist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.samplefoodapp.R
import com.example.samplefoodapp.commonutils.Constant.RECEIPE_FRAGMENT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction().add(R.id.container, ReceipeListFragment()).addToBackStack(RECEIPE_FRAGMENT).commit()
//
//        }
    }

}

