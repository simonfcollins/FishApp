package com.WaterWatcher;

import javax.swing.*;
import java.awt.*;

public class WeatherView extends JPanel{
    
    public WeatherView() {
        setLayout(new GridBagLayout());
        setBackground(GUI.SKY);
        JLabel temp = new JLabel("Weather data coming soon!");
        temp.setForeground(GUI.OLIVE);
        add(temp);
    }
}
