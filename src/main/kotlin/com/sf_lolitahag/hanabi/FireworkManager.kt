/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.hanabi

import java.awt.Graphics
import java.util.*
import javax.swing.Timer

class FireworkManager {
    private val fireworks = ArrayList<Firework>()

    init {
        repeat(HINOTAMA_MAX) {
            fireworks.add(Firework())

        }
        Timer(LAUNCH_INTERVAL) { e -> startLaunch() }.start()
    }

    private fun startLaunch() {
        fireworks.filter { it.isNotRun }
                .forEach { it.startFireball() }
    }

    fun draw(g: Graphics) {
        fireworks.forEach { firework -> firework.draw(g) }
    }

    companion object {

        private const val HINOTAMA_MAX = 6
        private const val LAUNCH_INTERVAL = 1150
    }
}
