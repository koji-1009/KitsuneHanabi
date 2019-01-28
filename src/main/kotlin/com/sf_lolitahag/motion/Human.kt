/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.motion

import com.sf_lolitahag.Utils
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import java.awt.Image
import javax.swing.Timer

class Human(private val callback: Callback) : AbstractMotion() {
    private var index = 0
    private var repeatTime: Int = 0
    private var mode: Mode? = null
    private val timer = Timer(PAINT_INTERVAL_STAY) { e -> updateFileName() }

    val isSleepNow: Boolean
        get() = mode == Mode.SLEEP

    private val imageList: Array<String>
        get() {
            return when (mode) {
                Human.Mode.STAY -> STAY_LIST
                Human.Mode.NERU -> NERU_LIST
                Human.Mode.SLEEP -> SLEEP_LIST
                Human.Mode.OKOSU -> OKOSU_LIST
                Human.Mode.OKIRU -> OKIRU_LIST
                else -> STAY_LIST
            }
        }

    private val interval: Int
        get() {
            return when (mode) {
                Human.Mode.STAY -> PAINT_INTERVAL_STAY
                Human.Mode.NERU -> PAINT_INTERVAL_NERU
                Human.Mode.SLEEP -> PAINT_INTERVAL_SLEEP
                Human.Mode.OKOSU -> PAINT_INTERVAL_OKOSU
                Human.Mode.OKIRU -> PAINT_INTERVAL_OKIRU
                else -> PAINT_INTERVAL_STAY
            }
        }

    init {

        axisX = AXIS_X
        axisY = AXIS_Y

        val tmpClass = javaClass
        cache = AbstractMotion.CACHE_MANAGER.createCache(Human::class.java.simpleName,
                CacheConfigurationBuilder.newCacheConfigurationBuilder<String, Image>(String::class.java, Image::class.java, ResourcePoolsBuilder.heap(100)).build())
        for (fileName in IMAGE_LIST) {
            cache!!.put(fileName, Utils.getImageFromResources(tmpClass, fileName))
        }

        mode = Mode.STAY
        fileName = STAY_LIST[0]
        timer.start()
    }

    fun onSpaceKeyPress() {
        if (mode == Mode.STAY) {
            setNextMode()
        }
    }

    fun startOkosu() {
        repeatTime = Utils.getRandRange(0, 3)
        setNextMode()
    }

    private fun updateFileName() {
        when (mode) {
            Human.Mode.STAY -> {
            }
            Human.Mode.SLEEP -> {
                val list = imageList
                fileName = list[Utils.getRandInt(list.size)]
            }
            Human.Mode.OKOSU -> {
                if (repeatTime >= 0) {
                    val list = imageList
                    fileName = list[index]
                    if (index == 0) {
                        index = 1
                    } else {
                        index = 0
                        repeatTime -= 1
                    }
                } else {
                    setNextMode()
                }
            }
            else -> {
                var list = imageList
                index += 1
                if (index >= list.size - 1) {
                    setNextMode()
                    list = imageList
                }
                fileName = list[index]
            }
        }// nop
    }

    private fun setNextMode() {
        when (mode) {
            Human.Mode.STAY -> mode = Mode.NERU
            Human.Mode.NERU -> mode = Mode.SLEEP
            Human.Mode.SLEEP -> {
                mode = Mode.OKOSU
                callback.onStartOkosuMotion()
                updateFileName()
            }
            Human.Mode.OKOSU -> {
                mode = Mode.OKIRU
                callback.onFinishOkosuMotion()
                updateFileName()
            }
            Human.Mode.OKIRU -> mode = Mode.STAY
        }
        index = 0
        timer.delay = interval
    }

    private enum class Mode {
        STAY,
        NERU,
        SLEEP,
        OKOSU,
        OKIRU
    }

    interface Callback {

        fun onStartOkosuMotion()

        fun onFinishOkosuMotion()
    }

    companion object {
        private const val AXIS_X = 140
        private const val AXIS_Y = 330
        private const val PAINT_INTERVAL_STAY = 1000
        private const val PAINT_INTERVAL_NERU = 1750
        private const val PAINT_INTERVAL_SLEEP = 750
        private const val PAINT_INTERVAL_OKOSU = 750
        private const val PAINT_INTERVAL_OKIRU = 2000
        private val STAY_LIST = arrayOf("o_stay01")
        private val NERU_LIST = arrayOf("o_stay01", "neru01", "neru02", "neru03")
        private val SLEEP_LIST = arrayOf("neru03", "neru04")
        private val OKIRU_LIST = arrayOf("okiru01", "okiru02", "okiru03")
        private val OKOSU_LIST = arrayOf("okosu01", "okosu02")
        private val IMAGE_LIST = arrayOf("o_stay01", "neru01", "neru02", "neru03", "neru04", "okiru01", "okiru02", "okiru03", "okosu01", "okosu02")
    }
}
