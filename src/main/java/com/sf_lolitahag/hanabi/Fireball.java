/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.hanabi;

import com.sf_lolitahag.Utils;
import java.awt.Graphics;

public class Fireball extends AbsPaintArray {

  private static final int R = 255;
  private static final int G = 200;
  private static final int B = 200;
  private static final int ALPHA_MIN = 60;
  private static final int ALPHA_MAX = 200;
  private static final int TAIL_COUNT_MIN = 10;
  private static final int TAIL_COUNT_MAX = 25;
  private static final int UPDATE_AXIS_Y = -1;

  public Fireball() {
  }

  @Override
  public void init(int x, int y) {
    super.init(x, y);

    int tail = Utils.getRandRange(TAIL_COUNT_MIN, TAIL_COUNT_MAX);
    int alpha = Utils.getRandRange(ALPHA_MIN, ALPHA_MAX);
    initList(x, y, R, G, B, alpha, tail, 0, UPDATE_AXIS_Y);
  }

  @Override
  public void next() {
    addTop();
  }

  @Override
  protected void drawObject(final Graphics g, final PaintObject fireball) {
    g.setColor(fireball.getColor());
    g.fillOval(fireball.getX(), fireball.getY(), 4, 4);
  }
}
