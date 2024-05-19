package com.WaterWatcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;

public class TimeRangeSelector extends JPanel {

    private JRadioButton oneDay, oneWeek, oneMonth, oneYear, customRange;


    public TimeRangeSelector() {
        create();
    }


    private void create() {
        JLabel timeRangeSelectorLabel = new JLabel("Select data range:");
        oneYear = new JRadioButton("1 Year");
        oneMonth = new JRadioButton("1 Month");
        oneWeek = new JRadioButton("1 Week");
        oneDay = new JRadioButton("1 Day");
        customRange = new JRadioButton("Custom");

        ButtonGroup timeRangeButtonGroup = new ButtonGroup();
        timeRangeButtonGroup.add(oneYear);
        timeRangeButtonGroup.add(oneMonth);
        timeRangeButtonGroup.add(oneWeek);
        timeRangeButtonGroup.add(oneDay);
        timeRangeButtonGroup.add(customRange);
        oneWeek.setSelected(true);

        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.LEADING));
        add(timeRangeSelectorLabel);
        add(oneYear);
        add(oneMonth);
        add(oneWeek);
        add(oneDay);
        add(customRange);

    }

    public void addItemListener(ItemListener l) {
        oneYear.addItemListener(l);
        oneMonth.addItemListener(l);
        oneWeek.addItemListener(l);
        oneDay.addItemListener(l);
        customRange.addItemListener(l);
    }


    public JRadioButton getDayButton() { return oneDay; }

    public JRadioButton getWeekButton() { return oneWeek; }

    public JRadioButton getMonthButton() { return oneMonth; }

    public JRadioButton getYearButton() { return oneYear; }

    public JRadioButton getCustomButton() { return customRange; }
}
