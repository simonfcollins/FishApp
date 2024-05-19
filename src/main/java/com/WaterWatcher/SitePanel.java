package com.WaterWatcher;

import javax.swing.*;
import java.awt.*;

public class SitePanel extends JButton {
    private DataSite dataSite;
    private String name;
    private int position;
    private JLabel nameLabel, airTemp, high, low, condition, level, discharge, waterTemp;


    /**
     * Creates a new SitePanel instance.
     *
     * @param name the display name of the SitePanel.
     * @param dataSite the DataSite this SitePanel belongs to.
     */
    public SitePanel(String name, DataSite dataSite, int position) {
        this.dataSite = dataSite;
        this.name = name;
        this.position = position;
        create();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private void create() {

        setLayout(new GridBagLayout());
        //setPreferredSize(new Dimension(240, 80));
        setBorder(GUI.sageLineBorder);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(2, 8, 2, 2);

        nameLabel = new JLabel(name);
        nameLabel.setFont(StyleLibrary.locationHeading);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = .66;
        c.weighty = .4;
        c.gridwidth = 3;
        c.gridheight = 1;
//        c.anchor = GridBagConstraints.LINE_START;
        add(nameLabel, c);


        level = new JLabel(dataSite.getCurrentGH() + " ft");
        level.setFont(StyleLibrary.siteDetails);
        level.setHorizontalAlignment(SwingConstants.CENTER);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = .22;
        c.weighty = .6;
        c.gridwidth = 1;
        c.gridheight = 2;
        add(level, c);


        discharge = new JLabel(dataSite.getCurrentDischarge() + " cf/s");
        discharge.setFont(StyleLibrary.siteDetails);
        discharge.setHorizontalAlignment(SwingConstants.CENTER);

        c.gridx = 1;
        c.gridy = 1;
        c.weightx = .22;
        c.weighty = .6;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.insets = new Insets(2, 2, 2, 2);
        add(discharge, c);


        waterTemp = new JLabel(dataSite.getCurrentTemperature() + "°C");
        waterTemp.setFont(StyleLibrary.siteDetails);
        waterTemp.setHorizontalAlignment(SwingConstants.CENTER);

        c.gridx = 2;
        c.gridy = 1;
        c.weightx = .22;
        c.weighty = .6;
        c.gridwidth = 1;
        c.gridheight = 2;
        add(waterTemp, c);


        low = new JLabel("L:30°");
        low.setFont(StyleLibrary.siteDetails);
        low.setHorizontalAlignment(SwingConstants.TRAILING);

        c.gridx = 3;
        c.gridy = 1;
        c.weightx = .165;
        c.weighty = .3;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(low, c);

        high = new JLabel("H:63°");
        high.setFont(StyleLibrary.siteDetails);
        high.setHorizontalAlignment(SwingConstants.LEADING);

        c.gridx = 4;
        c.gridy = 1;
        c.weightx = .165;
        c.weighty = .3;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets = new Insets(2, 2, 2, 8);
        add(high, c);


        airTemp = new JLabel("50°F");
        airTemp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        airTemp.setHorizontalAlignment(SwingConstants.CENTER);

        c.gridx = 3;
        c.gridy = 0;
        c.weightx = .33;
        c.weighty = .4;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(airTemp, c);


        condition = new JLabel("Cloudy");
        condition.setFont(StyleLibrary.siteDetails);
        condition.setHorizontalAlignment(SwingConstants.CENTER);

        c.gridx = 3;
        c.gridy = 2;
        c.weightx = .33;
        c.weighty = .3;
        c.gridwidth = 2;
        c.gridheight = 1;
        add(condition, c);
    }


    /**
     * Updates instantaneous data displayed on this SitePanel.
     */
    public void refresh() {
        //TODO : retrieve instantaneous values and update panel
        revalidate();
    }


    public void edit(String name) {
        if (name != null) {
            this.name = name;
            this.nameLabel.setText(this.name);
            revalidate();
        }
    }


    /**
     * Retrieves the name of this SitePanel.
     *
     * @return the name of this SitePanel.
     */
    public String getName() {
        return this.name;
    }


    /**
     * Retrieves the DataSite this SitePanel belongs to.
     *
     * @return a DataSite.
     */
    public DataSite getDataSite() {
        return this.dataSite;
    }
}