/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.hanabi

import com.sf_lolitahag.Utils
import java.awt.Graphics
import java.util.*
import javax.swing.Timer

class Firework {
    private var isRun = false
    private var isFireballShow = false
    private var isSparksShow = false
    private val fireball = Fireball()
    private val sparks = ArrayList<Spark>()
    private val updateTimer = Timer(TIMER_POSITION_UPDATE_FIREBALL
    ) { e -> updatePosition() }
    private var fireballTimer: Timer? = null
    private var sparkTimer: Timer? = null

    val isNotRun: Boolean
        get() = !isRun

    fun draw(g: Graphics) {
        if (isFireballShow) {
            fireball.draw(g)
        } else if (isSparksShow) {
            sparks.parallelStream().forEach { spark -> spark.draw(g) }
        }
    }

    fun startFireball() {
        if (Math.random() > LAUNCH_PERCENT) {
            isRun = true

            initFireball(Utils.getRandBaseCoe(POSITION_BASE, POSITION_GAP), POSITION_BASE)
            fireballTimer = Timer(Utils.getRandBaseCoe(FIREBALL_LIFETIME_BASE, FIREBALL_LIFETIME_COEFFICIENT)
            ) { e -> onFinishFireball() }
            fireballTimer!!.start()
            isFireballShow = true

            updateTimer.delay = TIMER_POSITION_UPDATE_FIREBALL
            updateTimer.start()
        }
    }

    private fun startSpark() {
        initSpark(fireball.topX, fireball.topY)
        isSparksShow = true
        sparkTimer = Timer(Utils.getRandBaseCoe(SPARK_LIFETIME_BASE, SPARK_LIFETIME_COEFFICIENT)
        ) { e -> onFinishSpark() }
        sparkTimer!!.start()

        updateTimer.delay = TIMER_POSITION_UPDATE_SPARK
        updateTimer.restart()
    }

    private fun initFireball(x: Int, y: Int) {
        fireball.init(x, y)
    }

    private fun initSpark(x: Int, y: Int) {
        sparks.clear()
        val sparkCount = Utils.getRandRange(SPARK_COUNT_MIN, SPARK_COUNT_MAX)
        for (index in 0 until sparkCount) {
            sparks.add(Spark())
        }
        sparks.forEach { spark -> spark.init(x, y) }
    }

    private fun updatePosition() {
        if (isFireballShow) {
            fireball.next()
        } else if (isSparksShow) {
            sparks.forEach { it.next() }
        }
    }

    private fun onFinishFireball() {
        updateTimer.stop()

        isFireballShow = false
        fireballTimer!!.stop()

        startSpark()
    }

    private fun onFinishSpark() {
        isSparksShow = false
        sparkTimer!!.stop()

        updateTimer.stop()
        isRun = false
    }

    companion object {

        private const val POSITION_BASE = 400
        private const val POSITION_GAP = 300
        private const val TIMER_POSITION_UPDATE_FIREBALL = 30
        private const val TIMER_POSITION_UPDATE_SPARK = 150
        private const val SPARK_COUNT_MIN = 50
        private const val SPARK_COUNT_MAX = 250
        private const val FIREBALL_LIFETIME_BASE = 3000
        private const val FIREBALL_LIFETIME_COEFFICIENT = 2500
        private const val SPARK_LIFETIME_BASE = 1250
        private const val SPARK_LIFETIME_COEFFICIENT = 1000
        private const val LAUNCH_PERCENT = 0.85
    }
}
