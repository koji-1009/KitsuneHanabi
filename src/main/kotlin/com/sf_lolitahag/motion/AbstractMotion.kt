/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.motion

import org.ehcache.Cache
import org.ehcache.CacheManager
import org.ehcache.config.builders.CacheManagerBuilder
import java.awt.Image

/**
 * パネル上で表示するオブジェクトのBaseクラス
 * 必要に応じてオブジェクトのResources名とX軸、Y軸、影の有無をOverrideする
 */
abstract class AbstractMotion {
    var axisX = 0
        protected set
    var axisY = 0
        protected set
    var isShow = true
        protected set
    protected var fileName = EMPTY
    protected var cache: Cache<String, Image>? = null

    val bodyImage: Image?
        get() = if (fileName == EMPTY) {
            null
        } else cache!!.get(fileName)

    companion object {

        @JvmStatic
        protected val CACHE_MANAGER: CacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true)
        private const val EMPTY = ""
    }
}
