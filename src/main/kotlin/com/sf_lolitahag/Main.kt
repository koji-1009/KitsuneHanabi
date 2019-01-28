/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag

import com.sf_lolitahag.panel.GamePanel
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

object Main {

    private const val FRAME_WIDTH = 1000
    private const val FRAME_HEIGHT = 800
    private const val FRAME_TITLE = "日本の夏、ロリババアの夏"

    @JvmStatic
    fun main(args: Array<String>) {
        SwingUtilities.invokeLater { initFrame() }
    }

    private fun initFrame() {
        JFrame(FRAME_TITLE).apply {
            setSize(FRAME_WIDTH, FRAME_HEIGHT)
            setLocationRelativeTo(null)
            defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

            val panel = GamePanel()
            addKeyListener(object : KeyListener {
                override fun keyTyped(e: KeyEvent) {
                    // nop
                }

                override fun keyPressed(e: KeyEvent) {
                    if (e.keyCode == KeyEvent.VK_SPACE) {
                        panel.onSpaceKeyPress()
                    }
                }

                override fun keyReleased(e: KeyEvent) {
                    // nop
                }
            })
            add(panel)

            isVisible = true
        }
    }
}

