package com.sf_lolitahag.hanabi;

import java.awt.Color;

public interface PaintObject {

  int getX();

  int getY();

  Color getColor();

  void updateColor(Color color);
}
