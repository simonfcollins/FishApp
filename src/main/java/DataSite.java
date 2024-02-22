public class DataSite {
    private String name, river, city, state, number;
    private boolean initialized;


    public DataSite(String siteNumber) {
        number = siteNumber;
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

    public String[][] getDailyGH() {
        return DataRetriever.retrieveData(number, DataRetriever.GAUGE_HEIGHT, 1);
    }

    public String[][] getDailyDischarge() {
        return DataRetriever.retrieveData(number, DataRetriever.DISCHARGE, 1);
    }

    public String[][] getDailyTemp() {
        return DataRetriever.retrieveData(number, DataRetriever.TEMPERATURE, 1);
    }

    public String[][] getWeeklyGH() {
        return DataRetriever.retrieveData(number, DataRetriever.GAUGE_HEIGHT, 7);
    }

    public String[][] getWeeklyDischarge() {
        return DataRetriever.retrieveData(number, DataRetriever.DISCHARGE, 7);
    }

    public String[][] getWeeklyTemp() {
        return DataRetriever.retrieveData(number, DataRetriever.TEMPERATURE, 7);
    }

    public String[][] getMonthlyGH() {
        return DataRetriever.retrieveData(number, DataRetriever.GAUGE_HEIGHT, 31);
    }

    public String[][] getMonthlyDischarge() {
        return DataRetriever.retrieveData(number, DataRetriever.DISCHARGE, 31);
    }

    public String[][] getMonthlyTemp() {
        return DataRetriever.retrieveData(number, DataRetriever.TEMPERATURE, 31);
    }

    public String[][] getYearlyGH() {
        return DataRetriever.retrieveData(number, DataRetriever.GAUGE_HEIGHT, 365);
    }

    public String[][] getYearlyDischarge() {
        return DataRetriever.retrieveData(number, DataRetriever.DISCHARGE, 365);
    }

    public String[][] getYearlyTemp() {
        return DataRetriever.retrieveData(number, DataRetriever.TEMPERATURE, 365);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return this.number;
    }

    public void initialize() {
        if (!initialized) {
            getWeeklyGH();
            name = DataRetriever.retrieveName("src/main/resources/" + number + DataRetriever.GAUGE_HEIGHT + "P7D.txt", number);
            initialized = true;
        }
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}
