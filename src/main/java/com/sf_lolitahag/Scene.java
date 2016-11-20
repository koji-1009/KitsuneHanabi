package com.sf_lolitahag;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;

class Scene {
    Image back;
    Image room;
    Image sky;
    JPanel p;
    Hanabi hanabi[];
    int hanabiNum;
    HanabiAnime ha;

    Scene(JPanel p) {
        this.p = p;

        try {
            URL url = getClass().getResource("/" + "Animation/back.png");
            back = ImageIO.read(url);
            url = getClass().getResource("/" + "Animation/room.png");
            room = ImageIO.read(url);
            url = getClass().getResource("/" + "Animation/sky.png");
            sky = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ha = new HanabiAnime();
        ha.init(140, 330, p);

        hanabiNum = 5;
        hanabi = new Hanabi[hanabiNum];
        for (int i = 0; i < hanabiNum; i++) {
            hanabi[i] = new Hanabi((int) (400 + 200 * Math.random()), 400, p);
        }
    }

    public void idle() {
        ha.idle();
        for (int i = 0; i < hanabiNum; i++) {
            if (hanabi[i].exist == 1) {
                hanabi[i].idle();
            } else if (hanabi[i].exist == 0) {
                if (Math.random() * 100 < 0.5) {
                    hanabi[i].init((int) (350 + 200 * Math.random()), 500);
                }
            }
        }
    }

    public void paint(Graphics g) {
        g.drawImage(sky, 0, 0, p);
        for (int i = 0; i < hanabiNum; i++) {
            hanabi[i].paint(g);
        }
        g.drawImage(back, 0, 0, p);
        g.drawImage(room, 0, 0, p);
        ha.paint(g);
    }

    public void KeyPressed(KeyEvent e) {
        ha.KeyPressed(e);
    }
}
