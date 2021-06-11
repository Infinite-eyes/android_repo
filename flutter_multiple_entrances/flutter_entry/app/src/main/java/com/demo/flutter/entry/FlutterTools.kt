package com.demo.flutter.entry

import android.content.Context
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel


object FlutterTools {

    const val ENGINE_ID = "default_engine_id"
    const val ROUTE_PAGE_A = "/page_a"
    const val ROUTE_PAGE_B = "/page_b"
    private const val METHOD_CHANNEL = "com.example/method_channel"
    private var sFlutterEngine: FlutterEngine? = null
    private var sMethodChannel: MethodChannel? = null

    fun preWarmFlutterEngine(context: Context) {
        if (null == sFlutterEngine) {
            sFlutterEngine = FlutterEngine(context)
            sFlutterEngine!!.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
            )
            sMethodChannel = MethodChannel(sFlutterEngine!!.dartExecutor, METHOD_CHANNEL)
            FlutterEngineCache.getInstance().put(ENGINE_ID, sFlutterEngine)
        }
    }

    fun setInitRoute(route: String) {
        sMethodChannel!!.invokeMethod("setInitRoute", route)
    }

    fun destroyEngine() {
        if (sFlutterEngine != null) {
            sFlutterEngine!!.destroy()
        }
    }
}