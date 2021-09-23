package com.demo.hilt.bean

import com.demo.hilt.bean.annotation.BindElectricEngine
import com.demo.hilt.bean.annotation.BindGasEngine
import com.demo.hilt.engine.Engine
import javax.inject.Inject


class Truck @Inject constructor(val driver: Driver){


    @BindGasEngine
    @Inject
    lateinit var gasEngine : Engine

    @BindElectricEngine
    @Inject
    lateinit var electricEngine: Engine


    fun deliver(){
        gasEngine.start()
        electricEngine.start()
        print("Truck is delivering cargo. Driven by $driver")
        gasEngine.shutdown()
        electricEngine.shutdown()
    }



}