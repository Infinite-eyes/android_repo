package com.demo.coroutine.carman.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.demo.coroutine.R
import com.demo.coroutine.carman.extensions.delayMain
import com.demo.coroutine.carman.extensions.requestIO
import com.demo.coroutine.carman.extensions.requestMain
import kotlinx.coroutines.*
import java.lang.NullPointerException
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

//https://juejin.cn/post/6953441828100112392
class MainTestActivity : AppCompatActivity() {

    init {
        lifecycleScope.launchWhenResumed {
            Log.d("init", "在类初始化位置启动协程")
        }
    }

    companion object Key : CoroutineContext.Key<ContinuationInterceptor>

    private lateinit var btn: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_test)
        btn = findViewById(R.id.btn)

        btn.setOnClickListener {
//            start()
//            withContextTest()
//            testCoroutineContext()
//            testCoroutineStart()
//            testUnDispatched()
//            testCoroutineScope()
//            testCoroutineScope2()
//            testCoroutineScope3()
//            testCoroutineScope4()
//            testCoroutineExceptionHandler()
//            testException()
//            testException3()
//            testException4()
//            testException5()
            testException6()
        }

//        lifecycleScope.launch {
//            delay(2000)
//            Toast.makeText(this@MainTestActivity,"haha",Toast.LENGTH_SHORT)
//        }
        requestMain {
            delay(2000)
            Toast.makeText(this@MainTestActivity,"haha",Toast.LENGTH_SHORT)
        }

        requestIO{
//            loadNetData()
        }

        delayMain(100){
            Toast.makeText(this@MainTestActivity,"haha",Toast.LENGTH_SHORT).show()
        }


    }


//    private fun start(){
//        val runBlockingJob = runBlocking {
//            Log.d("runBlocking","启动一个协程")
//            "我是runBlockingJob协程的返回值"
//        }
//        Log.d("runBlockingJob", "$runBlockingJob")
//
//        val launchJob =  GlobalScope.launch {
//            Log.d("launch","启动一个协程")
//        }
//        Log.d("launchJob", "$launchJob")
//
//        val asyncJob =  GlobalScope.async{
//            Log.d("async","启动一个协程")
//        }
//        Log.d("asyncJob", "$asyncJob")
//    }

//    private  fun start(){
//        GlobalScope.launch {
//            val launchJob = launch {
//                Log.d("launch","启动一个协程")
//            }
//            Log.d("launchJob","$launchJob")
//
//            val asyncJob = async{
//                Log.d("async","启动一个协程")
//                "我是async返回值"
//            }
//            Log.d("asyncJob.await",":${asyncJob.await()}")
//            Log.d("asyncJob","$asyncJob")
//        }
//    }

    private fun start() {
//        GlobalScope.launch(Dispatchers.Main){
        GlobalScope.launch {
            for (index in 1 until 10) {
                launch {
                    Log.d("launch$index", "启动一个协程")
                }
            }
        }
    }


    private fun withContextTest() {

        GlobalScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                "请求结果"
            }

            btn.text = result
        }


    }

    private fun testCoroutineContext() {
        val coroutineContext1 = Job() + CoroutineName("这是第一个上下文")
        Log.d("coroutineContext1", "$coroutineContext1 ")
        val coroutineContext2 = coroutineContext1 + Dispatchers.Default + CoroutineName("这是第二个上下文")
        Log.d("coroutineContext2", "$coroutineContext2")
        val coroutineContext3 = coroutineContext2 + Dispatchers.Main + CoroutineName("这是第三个上下文")
        Log.d("coroutineContext3", "$coroutineContext3")
    }


    private fun testCoroutineStart() {
        val defaultJob = GlobalScope.launch {
            Log.d("defaultJob", "CoroutineStart.DEFAULT")
        }
        defaultJob.cancel()

        val lazyJob = GlobalScope.launch(start = CoroutineStart.LAZY) {
            Log.d("lazyJob", "CoroutineStart.LAZY")
        }
//https://juejin.cn/post/6953287252373930021
        val atomicJob = GlobalScope.launch(start = CoroutineStart.ATOMIC) {
            Log.d("atomicJob", "CoroutineStart.ATOMIC挂起前")
            delay(100)
            Log.d("atomicJob", "CoroutineStart.ATOMIC挂起后")
        }
        atomicJob.cancel()

        val undispatchedJob = GlobalScope.launch(start = CoroutineStart.UNDISPATCHED) {
            Log.d("undispatchedJob", "CoroutineStart.UNDISPATCHED挂起前")
            delay(100)
            Log.d("atomicJob", "CoroutineStart.UNDISPATCHED挂起后")
        }
        undispatchedJob.cancel()
    }


    private fun testUnDispatched() {
//        //v1
//        GlobalScope.launch(Dispatchers.Main) {
//            val job = launch(Dispatchers.IO) {
//                Log.d("${Thread.currentThread().name}线程", "-> 挂起前")
//                delay(100)
//                Log.d("${Thread.currentThread().name}线程", "-> 挂起后")
//            }
//            Log.d("${Thread.currentThread().name}线程", "-> join前")
//            job.join()
//            Log.d("${Thread.currentThread().name}线程", "-> join 后")
//
//        }

        //v2
//        GlobalScope.launch(Dispatchers.Main) {
//            val job = launch(Dispatchers.IO, start = CoroutineStart.UNDISPATCHED) {
//                Log.d("${Thread.currentThread().name}线程", "-> 挂起前")
//                delay(100)
//                Log.d("${Thread.currentThread().name}线程", "-> 挂起后")
//            }
//            Log.d("${Thread.currentThread().name}线程", "-> join前")
//            job.join()
//            Log.d("${Thread.currentThread().name}线程", "-> join 后")
//        }

        GlobalScope.launch(Dispatchers.Main) {
            val job = launch(start = CoroutineStart.UNDISPATCHED) {
                Log.d("${Thread.currentThread().name}线程", "-> 挂起前")
                delay(100)
                Log.d("${Thread.currentThread().name}线程", "-> 挂起后")
            }

            Log.d("${Thread.currentThread().name}线程", "-> join前")
            job.join()
            Log.d("${Thread.currentThread().name}线程", "-> join后")
        }
    }

    private fun testCoroutineScope() {

        GlobalScope.launch(Dispatchers.Main) {
            Log.d("父协程上下文", "$coroutineContext")
            launch(CoroutineName("第一个子协程")) {
                Log.d("第一个子协程上下文", "$coroutineContext")
            }

            launch(Dispatchers.Unconfined) {
                Log.d("第二个子协程协程上下文", "$coroutineContext")
            }
        }
    }

    private fun testCoroutineScope2() {

        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d("exceptionHandler", "${coroutineContext[CoroutineName]} $throwable")
        }

        GlobalScope.launch(Dispatchers.Main + CoroutineName("scope1") + exceptionHandler) {

            Log.d("scope", "----------- 1")
            launch(CoroutineName("scope2") + exceptionHandler) {
                Log.d("scope", "---------- 2")
                throw NullPointerException("空指针")
                Log.d("scope", "-------- 3")
            }

            val scope3 = launch(CoroutineName("scope3") + exceptionHandler) {
                Log.d("scope", "----------- 4")
                delay(2000)
                Log.d("scope", "")
            }
            scope3.join()
            Log.d("scope", "---------- 6")
        }
    }

    private fun testCoroutineScope3() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d("exceptionHandler", "${coroutineContext[CoroutineName]} $throwable")
        }

        GlobalScope.launch(Dispatchers.Main + CoroutineName("scope1") + exceptionHandler) {

            supervisorScope {
                Log.d("scope", "----------- 1")
                launch(CoroutineName("scope2")) {
                    Log.d("scope", "--------- 2")
                    throw NullPointerException("空指针")
                    Log.d("scope", "----------- 3")
                    val scope3 = launch(CoroutineName("scope3")) {
                        Log.d("scope", "-------- 4")
                        delay(2000)
                        Log.d("scope", "-------- 5")
                    }
                    scope3.join()
                }

                val scope4 = launch(CoroutineName("scope4")) {
                    Log.d("scope", "--------- 6")
                    delay(2000)
                    Log.d("scope", "--------- 7")
                }
                scope4.join()
                Log.d("scope", "------------ 8")
            }
        }
    }

    private fun testCoroutineScope4() {

        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d("exceptionHandler", "${coroutineContext[CoroutineName]} $throwable")
        }

        val coroutineScope = CoroutineScope(SupervisorJob() + CoroutineName("coroutineScope"))

        GlobalScope.launch(Dispatchers.Main + CoroutineName("scope1") + exceptionHandler) {
            with(coroutineScope) {
                val scope2 = launch(CoroutineName("scope2") + exceptionHandler) {
                    Log.d("scope", "1-------- ${coroutineContext[CoroutineName]}")
                    throw NullPointerException("空指针")
                }

                val scope3 = launch(CoroutineName("scope3") + exceptionHandler) {
                    scope2.join()
                    Log.d("scope", "2------------- ${coroutineContext[CoroutineName]}")
                    delay(2000)
                    Log.d("scope", "3------------- ${coroutineContext[CoroutineName]}")
                }
                scope2.join()
                Log.d("scope", "4-------- ${coroutineContext[CoroutineName]}")
                coroutineScope.cancel()
                scope3.join()
                Log.d("scope", "5------------ ${coroutineContext[CoroutineName]}")
            }
            Log.d("scope", "6--------------- ${coroutineScope}")
        }
    }


    private suspend fun test() {

    }


    private fun testCoroutineExceptionHandler() {
        GlobalScope.launch {
            val job = launch {
                Log.d("${Thread.currentThread().name}", "抛出未捕获异常")
                throw NullPointerException("异常测试")
            }

            job.join()
            Log.d("${Thread.currentThread().name}","end")
        }
    }
//    https://juejin.cn/post/6954250061207306253
    private fun testException(){
        GlobalScope.launch {
            launch(start = CoroutineStart.UNDISPATCHED){
                Log.d("${Thread.currentThread().name}","我要开始抛异常了")
                try{
                    throw NullPointerException("异常测试")
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
            Log.d("${Thread.currentThread().name}","end")
        }
    }

    private fun testException2(){
        var a: MutableList<Int> = mutableListOf(1,2,3)

        GlobalScope.launch {
            launch {
                Log.d("${Thread.currentThread().name}","我要开始抛异常了")
                try{
                    launch {
                        Log.d("${Thread.currentThread().name}","${a[1]}")
                    }
                    a.clear()
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }

    private fun testException3(){
        val exceptionHandler = CoroutineExceptionHandler{ coroutineContext, throwable ->
            Log.d("exceptionHandler","${coroutineContext[CoroutineName]} : $throwable")
        }
        GlobalScope.launch(CoroutineName("异常处理")+ exceptionHandler){
            val job = launch {
                Log.d("${Thread.currentThread().name}","我要开始抛异常了")
                throw NullPointerException("异常测试")
            }
            Log.d("${Thread.currentThread().name}","end")
        }
    }

    private fun testException4(){
        val exceptionHandler = CoroutineExceptionHandler{ coroutineContext, throwable ->
            Log.d("exceptionHandler","${coroutineContext[CoroutineName]} 处理异常: $throwable")
        }

        GlobalScope.launch(CoroutineName("父协程")+ exceptionHandler){
            val job = launch(CoroutineName("子协程")){

                Log.d("${Thread.currentThread().name}","我要开始抛异常")
                for(index in 0..10){
                    launch(CoroutineName("孙子协程$index")){
                        Log.d("${Thread.currentThread().name}","${coroutineContext[CoroutineName]}")
                    }
                }
                throw NullPointerException("空指针异常")
            }

            for(index in 0..10){
                launch(CoroutineName("子协程$index")){
                    Log.d("${Thread.currentThread().name}","${coroutineContext[CoroutineName]}")
                }
            }
            try{
                job.join()
            }catch (e: Exception){
                e.printStackTrace()
            }
            Log.d("${Thread.currentThread().name}","end")
        }
    }



    private fun testException5(){
        val exceptionHandler = CoroutineExceptionHandler{ coroutineContext, throwable ->
            Log.d("exceptionHandler","${coroutineContext[CoroutineName].toString()} 处理异常: $throwable")
        }

        GlobalScope.launch(exceptionHandler){
            supervisorScope {

                launch(CoroutineName("异常子协程")){
                    Log.d("${Thread.currentThread().name}","我要开始抛异常了")
                    throw NullPointerException("空指针异常")
                }

                for(index in 0..10){
                   launch(CoroutineName("子协程$index")){
                       Log.d("${Thread.currentThread().name}正常执行","$index")
                       if(index % 3 == 0){
                           throw NullPointerException("子协程${index}空指针异常")
                       }
                   }
                }
            }
        }
    }

    private fun testException6(){
        val exceptionHandler = CoroutineExceptionHandler{coroutineContext, throwable ->
            Log.d("exceptionHandler","${coroutineContext[CoroutineName].toString()} 处理异常: $throwable")
        }
        val supervisorScope = CoroutineScope(SupervisorJob() + exceptionHandler)

        with(supervisorScope){

            launch(CoroutineName("异常子协程")){
                Log.d("${Thread.currentThread().name}","我要开始抛异常了")
                throw NullPointerException("空指针异常")
            }

            for(index in 0..10){
                launch(CoroutineName("子协程$index")){
                    Log.d("${Thread.currentThread().name}正常执行","$index")
                    if(index % 3 == 0){
                     throw NullPointerException("子协程${index}空指针异常")
                    }
                }
            }
        }
    }

    private fun start2(){
        GlobalScope.launch {
            launch{
                throw NullPointerException("空指针") //!子协程产生异常会产生相互干扰。子协程异常取消会导致父协程取消，同时其他子协程也将会被取消。
            }
            val result = withContext(Dispatchers.IO){
//                requestDa
                "请求结果"
            }
            btn.text = result// ! 因为我们的GlobalScope默认使用的是Dispatchers.Default，这会导致我们在非主线程上刷新UI。
            launch {

            }
        }

//        如果我们这个时候activity或者framgent退出，因为协程是在GlobalScope中运行，所以即使activity或者framgent退出,这个协程还是在运行，这个时候会产生各种泄露问题。同时此协程当执行到刷新操作时，因为我们的界面已经销毁，这个时候执行UI刷新将会产生崩溃。

    }


//    /// test 3
//    var job:Job? = null
//    private fun start3(){
//        job = GlobalScope.launch(Dispatchers.Main + SupervisorJob()){
//            launch{
//                throw NullPointerException("空指针")
//            }
//            val result = withContext(Dispatchers.IO){
//                "请求结果"
//            }
//            launch {
//
//            }
//            btn.text = result
//        }
//    }
//    override fun onDestroy() {
//        super.onDestroy()
//        job?.cancel()
//    }
//    /// test 3 ^^^^^^^

//    //    /// test 4
//    private val mainScope = MainScope()
//    private fun start4(){
//        mainScope.launch{
//            launch{
//                throw NullPointerException("空指针")
//            }
//            val result = withContext(Dispatchers.IO){
//                "请求结果"
//            }
//            launch{
//
//            }
//            btn.text = result
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mainScope.cancel()
//    }
////    /// test 4 ^^^^^^^


    init{
        lifecycleScope.launchWhenResumed {
            Log.d("init","在类初始化位置启动协程")
        }
    }







}