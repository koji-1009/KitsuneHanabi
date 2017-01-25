/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.hanabi;

import com.sf_lolitahag.Utils;
import java.awt.Color;

public class Spark extends AbsPaintArray {

  private static final int ALPHA_BASE = 225;
  private static final int TAIL_COUNT_MIN = 5;
  private static final int TAIL_COUNT_MAX = 30;
  private static final int COLOR_R_BASE = 200;
  private static final int COLOR_R_COEFFICIENT = 50;
  private static final int COLOR_GB_BASE = 50;
  private static final int COLOR_GB_COEFFICIENT = 200;
  private static final int EXPLOSION_BASE = 15;
  private static final int EXPLOSION_COEFFICIENT = 20;
  private static final int ALPHA_DECREASE_MIN = 10;
  private static final int ALPHA_DECREASE_MAX = 30;
  private static final double RESIST = 0.05;
  private static final double GRAVITY = 0.7;
  private double gapX;
  private double gapY;

  public Spark() {
  }

  @Override
  public void init(int x, int y) {
    super.init(x, y);

    int r = Utils.getRandBaseCoe(COLOR_R_BASE, COLOR_R_COEFFICIENT);
    int g = Utils.getRandBaseCoe(COLOR_GB_BASE, COLOR_GB_COEFFICIENT);
    int b = Utils.getRandBaseCoe(COLOR_GB_BASE, COLOR_GB_COEFFICIENT);
    Color color = new Color(r, g, b, ALPHA_BASE);

    int tail = Utils.getRandRange(TAIL_COUNT_MIN, TAIL_COUNT_MAX);
    for (int index = 0; index < tail; index++) {
      paintObjects.add(new PaintObjectImpl(x, y, color));
    }

    double roll = Math.random() * Math.PI * 2;
    double coefficient = Utils.getRandBaseCoe(EXPLOSION_BASE, EXPLOSION_COEFFICIENT);
    gapX = coefficient * Math.cos(roll);
    gapY = coefficient * Math.sin(roll);
  }

  @Override
  public void next() {
    gapY += GRAVITY;
    double coefficient = RESIST * Math.pow(Math.pow(gapX, 2) + Math.pow(gapY, 2), 0.5);
    gapX -= coefficient * gapX;
    gapY -= coefficient * gapY;

    if (Math.floor(gapX) == 0) {
      gapX = 0;
    }
    PaintObject firstObject = paintObjects.getFirst();
    PaintObject newObject = new PaintObjectImpl(firstObject.getX() + (int) gapX,
        firstObject.getY() + (int) gapY);

    paintObjects.addFirst(newObject);
    paintObjects.removeLast();

    Color color = firstObject.getColor();
    int alpha = color.getAlpha() - Utils.getRandRange(ALPHA_DECREASE_MIN, ALPHA_DECREASE_MAX);
    if (alpha < 0) {
      alpha = 0;
    }
    Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    for (PaintObject object : paintObjects) {
      object.updateColor(newColor);
    }
  }
}
