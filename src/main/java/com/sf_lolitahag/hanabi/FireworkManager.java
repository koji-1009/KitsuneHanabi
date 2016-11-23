package com.sf_lolitahag.hanabi;

import javax.swing.*;
import java.util.ArrayList;

public class FireworkManager {

    private static final int HINOTAMA_MAX = 6;
    private ArrayList<Firework> mFireworks = new ArrayList<>();
    private Timer mTimer = new Timer(500, (e) -> startLaunch());

    public FireworkManager() {
        for (int i = 0; i < HINOTAMA_MAX; i++) {
            mFireworks.add(new Firework());
        }
        mTimer.start();
    }

    private void startLaunch() {
        mFireworks.forEach(Firework::startFireball);
    }

    public ArrayList<Firework> getFireworks() {
        return mFireworks;
    }
}
