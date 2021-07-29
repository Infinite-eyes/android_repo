package com.demo.settings.utils

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.demo.library.PermissionUtils
import com.demo.settings.R

//https://github.com/ly-android/PermissionUtils
class PermissionUtilsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //    if (PermissionChecker.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == 0) {
//      Toast.makeText(this, "有权限！", Toast.LENGTH_SHORT).show();
//    }
//        if (PermissionUtils.hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//            Toast.makeText(this, "有权限！", Toast.LENGTH_SHORT).show()
//        }
        findViewById<View>(R.id.btn).setOnClickListener {
            try {
                PermissionUtils.toPermissionSetting(this@PermissionUtilsActivity)
//                val intent =  Intent();
//                intent.action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
//                intent.data = Uri.parse("package:" + this.packageName);
//                startActivity(intent);
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }



    }
}