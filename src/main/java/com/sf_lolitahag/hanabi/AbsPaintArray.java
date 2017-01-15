package com.sf_lolitahag.hanabi;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class AbsPaintArray {

  protected LinkedList<PaintObject> paintObjects = new LinkedList<>();

  public void init(int x, int y) {
    paintObjects.clear();
  }

  public int getTopX() {
    return paintObjects.getFirst().getX();
  }

  public int getTopY() {
    return paintObjects.getFirst().getY();
  }

  public ArrayList<PaintObject> getArray() {
    ArrayList<PaintObject> ret = new ArrayList<>();
    for (PaintObject object : paintObjects) {
      ret.add(object);
    }

    return ret;
  }

  public abstract void next();
}
