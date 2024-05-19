package com.WaterWatcher;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GUI gui = new GUI();
                gui.addSite("Gardiner", "06192500");
                gui.addSite("Logan", "06052500");
        });
    }
}
