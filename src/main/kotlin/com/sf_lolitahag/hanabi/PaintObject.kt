/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.hanabi

import java.awt.Color

interface PaintObject {

    val x: Int

    val y: Int

    val color: Color

    fun setMoveX(positionX: Int)

    fun setMoveY(positionY: Int)

    fun movePositionNext()

    fun moveIndexNext()

    fun setAlpha(alpha: Int)

    fun clone(): PaintObject
}
