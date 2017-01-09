package com.sf_lolitahag.hanabi;

import javax.swing.*;
import java.util.ArrayList;

public class FireworkManager {

    private static final int HINOTAMA_MAX = 8;
    private static final int LAUNCH_INTERVAL = 1250;
    private ArrayList<Firework> mFireworks = new ArrayList<>();

    public FireworkManager() {
        for (int i = 0; i < HINOTAMA_MAX; i++) {
            mFireworks.add(new Firework());
        }
        new Timer(LAUNCH_INTERVAL, (e) -> startLaunch()).start();
    }

    private void startLaunch() {
        mFireworks.forEach(Firework::startFireball);
    }

    public ArrayList<Firework> getFireworks() {
        return mFireworks;
    }
}
