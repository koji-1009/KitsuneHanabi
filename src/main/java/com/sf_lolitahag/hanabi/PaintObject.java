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

  void setMoveX(int positionX);

  void setMoveY(int positionY);

  void movePositionNext();

  void moveIndexNext();

  void setAlpha(int alpha);

  Color getColor();

  PaintObject clone();
}
