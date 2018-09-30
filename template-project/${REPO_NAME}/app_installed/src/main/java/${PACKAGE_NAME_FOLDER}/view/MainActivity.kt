package ${PACKAGE_NAME}.view

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import ${PACKAGE_NAME}.R

class MainActivity : FragmentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.${APP_PREFIX}_app_installed_activity_main)
  }
}
