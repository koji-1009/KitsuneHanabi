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

class Kitsune(private val callback: Callback) : AbstractMotion() {
    private var index = 0
    private var mode: Mode? = null
    private val sleepChecker = Timer(CHECK_INTERVAL) { e -> checkHumanSleep() }
    private val timer = Timer(PAINT_INTERVAL_STAY) { e -> updateFileName() }

    private val imageList: Array<String>
        get() {
            return when (mode) {
                Kitsune.Mode.STAY -> STAY_LIST
                Kitsune.Mode.KIZUKU -> KIZUKU_LIST
                Kitsune.Mode.MANZOKU -> MANZOKU_LIST
                else -> STAY_LIST
            }
        }

    init {

        axisX = AXIS_X
        axisY = AXIS_Y

        val tmpClass = javaClass
        cache = AbstractMotion.CACHE_MANAGER.createCache(Kitsune::class.java.simpleName,
                CacheConfigurationBuilder
                        .newCacheConfigurationBuilder<String, Image>(String::class.java, Image::class.java, ResourcePoolsBuilder.heap(100))
                        .build())
        for (fileName in IMAGE_LIST) {
            cache!!.put(fileName, Utils.getImageFromResources(tmpClass, fileName))
        }

        mode = Mode.STAY
        updateFileName()
        timer.start()
        sleepChecker.start()
    }

    fun setVisibility(isShow: Boolean) {
        this.isShow = isShow
    }

    private fun checkHumanSleep() {
        if (callback.isCheckHumanSleep() && Math.random() >= CHECK_SUCCEED) {
            sleepChecker.stop()
            startKizuku()
        }
    }

    private fun startKizuku() {
        setNextMode()
        val list = imageList
        fileName = list[index]
        timer.restart()
    }

    fun startManzoku() {
        val list = imageList
        fileName = list[index]
        timer.delay = PAINT_INTERVAL_MANZOKU
        timer.start()
    }

    private fun updateFileName() {
        when (mode) {
            Kitsune.Mode.STAY -> {
                val list = imageList
                fileName = list[Utils.getRandInt(list.size)]
            }
            Kitsune.Mode.KIZUKU -> {
                val list = imageList
                index += 1
                if (index >= list.size) {
                    setNextMode()
                    timer.stop()
                    callback.onStartOkosu()
                } else {
                    fileName = list[index]
                }
            }
            Kitsune.Mode.MANZOKU -> {
                var list = imageList
                index += 1
                if (index >= list.size) {
                    setNextMode()
                    list = imageList
                }
                fileName = list[index]
            }
        }
    }

    private fun setNextMode() {
        when (mode) {
            Kitsune.Mode.STAY -> mode = Mode.KIZUKU
            Kitsune.Mode.KIZUKU -> mode = Mode.MANZOKU
            Kitsune.Mode.MANZOKU -> {
                sleepChecker.start()
                mode = Mode.STAY
            }
        }
        index = 0
    }

    private enum class Mode {
        STAY,
        KIZUKU,
        MANZOKU
    }

    interface Callback {

        fun isCheckHumanSleep(): Boolean

        fun onStartOkosu()
    }

    companion object {
        private const val AXIS_X = 140
        private const val AXIS_Y = 330
        private const val PAINT_INTERVAL_STAY = 750
        private const val PAINT_INTERVAL_MANZOKU = 750
        private const val CHECK_INTERVAL = 1250
        private const val CHECK_SUCCEED = 0.6
        private val STAY_LIST = arrayOf("k_stay01", "k_stay02")
        private val KIZUKU_LIST = arrayOf("kiduku01")
        private val MANZOKU_LIST = arrayOf("manzoku01", "manzoku02", "manzoku03")
        private val IMAGE_LIST = arrayOf("k_stay01", "k_stay02", "kiduku01", "manzoku01", "manzoku02", "manzoku03")
    }
}
