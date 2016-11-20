package com.sf_lolitahag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class MainPanel extends JPanel implements Runnable, KeyListener, MouseListener {
    GameScene gamescene;
    int x = 100, y = 100;
    boolean state = true;
    Thread t;
    long stepTime, startTime, endTime;
    Scene scene;

    MainPanel() {
        //setLayout(null);
        stepTime = 33;
        setBackground(Color.blue);//背景色を青に
        t = new Thread(this);//Thread instance
        scene = new Scene(this);
        gamescene = gamescene.TITLE;
        t.start();//Thread Start
    }

    public void run() {
        while (true) {
            startTime = System.currentTimeMillis();

            switch (gamescene) {
                case TITLE:
                    scene.idle();
                    break;
                case GAME:
                    break;
                default:
                    break;
            }
            repaint();
            endTime = System.currentTimeMillis();

            if (stepTime > (endTime - startTime)) {
                try {
                    t.sleep(stepTime - (endTime - startTime));
                } catch (InterruptedException e) {

                }
            }
        }
    }

    //JComponentによるpaintComponent method
    //JPanel は JComponent を継承している
    public void paintComponent(Graphics g) {
        switch (gamescene) {
            case TITLE:
                scene.paint(g);
                break;
            case GAME:
                break;
            default:
                break;
        }

    }

    public void keyTyped(KeyEvent e) {
    }

    /**********************
     KeyEvent
     **********************/
    //Keyが押された場合
    public void keyPressed(KeyEvent e) {
        switch (gamescene) {
            case TITLE:
                scene.KeyPressed(e);
                break;
            case GAME:
                break;
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
