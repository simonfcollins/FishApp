package com.WaterWatcher;

import org.jfree.chart.ChartPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

public class GUI extends JFrame {
    
    // UI Components
    private SidebarHeader sbh;
    private SitePanelScrollPane scrollPane;
    private DataSelector dataSelector;
    private TimeRangeSelector trs;
    private WeatherView weatherView;
    private TitleBar titleBar;
    private static JPopupMenu menu;
    private ChartPanel chart;

    // Data
    private final ArrayList<DataSite> siteList;
    private final HashMap<String, ChartPanel> charts;
    private static DataSite currentSite;
    private static String currentParam;
    private static int currentTimeRange;
    public static final int MAX_SITES = 2;

    // Clock
    private static final long REFRESH_INTERVAL = 15;
    private static final Clock clock = Clock.tick(Clock.systemDefaultZone(), Duration.ofMinutes(REFRESH_INTERVAL));
    private static long time;

    // Styling
    public static Color PINE, OLIVE, SAGE, GOLD, MARIGOLD, CREAM, TAN, PINK, SKY, CHALK;
    public static Border sageLineBorder;
    private ImageIcon funnyFish;


    public GUI() {
        siteList = new ArrayList<>();
        charts = new HashMap<>();
        currentTimeRange = 7;
        currentParam = DataRetriever.GAUGE_HEIGHT;
        time = clock.millis();

        PINE = new Color(50, 64, 1);
        OLIVE = new Color(125, 140, 69);
        SAGE = new Color(208, 217, 180);
        TAN = new Color(166, 109, 3);
        GOLD = new Color(217, 164, 4);
        MARIGOLD = new Color(255, 233, 110);
        CREAM = new Color(255, 245, 235);
        PINK = new Color(244, 158, 167);
        SKY = new Color(216, 235, 242);
        CHALK = new Color(240, 240, 235);

        sageLineBorder = BorderFactory.createLineBorder(OLIVE, 2);

        Image fishImage;
        try {
            fishImage = ImageIO.read(new File("src/main/resources/icons/funny_fish_chalk.jpg"));
            fishImage = fishImage.getScaledInstance(1278/15, 936/15, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        funnyFish = new ImageIcon(fishImage);

        create();
    }


    private void create() {

        setIconImage(Toolkit.getDefaultToolkit().createImage("src/main/resources/icons/funny_fish_chalk.jpg"));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1080, 720));
        setTitle("Water Watcher");
        setLayout(new GridBagLayout());
        getContentPane().setBackground(CREAM);

        Insets normalInsets = new Insets(4, 4, 4, 4);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = normalInsets;

        sbh = new SidebarHeader();
        scrollPane = new SitePanelScrollPane();
        dataSelector = new DataSelector();
        trs = new TimeRangeSelector();
        weatherView = new WeatherView();
        titleBar = new TitleBar();
        chart = Charter.nullChart();

        c.weightx = 0;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets = new Insets(4, 16, 4, 4);
        add(sbh, c);

        c.weightx = 0.15;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 7;
        c.insets = new Insets(4, 16, 16, 4);
        add(scrollPane, c);

        c.weightx = 0;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.insets = new Insets(16, 16, 4, 16);
        add(titleBar, c);

        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 4;
        c.insets = new Insets(4, 4, 4, 16);
        add(chart, c);

        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(dataSelector, c);

        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 1;
        c.gridheight = 1;
        add(trs, c);

        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 1;
        c.gridy = 7;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.insets = new Insets(4, 4, 16, 16);
        add(weatherView, c);


        //==================================================================
        // Sub-Components
        //==================================================================

        menu = new JPopupMenu();

        JMenuItem delete = new JMenuItem("Delete");
        delete.addActionListener(e -> deleteSite(((SitePanel) menu.getInvoker()).getDataSite()));
        menu.add(delete);

        JMenuItem rename = new JMenuItem("Rename");
        rename.addActionListener(e -> ((SitePanel) menu.getInvoker()).edit(PopupPanes.editSiteDialog()));
        menu.add(rename);


        //==================================================================
        // Listeners
        //==================================================================

        titleBar.getRefreshButton().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { refresh(); }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }
            @Override
            public void mouseEntered(MouseEvent e) { titleBar.invertRefresh(true); }
            @Override
            public void mouseExited(MouseEvent e) { titleBar.invertRefresh(false); }
        });


        sbh.getAddButton().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }
            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (siteList.size() < MAX_SITES) {
                    PopupPanes.addSiteDialog(GUI.this);
                } else {
                    PopupPanes.siteLimitDialog(funnyFish);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) { sbh.invertAddButton(true); }
            @Override
            public void mouseExited(MouseEvent e) { sbh.invertAddButton(false); }
        });

        
        trs.addItemListener(e -> {
            JRadioButton source = (JRadioButton) e.getSource();
            if (source == trs.getYearButton()) {
                currentTimeRange = 365;
            } else if (source == trs.getMonthButton()) {
                currentTimeRange = 30;
            } else if (source == trs.getWeekButton()) {
                currentTimeRange = 7;
            } else if (source == trs.getDayButton()) {
                currentTimeRange = 1;
            } else if (source == trs.getCustomButton()) {
                currentTimeRange = 7;
            }
            setChart(getChart(currentSite, currentParam, currentTimeRange));
        });

        
        dataSelector.addItemListener(e -> {
            JRadioButton source = (JRadioButton) e.getSource();
            if (source == dataSelector.getDischargeButton()) {
                currentParam = DataRetriever.DISCHARGE;
            } else if (source == dataSelector.getGaugeHeightButton()) {
                currentParam = DataRetriever.GAUGE_HEIGHT;
            } else if (source == dataSelector.getWaterTempButton()) {
                currentParam = DataRetriever.TEMPERATURE;
            }
            setChart(getChart(currentSite, currentParam, currentTimeRange));
        });

        //==================================================================


        pack();
        setVisible(true);
    }


    /**
     * A method to get a chart from the local "database".
     *
     * @param dataSite the data site of the chart
     * @param paramCD the data type of the chart
     * @param timeRange the time range of the chart
     * @return the chart specified by the parameters
     */
    private ChartPanel getChart(DataSite dataSite, String paramCD, int timeRange) {

        if (dataSite == null) return Charter.nullChart();

        ChartPanel chart = charts.get(dataSite.getNumber() + paramCD + timeRange);

        if (chart == null) {
            chart = Charter.generateChart(dataSite, paramCD, timeRange);
            charts.put(dataSite.getNumber() + paramCD + timeRange, chart);
        }

        return chart;
    }


    /**
     * Set the currently visible chart.
     *
     * @param chartPanel the chart to display
     */
    private void setChart(ChartPanel chartPanel) {

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 4;
        c.insets = new Insets(4, 4, 4, 16);
        remove(chart);

        chart = chartPanel == null ? Charter.nullChart() : chartPanel;

        add(chart, c);
        revalidate();
        repaint();
    }


    /**
     * <p>
     *     Refresh the local database.
     * </p>
     * <p>
     *     USGS updates their public databases once every 15 minutes.
     *     Thus, to avoid redundant API calls, Water Watcher will only
     *     allow a refresh when new data is available.
     * </p>
     *
     * @return true if the database is refreshed; false otherwise
     */
    private boolean refresh() {
        if (clock.millis() != time) {
            time = clock.millis();

            charts.clear();
            setChart(getChart(currentSite, currentParam, currentTimeRange));

            for (DataSite site : siteList) site.getSitePanel().refresh();

            System.out.println("Data refreshed @" + Instant.now());
            return true;
        }
        return false;
    }


    /**
     * Deletes the specified DataSite from the system.
     *
     * @param site the site to delete
     */
    private void deleteSite(DataSite site) {
        if (PopupPanes.deleteSiteDialog(site.getName())) {

            // change the chart view if the display site is the one being deleted
            if (currentSite == site) {

                if (siteList.size() == 1) {
                    currentSite = null;
                } else {
                    currentSite = siteList.getFirst();
                }
                setChart(getChart(currentSite, currentParam, currentTimeRange));
            }

            // remove the DataSite and its SitePanel from the system
            siteList.remove(site);
            scrollPane.removePanel(site.getSitePanel());
        }
    }


    /**
     * <p>
     *     Puts a new DataSite into the system.
     * </p>
     * <p>
     *     This method is not safe and assumes a valid USGS data site number for {@param siteNumber}.
     * </p>
     *
     * @param siteName a name to be displayed in the sidebar
     * @param siteNumber the number of the new data site
     */
    public void addSite(String siteName, String siteNumber) {
        DataSite newSite = new DataSite(siteNumber, siteName);

        newSite.getSitePanel().setComponentPopupMenu(menu);

        newSite.getSitePanel().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                currentSite = newSite;
                setChart(getChart(currentSite, currentParam, currentTimeRange));
            }}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        // add SitePanel to the sidebar
        currentSite = newSite;
        siteList.add(newSite);
        scrollPane.addPanel(newSite.getSitePanel());
        setChart(getChart(currentSite, currentParam, currentTimeRange));
    }
}
