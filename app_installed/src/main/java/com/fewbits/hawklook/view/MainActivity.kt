package com.fewbits.hawklook.view

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.fewbits.hawklook.R

class MainActivity : FragmentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.hlk_app_installed_activity_main)
  }
}
