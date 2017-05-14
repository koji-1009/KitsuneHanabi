/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.hanabi;

import java.awt.Color;

public class PaintObjectImpl implements PaintObject, Cloneable {

  private int x;
  private int y;
  private int r;
  private int g;
  private int b;
  private int tail;
  private int index;
  private int alpha;
  private int moveAxisX;
  private int moveAxisY;

  public PaintObjectImpl(int x, int y, int r, int g, int b, int alpha, int tail, int index,
      int moveAxisX, int moveAxisY) {
    this.x = x;
    this.y = y;
    this.r = r;
    this.g = g;
    this.b = b;
    this.tail = tail;
    this.index = index;
    this.alpha = alpha;
    this.moveAxisX = moveAxisX;
    this.moveAxisY = moveAxisY;
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public int getY() {
    return y;
  }

  @Override
  public void setMoveX(int moveX) {
    moveAxisX = moveX;
  }

  @Override
  public void setMoveY(int moveY) {
    moveAxisY = moveY;
  }

  @Override
  public void movePositionNext() {
    x = x + moveAxisX;
    y = y + moveAxisY;
  }

  @Override
  public void moveIndexNext() {
    index += 1;
  }

  @Override
  public void setAlpha(int alpha) {
    this.alpha = alpha;
  }

  @Override
  public Color getColor() {
    return new Color(r, g, b, alpha * (tail - index) / tail);
  }

  @Override
  public PaintObject clone() {
    PaintObject o = null;
    try {
      o = (PaintObject) super.clone();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return o;
  }
}
