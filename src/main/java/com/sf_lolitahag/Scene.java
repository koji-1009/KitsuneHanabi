package com.sf_lolitahag;

import com.sf_lolitahag.hanabi.Hanabi;

public class Scene {
    private static final int hanabiNum = 5;
    private Hanabi hanabi[] = new Hanabi[hanabiNum];

    public Scene() {
        for (int i = 0; i < hanabiNum; i++) {
            hanabi[i] = new Hanabi((int) (400 + 200 * Math.random()), 400);
        }
    }

    public void idle() {
        for (int i = 0; i < hanabiNum; i++) {
//            if (hanabi[i].exist == 1) {
//                hanabi[i].idle();
//            } else if (hanabi[i].exist == 0) {
//                if (Math.random() * 100 < 0.5) {
//                    hanabi[i].init((int) (350 + 200 * Math.random()), 500);
//                }
//            }
        }
    }
}
