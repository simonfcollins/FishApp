import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SitePanel extends JButton {
    private DataSite dataSite;
    private String name;
    private JPanel namePanel;
    private JPanel waterDataPanel;
    private JPanel weatherPanel;

    public SitePanel (String name, String siteNumber) {
        this.dataSite = new DataSite(siteNumber);
        this.name = name;
        createPanel();
    }


    public SitePanel(String name, DataSite dataSite) {
        this.dataSite = dataSite;
        this.name = name;
        createPanel();
    }


    private void createPanel() {
        this.setLayout(new GridBagLayout());
        //setBorder(BorderFactory.createLineBorder(Color.GRAY));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(2, 2, 2, 2);

        namePanel = new JPanel();
        namePanel.setLayout(new GridBagLayout());
        namePanel.setOpaque(false);
        JLabel nameLabel = new JLabel(this.name);
        nameLabel.setFont(FontClass.locationHeading);
        nameLabel.setHorizontalAlignment(SwingConstants.LEADING);
        namePanel.add(nameLabel, constraints);

        weatherPanel = new JPanel();
        weatherPanel.setLayout(new GridBagLayout());
        weatherPanel.setOpaque(false);
        JLabel airTemperature = new JLabel("50\u00b0F");
        JLabel highLow = new JLabel("L:30\u00b0H:63\u00b0");
        JLabel condition = new JLabel("Partly Sunny");
        airTemperature.setHorizontalAlignment(SwingConstants.CENTER);
        highLow.setHorizontalAlignment(SwingConstants.CENTER);
        condition.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 0.5;
        weatherPanel.add(airTemperature, constraints);
        constraints.gridy = 1;
        constraints.weighty = 0.25;
        weatherPanel.add(highLow, constraints);
        constraints.gridy = 2;
        weatherPanel.add(condition, constraints);

        waterDataPanel = new JPanel();
        waterDataPanel.setLayout(new GridBagLayout());
        waterDataPanel.setOpaque(false);
        JLabel gaugeHeight = new JLabel(dataSite.getCurrentGH() + " ft");
        JLabel discharge = new JLabel(dataSite.getCurrentDischarge() + " cf/s");
        JLabel waterTemperature = new JLabel(dataSite.getCurrentTemperature() + "\u00b0C");
        gaugeHeight.setFont(FontClass.siteDetails);
        discharge.setFont(FontClass.siteDetails);
        waterTemperature.setFont(FontClass.siteDetails);
        constraints.gridy = 0;
        constraints.weighty = 1;
        constraints.weightx = 0.33;
        waterDataPanel.add(gaugeHeight, constraints);
        constraints.gridx++;
        waterDataPanel.add(discharge, constraints);
        constraints.gridx++;
        waterDataPanel.add(waterTemperature, constraints);

        constraints.insets = new Insets(2, 6, 2, 6);
        constraints.weightx = 0.66;
        constraints.weighty = 0.5;
        constraints.gridx = 0;
        this.add(namePanel, constraints);
        constraints.gridy = 1;
        this.add(waterDataPanel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        constraints.weighty = 1;
        constraints.weightx = 0.33;
        this.add(weatherPanel, constraints);
    }


    public boolean update() {
        //TODO : retrieve instantaneous values and update panel
        revalidate();
        return false;
    }

    public String getName() {
        return this.name;
    }

    public DataSite getDataSite() {
        return this.dataSite;
    }

}