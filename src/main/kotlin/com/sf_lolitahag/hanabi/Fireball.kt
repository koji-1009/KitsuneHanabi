/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.hanabi

import com.sf_lolitahag.Utils
import java.awt.Graphics

class Fireball : AbsPaintArray() {

    override fun init(x: Int, y: Int) {
        super.init(x, y)

        val tail = Utils.getRandRange(TAIL_COUNT_MIN, TAIL_COUNT_MAX)
        val alpha = Utils.getRandRange(ALPHA_MIN, ALPHA_MAX)
        initList(x, y, R, G, B, alpha, tail, 0, UPDATE_AXIS_Y)
    }

    override fun next() {
        addTop()
    }

    override fun drawObject(g: Graphics, fireball: PaintObject) {
        g.color = fireball.color
        g.fillOval(fireball.x, fireball.y, 4, 4)
    }

    companion object {

        private const val R = 255
        private const val G = 200
        private const val B = 200
        private const val ALPHA_MIN = 60
        private const val ALPHA_MAX = 200
        private const val TAIL_COUNT_MIN = 10
        private const val TAIL_COUNT_MAX = 25
        private const val UPDATE_AXIS_Y = -1
    }
}
