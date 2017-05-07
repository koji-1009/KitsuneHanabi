/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.hanabi;

import java.awt.Color;

public interface PaintObject {

  int getX();

  int getY();

  Color getColor();

  void updateColor(final Color color);
}
