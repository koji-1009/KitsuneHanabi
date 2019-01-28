/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.hanabi

import java.awt.Graphics
import java.util.concurrent.CopyOnWriteArrayList
import java.util.stream.IntStream

abstract class AbsPaintArray {

    private val paintList = CopyOnWriteArrayList<PaintObject>()

    val topX: Int
        get() = paintList[0].x

    val topY: Int
        get() = paintList[0].y

    val topAlpha: Int
        get() = paintList[0].color.alpha

    open fun init(x: Int, y: Int) {
        paintList.clear()
    }

    fun draw(g: Graphics) {
        synchronized(paintList) {
            paintList.forEach { paintObject -> drawObject(g, paintObject) }
        }
    }

    @JvmOverloads
    protected fun initList(x: Int, y: Int, r: Int, g: Int, b: Int, alpha: Int, tail: Int, moveX: Int = 0,
                           moveY: Int = 0) {
        IntStream.range(0, tail).forEach { index -> paintList.add(PaintObjectImpl(x, y, r, g, b, alpha, tail, index, moveX, moveY)) }
    }

    protected fun addTop() {
        synchronized(paintList) {
            val newObject = paintList[0].clone()
            newObject.movePositionNext()

            paintList.forEach { it.moveIndexNext() }
            paintList.add(0, newObject)
            paintList.removeAt(paintList.size - 1)
        }
    }

    protected fun addTop(newMoveX: Int, newMoveY: Int) {
        synchronized(paintList) {
            val newObject = paintList[0].clone()
            newObject.setMoveX(newMoveX)
            newObject.setMoveY(newMoveY)
            newObject.movePositionNext()

            paintList.forEach { it.moveIndexNext() }
            paintList.add(0, newObject)
            paintList.removeAt(paintList.size - 1)
        }
    }

    protected fun updateAlpha(alpha: Int) {
        synchronized(paintList) {
            paintList.parallelStream().forEach { `object` -> `object`.setAlpha(alpha) }
        }
    }

    abstract operator fun next()

    protected abstract fun drawObject(g: Graphics, `object`: PaintObject)
}
