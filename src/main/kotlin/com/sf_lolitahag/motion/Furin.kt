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

class Furin : AbstractMotion() {
    private val timer = Timer(PAINT_INTERVAL) { e -> updateFileName() }

    init {
        axisX = AXIS_X
        axisY = AXIS_Y

        val tmpClass = javaClass
        cache = AbstractMotion.CACHE_MANAGER.createCache(Furin::class.java.simpleName,
                CacheConfigurationBuilder
                        .newCacheConfigurationBuilder<String, Image>(String::class.java, Image::class.java, ResourcePoolsBuilder.heap(100))
                        .build())
        for (fileName in IMAGE_LIST) {
            cache!!.put(fileName, Utils.getImageFromResources(tmpClass, fileName))
        }

        updateFileName()
        timer.start()
    }

    private fun updateFileName() {
        fileName = IMAGE_LIST[Utils.getRandInt(IMAGE_LIST.size)]
        timer.delay = Utils.getRandRange(PAINT_INTERVAL, PAINT_INTERVAL_MAX)
    }

    companion object {
        private const val AXIS_X = 200
        private const val AXIS_Y = 155
        private const val PAINT_INTERVAL = 500
        private const val PAINT_INTERVAL_MAX = 1500
        private val IMAGE_LIST = arrayOf("furin01", "furin02", "furin03", "furin03")
    }
}
