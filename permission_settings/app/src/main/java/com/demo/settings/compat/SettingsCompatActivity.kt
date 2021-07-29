package com.demo.settings.compat

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.demo.settings.R
import java.io.*


//https://github.com/czy1121/settingscompat

class SettingsCompatActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var vResult: TextView
    lateinit var vFloat: FloatView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity_main)
        vFloat = FloatView(this)
        vResult = findViewById(R.id.result)
        findViewById<TextView>(R.id.result).setOnClickListener(this)
        findViewById<TextView>(R.id.check).setOnClickListener(this)
        findViewById<TextView>(R.id.manage).setOnClickListener(this)
        findViewById<TextView>(R.id.toggle).setOnClickListener(this)
        findViewById<TextView>(R.id.detail).setOnClickListener(this)
        val tv = findViewById<TextView>(R.id.info) as TextView
        tv.movementMethod = ScrollingMovementMethod.getInstance()
        tv.text = readString("/system/build.prop")
        if (SettingsCompat.canDrawOverlays(this)) {
            vFloat.attach()
        } else {
            vFloat.detach()
        }
    }

    fun checkPermission() {
        if (SettingsCompat.canDrawOverlays(this)) {
            vResult.setText(
                RomUtil.getVersion().toString() + "\n" + RomUtil.getName() + "\ngranted"
            )
            //            vFloat.attach();
        } else {
            vResult.setText(RomUtil.getVersion().toString() + "\n" + RomUtil.getName() + "\ndenied")
            //            vFloat.detach();
        }
    }

    override fun onResume() {
        super.onResume()
        checkPermission()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.detail -> {
                val intent1 = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent1.data = Uri.fromParts("package", this.packageName, null)
                startActivity(intent1)
            }
            R.id.result -> {
                val manager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                manager.setPrimaryClip(
                    ClipData.newPlainText(
                        "build.prop",
                        readString("/system/build.prop")
                    )
                )
            }
            R.id.check -> {
                checkPermission()
                if (SettingsCompat.canDrawOverlays(this)) {
                    vFloat.attach()
                } else {
                    vFloat.detach()
                }
            }
            R.id.manage -> SettingsCompat.manageDrawOverlays(this)
            R.id.toggle -> {
                val granted1: Boolean = SettingsCompat.canDrawOverlays(this)
                SettingsCompat.setDrawOverlays(this, !granted1)
                val granted2: Boolean = SettingsCompat.canDrawOverlays(this)
                vResult.setText(
                    RomUtil.getVersion()
                        .toString() + "\n" + RomUtil.getName() + "\ngranted: " + granted1 + ", " + granted2
                )
                if (granted2) {
                    vFloat.attach()
                } else {
                    vFloat.detach()
                }
            }
        }
    }

    fun readString(file: String?): String? {
        var input: InputStream? = null
        val output = ByteArrayOutputStream()
        try {
            input = FileInputStream(File(file))
            val buffer = ByteArray(1024 * 4)
            var n: Int
            while (-1 != input.read(buffer).also { n = it }) {
                output.write(buffer, 0, n)
            }
            output.flush()
            return output.toString("UTF-8")
        } catch (e: IOException) {
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (output != null) {
                try {
                    output.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return ""
    }
}