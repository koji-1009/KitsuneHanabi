package com.sf_lolitahag;

import com.sf_lolitahag.panel.AbstractPanel;
import com.sf_lolitahag.panel.GamePanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {

    private static final int FRAME_WIDTH = 1000;
    private static final int FRAME_HEIGHT = 800;
    private static final String FRAME_TITLE = "日本の夏、ロリババアの夏";
    private static AbstractPanel mPanel;

    public static void main(String args[]) {
        SwingUtilities.invokeLater(Main::initFrame);
    }

    private static void initFrame() {
        JFrame frame = new JFrame(FRAME_TITLE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null); //初期画面表示 位置を中央に
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //CLOSEでプログラム終了
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // nop
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    mPanel.onSpaceKeyPress();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // nop
            }
        });

        mPanel = new GamePanel();
        frame.add(mPanel);
        frame.setVisible(true);
    }
}
