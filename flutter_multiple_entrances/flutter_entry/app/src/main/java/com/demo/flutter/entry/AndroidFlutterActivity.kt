package com.demo.flutter.entry

import android.content.Context
import android.content.Intent
import io.flutter.embedding.android.FlutterActivity


class AndroidFlutterActivity : FlutterActivity() {


    companion object {
        val EXTRA_CACHED_ENGINE_ID = "cached_engine_id"
        val EXTRA_ROUTE = "extra_route"
        val EXTRA_DESTROY_ENGINE_WITH_ACTIVITY = "destroy_engine_with_activity"
        fun open(context: Context, route: String?) {

            val intent = Intent(context, AndroidFlutterActivity::class.java)
                .putExtra(EXTRA_CACHED_ENGINE_ID, "default_engine_id")
                .putExtra(EXTRA_ROUTE, route) // Activity 销毁时保留 FlutterEngine
                .putExtra(EXTRA_DESTROY_ENGINE_WITH_ACTIVITY, false)
            context.startActivity(intent)
        }
    }


    override fun onFlutterUiDisplayed() {
        super.onFlutterUiDisplayed()

        // 设置 Flutter 界面入口，注意不要在 onCreate 方法中调用，否则
        // Flutter 入口不会更新。
        intent.getStringExtra(EXTRA_ROUTE)?.let {
            FlutterTools.setInitRoute(it)
        }
    }

}