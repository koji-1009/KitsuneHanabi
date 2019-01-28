/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.hanabi

import com.sf_lolitahag.Utils
import java.awt.Graphics

class Spark : AbsPaintArray() {
    private var gapX: Double = 0.toDouble()
    private var gapY: Double = 0.toDouble()

    override fun init(x: Int, y: Int) {
        super.init(x, y)

        val r = Utils.getRandBaseCoe(COLOR_R_BASE, COLOR_R_COEFFICIENT)
        val g = Utils.getRandBaseCoe(COLOR_GB_BASE, COLOR_GB_COEFFICIENT)
        val b = Utils.getRandBaseCoe(COLOR_GB_BASE, COLOR_GB_COEFFICIENT)
        val tail = Utils.getRandRange(TAIL_COUNT_MIN, TAIL_COUNT_MAX)
        initList(x, y, r, g, b, ALPHA_BASE, tail)

        val roll = Math.random() * Math.PI * 2.0
        val coefficient = Utils.getRandBaseCoe(EXPLOSION_BASE, EXPLOSION_COEFFICIENT).toDouble()
        gapX = coefficient * Math.cos(roll)
        gapY = coefficient * Math.sin(roll)
    }

    override fun next() {
        gapY += GRAVITY
        val coefficient = RESIST * Math.pow(Math.pow(gapX, 2.0) + Math.pow(gapY, 2.0), 0.5)
        gapX -= coefficient * gapX
        gapY -= coefficient * gapY

        if (Math.floor(gapX) == 0.0) {
            gapX = 0.0
        }
        addTop(gapX.toInt(), gapY.toInt())
        var alpha = topAlpha - Utils.getRandRange(ALPHA_DECREASE_MIN, ALPHA_DECREASE_MAX)
        alpha = if (alpha > 0) alpha else 0
        updateAlpha(alpha)
    }

    override fun drawObject(g: Graphics, spark: PaintObject) {
        g.color = spark.color
        g.fillOval(spark.x, spark.y, 2, 2)
    }

    companion object {
        private const val ALPHA_BASE = 225
        private const val TAIL_COUNT_MIN = 5
        private const val TAIL_COUNT_MAX = 30
        private const val COLOR_R_BASE = 200
        private const val COLOR_R_COEFFICIENT = 50
        private const val COLOR_GB_BASE = 50
        private const val COLOR_GB_COEFFICIENT = 200
        private const val EXPLOSION_BASE = 15
        private const val EXPLOSION_COEFFICIENT = 20
        private const val ALPHA_DECREASE_MIN = 10
        private const val ALPHA_DECREASE_MAX = 30
        private const val RESIST = 0.05
        private const val GRAVITY = 0.7
    }
}
