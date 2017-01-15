package com.sf_lolitahag.hanabi;

import com.sf_lolitahag.Utils;
import java.awt.Color;
import java.util.ArrayList;

public class Fireball extends AbsPaintArray {

  private static final int R = 255;
  private static final int G = 200;
  private static final int B = 200;
  private static final int ALPHA_MIN = 60;
  private static final int ALPHA_MAX = 200;
  private static final int TAIL_COUNT_MIN = 10;
  private static final int TAIL_COUNT_MAX = 25;
  private static final int UPDATE_AXIS_Y = -1;
  private ArrayList<Color> colors = new ArrayList<>();

  public Fireball() {
  }

  @Override
  public void init(int x, int y) {
    super.init(x, y);
    colors.clear();

    int tail = Utils.getRandRange(TAIL_COUNT_MIN, TAIL_COUNT_MAX);
    int alpha = Utils.getRandRange(ALPHA_MIN, ALPHA_MAX);
    for (int index = 0; index < tail; index++) {
      Color color = new Color(R, G, B, alpha * (tail - index) / tail);
      paintObjects.add(new PaintObjectImpl(x, y, color));
      colors.add(color);
    }
  }

  @Override
  public void next() {
    PaintObject firstObject = paintObjects.getFirst();
    PaintObject newObject = new PaintObjectImpl(firstObject.getX(),
        firstObject.getY() + UPDATE_AXIS_Y);

    paintObjects.addFirst(newObject);
    paintObjects.removeLast();

    int size = colors.size();
    for (int index = 0; index < size; index++) {
      paintObjects.get(index).updateColor(colors.get(index));
    }
  }
}
