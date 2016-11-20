package com.sf_lolitahag;

import javax.swing.*;
import java.awt.*;

class Hibana {
    double x[], y[];
    int r, g, b;
    int tail;
    double v;
    double vx, vy;
    double roll;
    double yaw;
    double resist;
    double gravity;
    double fade;
    double count = 0;
    double fadePeriod = 200;
    int exist = 0;
    Color color[];
    JPanel p;

    Hibana(int x, int y, JPanel p) {
        tail = 30;
        resist = 0.05;
        gravity = 0.01;

        r = (int) (100 + 150 * Math.random());
        g = (int) (100 + 150 * Math.random());
        b = (int) (100 + 150 * Math.random());

        this.x = new double[tail];
        this.y = new double[tail];
        color = new Color[tail];

        this.p = p;
        for (int i = 0; i < tail; i++) {
            color[i] = new Color(r, g, b, (int) (255 * i / tail));
            this.x[i] = (double) x;
            this.y[i] = (double) y;
        }
    }

    public void init(int x, int y, double v) {

        for (int i = 0; i < tail; i++) {
            color[i] = new Color(r, g, b, (int) (255 * i / tail));
            this.x[i] = (double) x;
            this.y[i] = (double) y;
        }
        count = 0;
        fade = 1;
        r = 255;
        g = (int) (100 + 150 * Math.random());
        b = (int) (100 + 150 * Math.random());
        roll = Math.random() * Math.PI * 2;
        yaw = Math.random() * Math.PI * 2;
        this.v = v * Math.cos(yaw);
        vx = this.v * Math.cos(roll);
        vy = this.v * Math.sin(roll);
        exist = 1;
    }

    public void idle() {
        double coef;

        count++;

        if (fadePeriod > count) {
            fade = (fadePeriod - count) / fadePeriod;
        }
        for (int i = 0; i < tail; i++) {
            color[i] = new Color(r, g, b, (int) (200 * fade * i / tail));

        }

        for (int i = 1; i < tail; i++) {
            x[i - 1] = x[i];
            y[i - 1] = y[i];
        }

        vy += gravity;
        coef = resist * Math.pow(Math.pow(vx, 2) + Math.pow(vy, 2), 0.5);
        vx -= coef * vx;
        vy -= coef * vy;
        x[tail - 1] += vx;
        y[tail - 1] += vy;
    }

    public void paint(Graphics g) {

        for (int i = 0; i < tail; i++) {
            g.setColor(color[i]);
            g.fillOval((int) x[i], (int) y[i], 2, 2);
        }

    }

    public void exit() {
        exist = 0;
    }
}
