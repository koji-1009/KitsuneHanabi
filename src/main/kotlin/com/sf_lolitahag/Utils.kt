/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag

import java.awt.image.BufferedImage
import java.io.IOException
import javax.imageio.ImageIO

object Utils {

    fun getImageFromResources(Class: Class<*>, fileName: String?): BufferedImage? {
        if (fileName == null || fileName == "") {
            return null
        }

        var image: BufferedImage? = null
        try {
            image = ImageIO.read(Class.getResource("/$fileName.png"))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (image == null) {
            System.err.println("$fileName is not found. return null.")
        }
        return image
    }

    fun getRandInt(max: Int): Int {
        if (max == 0) {
            throw IllegalArgumentException("This method doesn\'t support 0.")
        }
        var ret = (Math.random() * max).toInt()
        if (ret == max) {
            ret = getRandInt(max)
        }
        return ret
    }

    fun getRandRange(start: Int, end: Int): Int {
        if (start > end) {
            System.err.println("startFireball = [$start], end = [$end]")
            throw IllegalArgumentException("Please end value is more large as startFireball value.")
        }
        var ret = getRandInt(end + 1)
        if (ret < start) {
            ret = getRandRange(start, end)
        }
        return ret
    }

    fun getRandBaseCoe(base: Int, coefficient: Int): Int {
        return base + getRandInt(coefficient + 1)
    }
}
