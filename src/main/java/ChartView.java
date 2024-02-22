import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.RendererState;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.IOException;

public class ChartView extends JPanel{
    private final JPanel chart;
    private DataSite currentSite;
    private int currentTimeRange;
    private String currentParam;
    private final JRadioButton oneWeek, gaugeHeight;

    public ChartView() {
        currentTimeRange = 7;
        currentParam = DataRetriever.GAUGE_HEIGHT;
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        setLayout(new GridBagLayout());
        setBackground(Color.white);

        chart = new JPanel();
        chart.setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        add(chart, c);

        JLabel dataSelectorLabel = new JLabel("Select data to plot:");
        gaugeHeight = new JRadioButton("Gauge Height (ft)");
        JRadioButton discharge = new JRadioButton("Discharge (cf/s)");
        JRadioButton waterTemp = new JRadioButton("Temperature (F)");

        ButtonGroup dataButtonGroup = new ButtonGroup();
        dataButtonGroup.add(gaugeHeight);
        dataButtonGroup.add(discharge);
        dataButtonGroup.add(waterTemp);
        gaugeHeight.setSelected(true);

        JPanel dataSelector = new JPanel();
        dataSelector.setLayout(new FlowLayout(FlowLayout.LEADING));
        dataSelector.add(dataSelectorLabel);
        dataSelector.add(gaugeHeight);
        dataSelector.add(discharge);
        dataSelector.add(waterTemp);
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 0;
        add(dataSelector, c);

        JLabel timeRangeSelectorLabel = new JLabel("Select data range:");
        JRadioButton oneYear = new JRadioButton("1 Year");
        JRadioButton oneMonth = new JRadioButton("1 Month");
        oneWeek = new JRadioButton("1 Week");
        JRadioButton oneDay = new JRadioButton("1 Day");
        JRadioButton customRange = new JRadioButton("Custom");

        ButtonGroup timeRangeButtonGroup = new ButtonGroup();
        timeRangeButtonGroup.add(oneYear);
        timeRangeButtonGroup.add(oneMonth);
        timeRangeButtonGroup.add(oneWeek);
        timeRangeButtonGroup.add(oneDay);
        timeRangeButtonGroup.add(customRange);
        oneWeek.setSelected(true);

        JPanel timeRangeSelector = new JPanel();
        timeRangeSelector.setLayout(new FlowLayout(FlowLayout.LEADING));
        timeRangeSelector.add(timeRangeSelectorLabel);
        timeRangeSelector.add(oneYear);
        timeRangeSelector.add(oneMonth);
        timeRangeSelector.add(oneWeek);
        timeRangeSelector.add(oneDay);
        timeRangeSelector.add(customRange);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 0;
        add(timeRangeSelector, c);

        gaugeHeight.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                currentParam = DataRetriever.GAUGE_HEIGHT;
                updateChart();
            }
        });

        discharge.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                currentParam = DataRetriever.DISCHARGE;
                updateChart();
            }
        });

        waterTemp.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                currentParam = DataRetriever.TEMPERATURE;
                updateChart();
            }
        });

        oneDay.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                currentTimeRange = 1;
                updateChart();
            }
        });

        oneWeek.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                currentTimeRange = 7;
                updateChart();
            }
        });

        oneMonth.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                currentTimeRange = 31;
                updateChart();
            }
        });

        oneYear.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                currentTimeRange = 365;
                updateChart();
            }
        });
    }


    public void setSite(DataSite dataSite) {
        currentSite = dataSite;
        updateChart();
        /*oneWeek.setSelected(true);
        gaugeHeight.setSelected(true);*/
    }

    public void updateChart() {
        setChart(generateChart());
    }

    public void setChart(ChartPanel chartPanel) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;

        chart.removeAll();
        chart.add(chartPanel, c);
        chart.revalidate();
        chart.repaint();
    }

    public ChartPanel generateChart() {
        return generateChart(currentSite, currentParam, currentTimeRange);
    }

    public ChartPanel generateChart(DataSite dataSite, String paramCd, int days) {

        if (dataSite == null) {
            //TODO : return blank chart panel
        }

        String[][] data;

        try {
            data = DataRetriever.formatData("src/main/resources/" + dataSite.getNumber() + paramCd + "P" + days + "D.txt");
        } catch (IOException e) {
            data = DataRetriever.retrieveData(dataSite.getNumber(), paramCd, days);
        }

        if (data == null) {
            //TODO : return blank chart panel
        }

        String yLabel;
        String unit;

        if (paramCd.equals(DataRetriever.GAUGE_HEIGHT)) {
            yLabel = "Gauge Height";
            unit = "ft";
        } else if (paramCd.equals(DataRetriever.TEMPERATURE)) {
            yLabel = "Temperature";
            unit = "\u00b0C";
        } else if (paramCd.equals(DataRetriever.DISCHARGE)) {
            yLabel = "Discharge";
            unit = "cf/s";
        } else {
            yLabel = "";
            unit = "";
        }

        DefaultCategoryDataset dataset = DataRetriever.createDataset(data, unit);
        JFreeChart lineChart = ChartFactory.createLineChart(
                dataSite.getName(), "Time", yLabel, dataset, PlotOrientation.VERTICAL, false, false, false
        );
        ChartPanel cp = new ChartPanel(lineChart);
        cp.setDomainZoomable(true);
        cp.setRangeZoomable(false);

        return cp;
    }
}
