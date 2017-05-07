/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.hanabi;

import java.awt.Graphics;
import java.util.LinkedList;

public abstract class AbsPaintArray {

  protected final LinkedList<PaintObject> paintObjects = new LinkedList<>();

  public void init(int x, int y) {
    paintObjects.clear();
  }

  public int getTopX() {
    return paintObjects.getFirst().getX();
  }

  public int getTopY() {
    return paintObjects.getFirst().getY();
  }

  public abstract void next();

  public abstract void draw(final Graphics g);
}
