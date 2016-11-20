package com.sf_lolitahag;

import javax.swing.*;
import java.awt.*;

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

