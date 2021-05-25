package com.demo.graphics

import android.content.Context
import android.graphics.*
import android.view.View

internal class CustomView1(context: Context?) : View(context) {

    private val mFillPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPath = Path()
    var mRadiusArray: FloatArray = floatArrayOf(
        10f, 10f,
        10f, 10f,
        0f, 0f,
        0f, 0f
    )
    //在这里我们将测试canvas提供的绘制图形方法
    override fun onDraw(canvas: Canvas) {

//        val bounds: Rect = Rect()
        val mRect = RectF()
//        mRect.set(
//            bounds.left.toFloat(), bounds.top.toFloat(),
//            bounds.right.toFloat(), bounds.bottom.toFloat()
//        )

        mRect.set(100f,100f,300f,300f)

        mPath.reset()
        mPath.addRoundRect(mRect,mRadiusArray, Path.Direction.CW)
//        mFillPaint.colorFilter = colorFilter
        //        mPaint.setColor(Color.parseColor("#000000"));
        mFillPaint.color = Color.parseColor("#1890FF")
        canvas.drawPath(mPath, mFillPaint)
    }
}