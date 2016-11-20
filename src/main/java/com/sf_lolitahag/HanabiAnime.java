package com.sf_lolitahag;

import com.sf_lolitahag.GameComponent.Animation;
import com.sf_lolitahag.GameComponent.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

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
