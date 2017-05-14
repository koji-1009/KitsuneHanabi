/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.hanabi;

import java.awt.Graphics;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

public abstract class AbsPaintArray {

  private final List<PaintObject> paintList = new CopyOnWriteArrayList<>();

  public void init(int x, int y) {
    paintList.clear();
  }

  public int getTopX() {
    return paintList.get(0).getX();
  }

  public int getTopY() {
    return paintList.get(0).getY();
  }

  public int getTopAlpha() {
    return paintList.get(0).getColor().getAlpha();
  }

  public void draw(final Graphics g) {
    synchronized (paintList) {
      paintList.forEach(paintObject -> drawObject(g, paintObject));
    }
  }

  protected void initList(int x, int y, int r, int g, int b, int alpha, int tail) {
    initList(x, y, r, g, b, alpha, tail, 0, 0);
  }

  protected void initList(int x, int y, int r, int g, int b, int alpha, int tail, int moveX,
      int moveY) {
    IntStream.range(0, tail).forEach(index ->
        paintList.add(new PaintObjectImpl(x, y, r, g, b, alpha, tail, index, moveX, moveY)));
  }

  protected void addTop() {
    synchronized (paintList) {
      PaintObject newObject = paintList.get(0).clone();
      newObject.movePositionNext();

      paintList.parallelStream().forEach(PaintObject::moveIndexNext);
      paintList.add(0, newObject);
      paintList.remove(paintList.size() - 1);
    }
  }

  protected void addTop(int newMoveX, int newMoveY) {
    synchronized (paintList) {
      PaintObject newObject = paintList.get(0).clone();
      newObject.setMoveX(newMoveX);
      newObject.setMoveY(newMoveY);
      newObject.movePositionNext();

      paintList.parallelStream().forEach(PaintObject::moveIndexNext);
      paintList.add(0, newObject);
      paintList.remove(paintList.size() - 1);
    }
  }

  protected void updateAlpha(int alpha) {
    synchronized (paintList) {
      paintList.parallelStream().forEach(object -> object.setAlpha(alpha));
    }
  }

  public abstract void next();

  protected abstract void drawObject(Graphics g, PaintObject object);
}
