package com.sf_lolitahag.hanabi;

import java.awt.Color;

public class PaintObjectImpl implements PaintObject {

  private static final Color TMP_COLOR = new Color(255, 255, 255);
  private int x;
  private int y;
  private Color color;

  public PaintObjectImpl(int x, int y) {
    this(x, y, TMP_COLOR);
  }

  public PaintObjectImpl(int x, int y, Color color) {
    this.x = x;
    this.y = y;
    this.color = color;
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
  public Color getColor() {
    return color;
  }

  @Override
  public void updateColor(Color color) {
    this.color = color;
  }
}
