/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.panel

import com.sf_lolitahag.Utils
import com.sf_lolitahag.hanabi.FireworkManager
import com.sf_lolitahag.motion.Furin
import com.sf_lolitahag.motion.Human
import com.sf_lolitahag.motion.Kitsune
import java.awt.Graphics
import javax.swing.JPanel
import javax.swing.Timer

class GamePanel : JPanel() {

    private val back by lazy { Utils.getImageFromResources(javaClass, BACK) }
    private val room by lazy { Utils.getImageFromResources(javaClass, ROOM) }
    private val sky by lazy { Utils.getImageFromResources(javaClass, SKY) }
    private val fireworkManager = FireworkManager()

    private val motions by lazy {
        listOf(Furin(), human, kitsune)
    }
    private val human by lazy {
        Human(object : Human.Callback {
            override fun onStartOkosuMotion() {
                kitsune.setVisibility(false)
            }

            override fun onFinishOkosuMotion() {
                kitsune.startManzoku()
                kitsune.setVisibility(true)
            }
        })
    }
    private val kitsune: Kitsune

    init {
        startRepaintTimer()

        kitsune = Kitsune(object : Kitsune.Callback {
            override fun isCheckHumanSleep(): Boolean {
                return human.isSleepNow
            }

            override fun onStartOkosu() {
                human.startOkosu()
            }
        })
    }

    fun onSpaceKeyPress() {
        human.onSpaceKeyPress()
    }

    override fun paintComponent(g: Graphics) {
        g.drawImage(sky, 0, 0, null)
        g.drawImage(back, 0, 0, null)
        g.drawImage(room, 0, 0, null)

        motions.filter {
            it.isShow
        }.forEach {
            g.drawImage(it.bodyImage, it.axisX, it.axisY, null)
        }

        fireworkManager.draw(g)
    }

    private fun startRepaintTimer() {
        Timer(PAINT_INTERVAL) { e -> repaint() }.start()
    }

    companion object {
        private const val PAINT_INTERVAL = 33
        private const val BACK = "back"
        private const val ROOM = "room"
        private const val SKY = "sky"
    }
}
