package io.github.simcards.desktop;

import java.awt.EventQueue;
import javax.swing.JFrame;


public class SimCardsDesktop extends JFrame {
    public SimCardsDesktop() {

        initUI();
    }

    private void initUI() {

        setTitle("Simple example");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                SimCardsDesktop ex = new SimCardsDesktop();
                ex.setVisible(true);
            }
        });
    }
}
