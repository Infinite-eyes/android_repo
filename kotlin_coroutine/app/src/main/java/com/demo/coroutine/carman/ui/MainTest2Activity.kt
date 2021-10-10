package com.demo.coroutine.carman.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.demo.coroutine.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

//https://juejin.cn/post/6992629783674748936
//https://juejin.cn/post/6987724340775108622#heading-28
class MainTest2Activity : AppCompatActivity() {

    private lateinit var btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_test)
         btn = findViewById(R.id.btn)
        btn.setOnClickListener {
            testChannel()
        }
    }


    fun testChannel() {

        //v1
//        GlobalScope.launch {
//
//            val channel = Channel<Int> {  }
//            launch {
//                for(i in 1..3){
//                 delay(100)
//                 channel.send(i)
//                }
//                channel.close()
//            }
//
//            launch {
//                repeat(3){
//                    val receive = channel.receive()
//                    print("接收 $receive")
//                }
//            }
//        }


        //2

        GlobalScope.launch {
            val channel = produce<Int> {
                for (i in 1..3) {
                    delay(100)
                    send(i)
                }
            }

            launch {
                for (value in channel) {
                    print("接收 $value")
                }
            }
        }
    }


    fun testFlow() {


        //v1
//        lifecycleScope.launch {
//            flow<Int> {
//                for (i in 1..3) {
//                    delay(200)
//                    emit(i)
//                }
//            }.collect{
//                print("收集:$it")
//            }
//        }
//

        //v2

//        val flow = flow {
//            for (i in 1..3) {
//                delay(200)
//                emit(i)
//            }
//        }
//
//        lifecycleScope.launch{
//            flow.collect{print("$it")}
//            flow.collect{print("it")}
//        }


        //v3

//        lifecycleScope.launch {
//
//            flow {
//                for (i in 1..3) {
//                    delay(200)
//                    emit(i)
//                }
//            }.flowOn(Dispatchers.IO).collect { value ->
//
//
//            }
//        }

        //v4
//        lifecycleScope.launch{
//            flow{
//                emit(10)
//                throw NullPointerException()
//            }.catch{ e->
//                print("caught error: $e")
//            }.collect{
//                print("收集:$it")
//            }
//        }

        //v5
//        lifecycleScope.launch{
//            flow{
//                emit(10)-
//            }.onCompletion{
//                print("Flow 操作完成")
//            }.collect{
//                print("收集:$it")
//            }
//        }


        //v6
//        lifecycleScope.launch{
//            flow{
//                emit(10)
//            }.onCompletion{
//                print("Flow 操作完成")
//            }.collect{
//                print("收集:$it")
//            }
//        }

        //v7 取消
//        lifecycleScope.launch{
//            val job = launch{
//
//                val intFlow = flow{
//                    (1..5).forEach{
//                        delay(1000)
//                        emit(it)
//                    }
//                }
//
//
//                intFlow.collect{
//                    print(it)
//                }
//            }
//
//            delay(3500)
//            job.cancelAndJoin()
//        }

        //v8 背压
//        lifecycleScope.launch{
//            val time  = measureTimeMillis{
//                flow{
//                    for(i in 1..3){
//                        delay(100)
//                        emit(i)
//                    }
//                }.buffer()
//                    .collect{ value ->
//                        delat(300)
//                        print(value)
//                    }
//            }
//        }


        //v9
//        lifecycleScope.launch {
//            val time = measureTimeMillis {
//
//                flow {
//                    for (i in 1..3) {
//                        delay(100)
//                        emit(i)
//                    }
//                }.conflate()
//                    .collect(value ->
//                        delay(300)
//                        print(value)
//                )
//            }
//
//            print("收集耗时:$time ms")
//        }

        //v10
        lifecycleScope.launch {
            val time = measureTimeMillis {
                flow{
                    for(i in 1..3){
                        delay(100)
                        emit(i)
                    }
                }.collectLatest{ value->
                    print("收集的值:$value")
                    delay(300)
                    print("完成:$value")
                }
            }
            print("收集耗时:$time ms")
        }


    }


}