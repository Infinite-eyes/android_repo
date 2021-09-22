package com.demo.coroutine

import android.util.Log
import kotlinx.coroutines.*
import org.junit.Test
import kotlin.system.measureTimeMillis


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        print("start")

    }


    //https://juejin.cn/post/6987724340775108622
//    https://juejin.cn/post/6953441828100112392

    @Test
    fun runBloTest() {
        print("start")
        //context上下文使用默认值,阻塞当前线程，直到代码块中的逻辑完成
        runBlocking {
            //这里是协程体
            delay(1000)//挂起函数，延迟1000毫秒
            print("runBlocking")
        }
        print("end")
    }

    @Test
    fun launchTest() {

        print("start")

        GlobalScope.launch {
            delay(1000)
            print("GlobalScope.launch")
        }
        print("end")
    }

    @Test
    fun launchTest2() {
        print("start")

        val job = CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            print("CoroutineScope.launch")
        }

        print("end")

    }

    @Test
    fun launchTest3() {
        print("start")
        GlobalScope.launch {
            delay(1000)
            print("CoroutineScop.launch")

            launch {
                delay(1500)
                print("launch 子协程")
            }
        }
        print("end")
    }


    @Test
    fun asyncTest1() {
        print("start")

        GlobalScope.launch {

            val deferred: Deferred<String> = async {
                delay(2000)
                print("asyncOne")
                "HelloWord"
            }

            val result = deferred.await()
            print("result == $result")
        }

        print("end")

    }

    @Test
    fun asyncTest2() {
        print("start")
        GlobalScope.launch {
            val time = measureTimeMillis {
                val deferredOne: Deferred<Int> = async {
                    delay(2000)
                    print("asyncOne")
                    100
                }


                val deferredTwo: Deferred<Int> = async {
                    delay(3000)
                    print("asyncTwo")
                    200
                }


                val deferredThr: Deferred<Int> = async {
                    delay(4000)
                    print("asyncThr")
                    300
                }

                val result = deferredOne.await() + deferredTwo.await() + deferredThr.await()
                print("result == $result")
            }
            print("耗时 $time ms")
        }
        print("end")
    }


    fun jobTest() = runBlocking {
        val startTime = System.currentTimeMillis()

        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0

            while (isActive) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    print("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500
                }
            }
        }

        delay(1200)
        print("等待1.2秒后")

        job.cancelAndJoin()
        print("协程被取消并等待完成")


    }


    fun scopeTest() {

        GlobalScope.launch {
            launch {
                print("GlobalScope的子协程")
            }

            launch {
                print("GlobalScope的第二个子协程")
            }
        }

        val mainScope = MainScope()

        mainScope.launch {

        }
    }


    fun dispatcherTest() {
        val mainScope = MainScope()
        mainScope.launch {
            launch(Dispatchers.Main) {
                print("主线程调度器")
            }

            launch(Dispatchers.Default) {
                print("默认调度器")
            }

            launch(Dispatchers.Unconfined) {
                print("任意调度器")
            }

            launch(Dispatchers.IO) {
                print("IO调度器")
            }

        }

    }


    fun testWithContext() {

        GlobalScope.launch(Dispatchers.Main) {

            val result = withContext(Dispatchers.IO) {
                //网络请求（IO 线程）
            }

//更新 UI（主线程）

        }

    }



    fun testCoroutineName(){


        //v1
        GlobalScope.launch(CoroutineName("GlobalScope")){
            launch(CoroutineName("CoroutineA")){
                val coroutineName = coroutineContext[CoroutineName]
                print(coroutineName)
            }

        }


        //v2
        var context = CoroutineName("协程1") + Dispatchers.Main
        print("context == $context")

        context += Dispatchers.IO
        print("context == $context")

        val contextResult = context.minusKey(context[CoroutineName]!!.key)
        print("contextResult == $contextResult")

    }




    fun start(){

//        GlobalScope.launch(Dispatchers.Main) {  //同步执行
//            for(index in 1 until 10){
//             launch {
//                 Log.d("launch$index","启动一个协程")
//             }
//            }
//        }

        GlobalScope.launch {
            for(index in 1 until 10){
                //并发执行
                launch{
                    Log.d("launch$index","启动一个协程")
                }
            }
        }


    }

    fun testDispatchers(){
        GlobalScope.launch(Dispatchers.Main){
            val result = withContext(Dispatchers.IO){
                //网络请求...
                "请求结果"
            }
        }
    }








}