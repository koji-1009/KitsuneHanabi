package com.sf_lolitahag.GameComponent;

import javax.swing.*;

public interface GameObject {
    int INT_VAL1 = 10;

    void init(int x, int y, JPanel p);

    void idle();

    void exit();
}
