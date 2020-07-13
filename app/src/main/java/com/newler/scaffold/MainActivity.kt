package com.newler.scaffold

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.newler.state.StateManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StateManager.instance.initAdapter(StateManagerAdapter())
        setContentView(R.layout.activity_main)
    }
}
