package com.sf_lolitahag.hanabi;

import java.awt.*;

public class Hanabi {
    Hibana hibana[];
    int n_hibana = 100;
    Hinotama hinotama;
    double v;
    int exist = 0;
    int life;
    int count;

    public Hanabi(int x, int y) {
        hibana = new Hibana[n_hibana];

        life = 300;
        hinotama = new Hinotama(x, y);
        for (int i = 0; i < n_hibana; i++) {
            hibana[i] = new Hibana(x, y);
        }
    }

    public void init(int x, int y) {
        hinotama = new Hinotama(x, y);
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
