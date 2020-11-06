package com.reactnativeantourage

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import com.antourage.weaverlib.ui.fab.AntourageFab
import com.facebook.react.bridge.*
import com.facebook.react.common.MapBuilder
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.uimanager.events.RCTEventEmitter
import javax.annotation.Nonnull
import kotlin.math.roundToInt


class AntourageViewManager : SimpleViewManager<AntourageFab?>(), View.OnClickListener, LifecycleEventListener {

  companion object {
    private const val REACT_CLASS = "AntourageView"
    private const val TAG = "AntourageFabLogs"
    private const val MAX_HORIZONTAL_MARGIN = 50
    private const val MAX_VERTICAL_MARGIN = 220
  }

  private var fab: AntourageFab? = null
  private var horizontalMargin: Int = 0
  private var verticalMargin: Int = 0
  private var widgetPosition: AntourageFab.WidgetPosition = AntourageFab.WidgetPosition.bottomRight
  private var wereMarginsApplied: Boolean = false


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
    widgetPosition = AntourageFab.WidgetPosition.bottomRight
    horizontalMargin = 0
    verticalMargin = 0
    fab = AntourageFab(reactContext)
    fab?.let { setPosition(it, AntourageFab.WidgetPosition.bottomRight.name, true) }
    return fab as AntourageFab
  }

  @ReactProp(name = "widgetPosition")
  fun setPosition(button: AntourageFab, position: String?) {
    Log.d(TAG, "setPosition: $position")
    setPosition(button, position, false)
  }

  @ReactProp(name = "widgetMargins")
  fun setMargins(button: AntourageFab, position: ReadableMap) {
    try {
      horizontalMargin = position.getInt("horizontal").validateHorizontalMarginForFab(button.context)
      verticalMargin = position.getInt("vertical").validateVerticalMarginForFab(button.context)
    } catch (e: NoSuchKeyException) {
      Log.e(TAG, "wrong parameters in setMargins method")
    }
    applyMargins()
  }

  private fun setPosition(button: AntourageFab, position: String?, justResetPosition: Boolean) {
    position?.let {
      try {
        widgetPosition = AntourageFab.WidgetPosition.valueOf(it)
      } catch (e: IllegalArgumentException) {
        Log.e(TAG, "INVALID position value: $widgetPosition | $e")
      }

      button.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
          button.viewTreeObserver.removeOnGlobalLayoutListener(this)
          val parent = button.parent as View
          when (position) {
            AntourageFab.WidgetPosition.bottomLeft.name -> {
              button.x = 0f
              button.y = (parent.height - button.height).toFloat()
            }
            AntourageFab.WidgetPosition.bottomRight.name -> {
              button.x = (parent.width - button.width).toFloat()
              button.y = (parent.height - button.height).toFloat()
            }
            AntourageFab.WidgetPosition.bottomMid.name -> {
              button.x = ((parent.width / 2).toDouble().roundToInt() - (button.width / 2).toDouble().roundToInt()).toFloat()
              button.y = (parent.height - button.height).toFloat()
            }
            AntourageFab.WidgetPosition.midLeft.name -> {
              button.x = 0f
              button.y = ((parent.height / 2).toDouble().roundToInt() - (button.height / 2).toDouble().roundToInt()).toFloat()
            }
            AntourageFab.WidgetPosition.midRight.name -> {
              button.x = (parent.width - button.width).toFloat()
              button.y = ((parent.height / 2).toDouble().roundToInt() - (button.height / 2).toDouble().roundToInt()).toFloat()
            }
            AntourageFab.WidgetPosition.topLeft.name -> {
              button.x = 0f
              button.y = 0f
            }
            AntourageFab.WidgetPosition.topMid.name -> {
              button.x = ((parent.width / 2).toDouble().roundToInt() - (button.width / 2).toDouble().roundToInt()).toFloat()
              button.y = 0f
            }
            AntourageFab.WidgetPosition.topRight.name -> {
              button.x = (parent.width - button.width).toFloat()
              button.y = 0f
            }
            else -> {
              button.x = (parent.width - button.width).toFloat()
              button.y = (parent.height - button.height).toFloat()
            }
          }
          if (!justResetPosition) applyMargins()
        }
      })
    }
  }

  private fun applyMargins() {
    if (wereMarginsApplied) {
      fab?.let { setPosition(it, widgetPosition.name, true) }
    }
    wereMarginsApplied = true
    fab!!.viewTreeObserver!!.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
      override fun onGlobalLayout() {
        fab!!.viewTreeObserver!!.removeOnGlobalLayoutListener(this)
        when (widgetPosition) {
          AntourageFab.WidgetPosition.topLeft -> {
            fab!!.x += horizontalMargin
            fab!!.y += verticalMargin
          }
          AntourageFab.WidgetPosition.topMid -> {
            fab!!.y += verticalMargin
          }
          AntourageFab.WidgetPosition.topRight -> {
            fab!!.x -= horizontalMargin
            fab!!.y += verticalMargin
          }
          AntourageFab.WidgetPosition.midLeft -> {
            fab!!.x += horizontalMargin
          }
          AntourageFab.WidgetPosition.midRight -> {
            fab!!.x -= horizontalMargin
          }
          AntourageFab.WidgetPosition.bottomLeft -> {
            fab!!.x += horizontalMargin
            fab!!.y -= verticalMargin
          }
          AntourageFab.WidgetPosition.bottomMid -> {
            fab!!.y -= verticalMargin
          }
          AntourageFab.WidgetPosition.bottomRight -> {
            fab!!.x -= horizontalMargin
            fab!!.y -= verticalMargin
          }
        }
      }
    })
  }

  @ReactProp(name = "widgetLocale")
  fun setLocale(button: AntourageFab, locale: String?) {
    Log.d(TAG, "setLocale: $locale")
    locale?.let { button.setLocale(it) }
  }

  /** checks if values are in range and converts it to pixels*/
  private fun Int.validateHorizontalMarginForFab(context: Context): Int {
    return when {
      this < 0 -> 0
      this > MAX_HORIZONTAL_MARGIN -> dp2px(context, MAX_HORIZONTAL_MARGIN.toFloat()).toInt()
      else -> dp2px(context, this.toFloat()).toInt()
    }
  }

  /** checks if values are in range and converts it to pixels*/
  private fun Int.validateVerticalMarginForFab(context: Context): Int {
    return when {
      this < 0 -> 0
      this > MAX_VERTICAL_MARGIN -> dp2px(context, MAX_VERTICAL_MARGIN.toFloat()).toInt()
      else -> dp2px(context, this.toFloat()).toInt()
    }
  }

  private fun dp2px(context: Context, dipValue: Float): Float {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics)
  }

  override fun getExportedCustomDirectEventTypeConstants(): MutableMap<String, Any>? {
    return MapBuilder.of(
      "onViewerAppear",
      MapBuilder.of("registrationName", "onViewerAppear"),
      "onViewerDisappear",
      MapBuilder.of("registrationName", "onViewerDisappear")
    )
  }

  override fun onHostResume() {
    Log.d(TAG, "onResume")
    fab?.onResume()
  }

  override fun onHostPause() {
    Log.d(TAG, "onPause")
    fab?.onPause()
  }

  override fun onHostDestroy() {
  }
}
