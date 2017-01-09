package com.sf_lolitahag.hanabi;

import java.awt.*;

public interface PaintObject {

    int getX();
    int getY();
    Color getColor();
    void updateColor(Color color);
}
