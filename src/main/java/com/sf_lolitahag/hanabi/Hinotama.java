package com.sf_lolitahag.hanabi;

import java.awt.*;

class Hinotama {
    double x[], y[];
    int r, g, b;
    int tail;
    double v;
    double vx, vy;
    int count = 0;
    int exist = 0;
    int life;

    Color color[];

    Hinotama(int x, int y) {
        tail = 20;
        life = (int) (50 + 100 * Math.random());

        r = 255;
        g = 200;
        b = 200;

        this.x = new double[tail];
        this.y = new double[tail];
        color = new Color[tail];

        for (int i = 0; i < tail; i++) {
            color[i] = new Color(r, g, b, (int) (100 * i / tail));
            //color[i] = new Color(100,100,100);
            this.x[i] = (double) x;
            this.y[i] = (double) y;
        }

        vx = 0;
        vy = -3;

    }

    public void init(int x, int y) {
        for (int i = 0; i < tail; i++) {
            color[i] = new Color(r, g, b, (int) (100 * i / tail));
            this.x[i] = (double) x;
            this.y[i] = (double) y;
        }
        life = (int) (80 + 50 * Math.random());
        r = 255;
        g = 200;
        b = 200;
        tail = 20;
        count = 0;
        exist = 1;
    }

    public void idle() {
        count++;

        for (int i = 1; i < tail; i++) {
            x[i - 1] = x[i];
            y[i - 1] = y[i];
        }

        vx += -0.5 * vx + (Math.random() - Math.random() + Math.random() - Math.random() + Math.random() - Math.random() + Math.random() - Math.random() + Math.random() - Math.random()) / 10;
        x[tail - 1] += vx;
        y[tail - 1] += vy;

    }

    public void paint(Graphics g) {
        if (exist == 1) {
            for (int i = 0; i < tail; i++) {
                g.setColor(color[i]);
                g.fillOval((int) x[i], (int) y[i], 5, 5);
            }
        }
    }

    public void exit() {
        exist = 0;
    }
}
