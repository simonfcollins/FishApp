package com.WaterWatcher;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public abstract class Charter {

    private static ChartPanel NULL_CHART;


    /**
     * <p>
     *      Creates a chart from the given data.
     * </p>
     * <p>
     *      Given a DataSite, a USGS data parameter, and time period in days, returns a JFreeChart.ChartPanel
     *      component.
     * </p>
     *
     * @param dataSite the DataSite to chart data for.
     * @param paramCd specifies the type of data to chart.
     * @param days the interval of time to chart.
     * @return a ChartPanel charting the specified parameters.
     */
    public static ChartPanel generateChart(DataSite dataSite , String paramCd, int days) {

        if (dataSite == null) throw new IllegalArgumentException("dataSite cannot be null.");
        if (paramCd == null) throw new IllegalArgumentException("paramCd cannot be null.");
        if (days < 1 || days > 365) throw new IllegalArgumentException("days must be between 1 and 365.");

        String[][] data = DataRetriever.retrieveData(dataSite.getNumber(), paramCd, days);

        if (data == null) return NULL_CHART;

        String yLabel;
        String unit;

        switch (paramCd) {
            case DataRetriever.GAUGE_HEIGHT -> {
                yLabel = "Gauge Height";
                unit = "ft";
            }
            case DataRetriever.TEMPERATURE -> {
                yLabel = "Temperature";
                unit = "°C";
            }
            case DataRetriever.DISCHARGE -> {
                yLabel = "Discharge";
                unit = "cf/s";
            }
            default -> {
                yLabel = "";
                unit = "";
            }
        }

        DefaultCategoryDataset dataset = createDataset(data, unit);
        JFreeChart lineChart = ChartFactory.createLineChart(
                dataSite.getName(), "Time", yLabel, dataset, PlotOrientation.VERTICAL, false, false, false
        );
        lineChart.setBackgroundPaint(null);
        lineChart.getPlot().setBackgroundPaint(GUI.CHALK);

        ChartPanel cp = new ChartPanel(lineChart);

        cp.setMouseWheelEnabled(true);
        cp.setMouseWheelEnabled(true);
        cp.setOpaque(false);

        // removes unwanted functionality from the chart front-end
        Component[] components = cp.getPopupMenu().getComponents();
        JPopupMenu newMenu = new JPopupMenu();
        newMenu.add(components[2]);
        newMenu.add(components[3]);
        newMenu.addSeparator();
        newMenu.add(components[5]);
        newMenu.addSeparator();
        newMenu.add(components[7]);
        newMenu.add(components[8]);

        cp.setPopupMenu(newMenu);

        return cp;
    }


    /**
     * A method to generate a blank ChartPanel with no data.
     *
     * @return a blank ChartPanel
     */
    public static ChartPanel nullChart() {

        if (NULL_CHART == null) {

            DefaultCategoryDataset dataset = createDataset(new String[0][2], "");
            JFreeChart lineChart = ChartFactory.createLineChart(
                    "", "", "", dataset, PlotOrientation.VERTICAL, false, false, false
            );
            lineChart.setBackgroundPaint(null);
            lineChart.getPlot().setBackgroundPaint(GUI.CHALK);

            ChartPanel cp = new ChartPanel(lineChart);
            cp.setDomainZoomable(false);
            cp.setRangeZoomable(false);
            cp.setMouseWheelEnabled(false);
            cp.removeMouseListener(cp.getMouseListeners()[0]);
            cp.removeMouseListener(cp.getMouseListeners()[0]);
            cp.setOpaque(false);

            NULL_CHART = cp;
        }

        return NULL_CHART;
    }


    /**
     * Creates a DefaultCategoryDataset instance from raw data.
     * <p>
     *     Raw data must be formatted as a 2D String array where each sub-array is of length 2 and
     *     contains a time, value pair.
     * </p>
     * <p>
     *     The order of the dataset will be in the same order as {@param rawData}.
     * </p>
     *
     * @param rawData a 2D array of data points.
     * @param unit the unit of the dependent variable.
     * @return a DefaultCategoryDataset instance
     */
    private static DefaultCategoryDataset createDataset(String[][] rawData, String unit) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String[] rawDatum : rawData) {
            dataset.addValue(Double.parseDouble(rawDatum[1]), unit, rawDatum[0]);
        }
        return dataset;
    }
}
