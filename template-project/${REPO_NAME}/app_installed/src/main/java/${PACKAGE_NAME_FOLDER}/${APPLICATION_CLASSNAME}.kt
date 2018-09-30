package ${PACKAGE_NAME}

import android.app.Application
import org.koin.android.logger.AndroidLogger
import org.koin.core.Koin
import timber.log.Timber

class ${APPLICATION_CLASSNAME} : Application() {

  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      Timber.plant(${APP_NAME}TimberTree())
      Koin.logger = AndroidLogger()
    }
  }

  class ${APP_NAME}TimberTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
      val superTag = super.createStackElementTag(element)
      val lineNumber = element.lineNumber
      val methodName = element.methodName
      return "[${APP_PREFIX}] $superTag:$lineNumber#$methodName"
    }
  }
}
