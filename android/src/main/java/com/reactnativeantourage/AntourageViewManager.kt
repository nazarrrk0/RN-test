package com.reactnativeantourage

import android.util.Log
import android.view.View
import com.antourage.weaverlib.ui.fab.AntourageFab
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.bridge.ReactContext
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.uimanager.events.RCTEventEmitter
import javax.annotation.Nonnull


class AntourageViewManager : SimpleViewManager<AntourageFab?>(), View.OnClickListener, LifecycleEventListener {

  companion object {
    private const val REACT_CLASS = "AntourageView"
  }

  private var fab: AntourageFab? = null

  override fun onClick(v: View) {
    val map = Arguments.createMap()
    val context = v.context as ReactContext
    context.getJSModule(RCTEventEmitter::class.java).receiveEvent(v.id, "topChange", map)
  }

  override fun getName(): String {
    return REACT_CLASS
  }

  override fun createViewInstance(@Nonnull reactContext: ThemedReactContext): AntourageFab {
    reactContext.addLifecycleEventListener(this)
    Log.e("ant", "creating")
    fab = AntourageFab(reactContext)
    return fab as AntourageFab
  }

  @ReactProp(name = "widgetPosition")
  fun setPosition(button: AntourageFab, position: String?) {
    Log.e("ant", "setPosition: $position")
    position?.let { button.setPosition(it) }
  }

  @ReactProp(name = "widgetLocale")
  fun setLocale(button: AntourageFab, locale: String?) {
    Log.e("ant", "setLocale: $locale")
    locale?.let { button.setLocale(it) }
  }

  //TODO cast widgetMargins to object
//  @ReactProp(name = "widgetMargins")
//  fun setMargins(button: AntourageFab, position: Any) {
//    Log.e("ant", "setMargins: ${position} ")
//  }

  override fun onHostResume() {
    Log.e("ant", "resume")
    fab?.onResume()
  }

  override fun onHostPause() {
    Log.e("ant", "pause")
    fab?.onPause()
  }

  override fun onHostDestroy() {
  }
}
