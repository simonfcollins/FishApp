import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends JFrame {
    private ArrayList<DataSite> siteList;
    private final ChartView chartView;
    private final WeatherView weatherView;
    private final SidebarView sidebarView;

    public GUI() {
        siteList = new ArrayList<>();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1080, 720));
        setTitle("Let's Go Fishing");
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        chartView = new ChartView();
        weatherView = new WeatherView();
        sidebarView = new SidebarView(this);

        c.weightx = 0.2;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.insets = new Insets(8, 4, 8, 4);

        add(sidebarView, c);

        c.weightx = 0.8;
        c.weighty = 0.6;
        c.gridx = 1;
        c.gridheight = 1;

        add(chartView, c);

        c.weighty = 0.4;
        c.gridy = 1;

        add(weatherView, c);

        pack();
        setVisible(true);
    }

    public ArrayList<DataSite> getSiteList() {
        return siteList;
    }

    public ChartView getChartView() {
        return chartView;
    }

    public WeatherView getWeatherView() {
        return weatherView;
    }

    public SidebarView getSidebarView() {
        return sidebarView;
    }
}
