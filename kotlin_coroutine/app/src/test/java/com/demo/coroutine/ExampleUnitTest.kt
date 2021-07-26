package com.demo.coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test


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
}