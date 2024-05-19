package com.WaterWatcher;

public class DataSite {
    private String name;
    private final String number;
    private final SitePanel sitePanel;
    private boolean initialized;


    public DataSite(String siteNumber, String displayName) {
        number = siteNumber;
        this.sitePanel = new SitePanel(displayName, this, 0);
        initialize();
    }

    public String getCurrentGH() {
        return DataRetriever.retrieveData(this.number, DataRetriever.GAUGE_HEIGHT);
    }

    public String getCurrentDischarge() {
        return DataRetriever.retrieveData(this.number, DataRetriever.DISCHARGE);
    }

    public String getCurrentTemperature() {
        return DataRetriever.retrieveData(this.number, DataRetriever.TEMPERATURE);
    }

    public String getName() {
        return name;
    }


    public String getNumber() {
        return this.number;
    }


    public void initialize() {
        if (!initialized) {
            name = DataRetriever.retrieveName(number);
            initialized = true;
        }
    }


    public SitePanel getSitePanel() {
        return sitePanel;
    }
}
