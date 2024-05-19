package com.WaterWatcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;

public class DataSelector extends JPanel {
    private JRadioButton gaugeHeight, discharge, waterTemp;

    public DataSelector() {
        create();
    }

    private void create() {
        JLabel dataSelectorLabel = new JLabel("Select data to plot:");
        gaugeHeight = new JRadioButton("Gauge Height (ft)");
        discharge = new JRadioButton("Discharge (cf/s)");
        waterTemp = new JRadioButton("Temperature (F)");

        ButtonGroup dataButtonGroup = new ButtonGroup();
        dataButtonGroup.add(gaugeHeight);
        dataButtonGroup.add(discharge);
        dataButtonGroup.add(waterTemp);
        gaugeHeight.setSelected(true);

        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.LEADING));
        add(dataSelectorLabel);
        add(gaugeHeight);
        add(discharge);
        add(waterTemp);
    }

    public void addItemListener(ItemListener l) {
        gaugeHeight.addItemListener(l);
        discharge.addItemListener(l);
        waterTemp.addItemListener(l);
    }

    public JRadioButton getGaugeHeightButton() { return gaugeHeight; }

    public JRadioButton getDischargeButton() { return discharge; }

    public JRadioButton getWaterTempButton() { return waterTemp; }
}
