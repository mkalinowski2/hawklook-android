package com.fewbits.hawklook

import android.app.Application
import org.koin.android.logger.AndroidLogger
import org.koin.core.Koin
import timber.log.Timber

class HawkApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      Timber.plant(HawkTimberTree())
      Koin.logger = AndroidLogger()
    }
  }

  class HawkTimberTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
      val superTag = super.createStackElementTag(element)
      val lineNumber = element.lineNumber
      val methodName = element.methodName
      return "[hlk] $superTag:$lineNumber#$methodName"
    }
  }
}
