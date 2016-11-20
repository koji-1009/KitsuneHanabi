package com.sf_lolitahag;

import com.sf_lolitahag.GameComponent.Animation;
import com.sf_lolitahag.GameComponent.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

enum State {
    STAY,
    MOUNERU
}

enum GameScene {
    TITLE,
    GAME;
}

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
    JPanel p;

    Hinotama(int x, int y, JPanel p) {
        tail = 20;
        life = (int) (50 + 100 * Math.random());

        r = 255;
        g = 200;
        b = 200;

        this.x = new double[tail];
        this.y = new double[tail];
        color = new Color[tail];

        this.p = p;
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

class HanabiAnime implements GameObject {

    State state;
    JPanel p;
    Animation o_stay;
    Animation k_stay;
    Animation neru;
    Animation kiduku;
    Animation okosu;
    Animation okiru;
    Animation manzoku;
    Animation furin;

    int x, y;

    //mainTitle = new Animation("Animation/title.txt",p);

    HanabiAnime() {

    }


    @Override
    public void init(int x, int y, JPanel p) {
        this.p = p;
        this.x = x;
        this.y = y;

        state = State.STAY;

        o_stay = new Animation("Animation/o_stay.txt", p);
        k_stay = new Animation("Animation/k_stay.txt", p);
        neru = new Animation("Animation/neru.txt", p);
        kiduku = new Animation("Animation/kiduku.txt", p);
        okosu = new Animation("Animation/okosu.txt", p);
        okiru = new Animation("Animation/okiru.txt", p);
        manzoku = new Animation("Animation/manzoku.txt", p);
        furin = new Animation("Animation/furin.txt", p);

        o_stay.init(x, y);
        k_stay.init(x, y);
        furin.init(200, 155);
    }


    @Override
    public void idle() {
        switch (state) {
            case STAY:
                o_stay.idle();
                k_stay.idle();
                furin.idle();

                break;
            case MOUNERU:
                if (neru.CheckExist() == 1 && neru.CheckEnd() == 1 && kiduku.CheckExist() == 0) {
                    k_stay.exit();
                    kiduku.init(x, y);
                }

                if (kiduku.CheckExist() == 1 && kiduku.CheckEnd() == 1) {
                    neru.exit();
                    kiduku.exit();
                    okosu.init(x, y);
                }

                if (okosu.CheckExist() == 1 && okosu.CheckEnd() == 1) {
                    okosu.exit();
                    okiru.init(x, y);
                    manzoku.init(x + 100, y);
                }

                if (okiru.CheckExist() == 1 && okiru.CheckEnd() == 1) {
                    if (manzoku.CheckExist() == 1 && manzoku.CheckEnd() == 1) {
                        okiru.exit();
                        manzoku.exit();
                        o_stay.init(x, y);
                        k_stay.init(x, y);
                        state = State.STAY;
                    }
                }

                furin.idle();
                o_stay.idle();
                k_stay.idle();
                neru.idle();
                kiduku.idle();
                okosu.idle();
                okiru.idle();
                manzoku.idle();

                break;

            default:
                break;
        }
    }

    @Override
    public void exit() {

    }


    public void paint(Graphics g) {
        switch (state) {
            case STAY:

                furin.paint(g);
                o_stay.paint(g);
                k_stay.paint(g);

                break;
            case MOUNERU:

                furin.paint(g);
                o_stay.paint(g);
                k_stay.paint(g);
                neru.paint(g);
                kiduku.paint(g);
                okosu.paint(g);
                okiru.paint(g);
                manzoku.paint(g);

                break;
            default:
                break;
        }
    }

    public void KeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                switch (state) {
                    case STAY:
                        state = State.MOUNERU;
                        o_stay.exit();
                        neru.init(x, y);

                        break;
                    case MOUNERU:
                        break;
                }
        }
    }
}

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

        back = Toolkit.getDefaultToolkit().getImage("Animation/back.png");
        room = Toolkit.getDefaultToolkit().getImage("Animation/room.png");
        sky = Toolkit.getDefaultToolkit().getImage("Animation/sky.png");

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

public class KitsuneHanabi extends JFrame {
    KitsuneHanabi(String title) {
        setTitle(title);
        setSize(1000, 850);
        setLocationRelativeTo(null);//初期画面表示位置を中央に
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//CLOSEでプログラム終了

        Container CP = getContentPane();//getContentPane()はJFrameクラスに定義されている
        //CP.setLayout(null);//レイアウトマネージャを停止

        JPanel panel = new JPanel();

        //Mainパネルの作成、フレームへのセット
        MainPanel MP = new MainPanel();
        CP.add(MP);
        //CP.remove(MP);//フレームを外す
        addKeyListener(MP);//KeyListenerをフレームにセット
        //CP.removeKeyListener(MP);//KeyListenerを外す
    }

    public static void main(String args[]) {
        KitsuneHanabi frame = new KitsuneHanabi("日本の夏、ロリババアの夏");
        frame.setVisible(true);
    }
}

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