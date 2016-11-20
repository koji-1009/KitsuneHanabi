package com.sf_lolitahag;

import javax.swing.*;
import java.awt.*;

class Hanabi {
    int x, y;
    JPanel p;
    Hibana hibana[];
    int n_hibana = 100;
    Hinotama hinotama;
    double v;
    int exist = 0;
    int life;
    int count;

    Hanabi(int x, int y, JPanel p) {
        hibana = new Hibana[n_hibana];
        this.p = p;
        this.x = x;
        this.y = y;

        life = 300;
        hinotama = new Hinotama(x, y, p);
        for (int i = 0; i < n_hibana; i++) {
            hibana[i] = new Hibana(x, y, p);
        }
    }

    public void init(int x, int y) {
        this.x = x;
        this.y = y;
        hinotama = new Hinotama(x, y, p);
        hinotama.init(x, y);
        count = 0;
        exist = 1;

    }

    public void idle() {
        if (exist == 1) {
            count++;
            if (count == life) {
                exit();
            } else {
                hinotama.idle();
                if (hinotama.count == hinotama.life) {
                    hinotama.exit();
                    v = (1 + 10 * Math.random());
                    for (int i = 0; i < n_hibana; i++) {
                        hibana[i].init((int) hinotama.x[hinotama.tail - 1], (int) hinotama.y[hinotama.tail - 1], v);
                    }
                }
                for (int i = 0; i < n_hibana; i++) {
                    if (hibana[i].exist == 1) {
                        hibana[i].idle();
                    }
                }
            }
        }
    }

    public void paint(Graphics g) {
        if (exist == 1) {
            hinotama.paint(g);
            for (int i = 0; i < n_hibana; i++) {
                if (hibana[i].exist == 1) {
                    hibana[i].paint(g);
                }
            }
        }
    }

    public void exit() {
        hinotama.exit();
        for (int i = 0; i < n_hibana; i++) {
            hibana[i].exit();
        }
        exist = 0;

    }
}
