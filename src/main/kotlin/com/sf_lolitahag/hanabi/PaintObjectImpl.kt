/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.hanabi

import java.awt.Color

class PaintObjectImpl(x: Int, y: Int, private val r: Int, private val g: Int, private val b: Int, private var alpha: Int, private val tail: Int, private var index: Int,
                      private var moveAxisX: Int, private var moveAxisY: Int) : PaintObject, Cloneable {

    override var x: Int = 0
        private set
    override var y: Int = 0
        private set

    override val color: Color
        get() = Color(r, g, b, alpha * (tail - index) / tail)

    init {
        this.x = x
        this.y = y
    }

    override fun setMoveX(moveX: Int) {
        moveAxisX = moveX
    }

    override fun setMoveY(moveY: Int) {
        moveAxisY = moveY
    }

    override fun movePositionNext() {
        x += moveAxisX
        y += moveAxisY
    }

    override fun moveIndexNext() {
        index += 1
    }

    override fun setAlpha(alpha: Int) {
        this.alpha = alpha
    }

    override fun clone(): PaintObject {
        var o: PaintObject? = null
        try {
            o = super.clone() as PaintObject
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return o!!
    }
}
