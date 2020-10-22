package com.reactnativeantourage

import android.content.Intent
import com.antourage.weaverlib.screens.base.AntourageActivity
import com.antourage.weaverlib.ui.fab.AntourageFab
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod


class AntourageManager(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext){

  companion object {
    private const val REACT_CLASS = "RNAntourage"
  }

  override fun getName(): String {
    return REACT_CLASS
  }

  @ReactMethod
  fun authWithApiKey(apiKey: String?, userId: String?, userNickName: String?) {
    reactApplicationContext.currentActivity?.runOnUiThread {
      apiKey?.let { AntourageFab.authWith(it, userId, userNickName, reactApplicationContext) }
    }
  }

  @ReactMethod
  fun showFeed() {
    val intent = Intent(reactApplicationContext, AntourageActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
    reactApplicationContext.currentActivity?.startActivity(intent)
  }
}
