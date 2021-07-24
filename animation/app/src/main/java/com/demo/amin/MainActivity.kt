package com.demo.amin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.demo.amin.databinding.DialogLayoutBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding: DialogLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this@MainActivity),
            R.layout.dialog_layout,
            null, false
        )

        val builder = AlertDialog.Builder(this@MainActivity, R.style.dialog_normal)
        builder.setView(binding.root)
        val dialog = builder.setCancelable(true).create()

        val dialogWindow: Window = dialog.window!!
        val lp = dialogWindow.attributes
        dialogWindow.decorView.setPadding(0, 0, 0, 0)
        dialogWindow.setGravity(Gravity.CENTER or Gravity.CENTER)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialogWindow.attributes = lp



        findViewById<TextView>(R.id.btn_top_in).setOnClickListener {
            dialogWindow.setWindowAnimations(R.style.dialogTopIn)
            dialog.show()
        }

        findViewById<TextView>(R.id.btn_bottom_in).setOnClickListener {
            dialogWindow.setWindowAnimations(R.style.dialogBottomIn)
            dialog.show()
        }

        findViewById<TextView>(R.id.btn_left_in).setOnClickListener {
            dialogWindow.setWindowAnimations(R.style.dialogLeftIn)
            dialog.show()
        }

        findViewById<TextView>(R.id.btn_right_in).setOnClickListener {
            dialogWindow.setWindowAnimations(R.style.dialogRightIn)
            dialog.show()
        }
        findViewById<TextView>(R.id.btn_center_in).setOnClickListener {
            dialogWindow.setWindowAnimations(R.style.dialogCenterIn)
            dialog.show()
        }



    }

}