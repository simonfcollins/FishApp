package com.WaterWatcher;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

@SuppressWarnings("deprecation")
public abstract class DataRetriever {

    // USGS water data parameters
    public static final String GAUGE_HEIGHT = "00065";
    public static final String DISCHARGE = "00060";
    public static final String TEMPERATURE = "00010";


    /**
     * A method to retrieve water data regarding a specific monitoring site from the USGSs' public database.
     * <p>
     *     Sends a https request to waterservices.usgs.gov to retrieve a stream of water data for the given site number, data parameter, and time period.
     * </p>
     *
     * @param siteNo the site number of the monitoring site
     * @param paramCd the water data parameter (must be a pre-defined water data parameter constant)
     * @param days the period of time to pull data from, in days
     * @return a 2D array of times and values
     */
    public static String[][] retrieveData(String siteNo, String paramCd, int days) {
        try {

            URL url = new URL("https://waterservices.usgs.gov/nwis/iv?sites=" + siteNo + "&parameterCd=" + paramCd + "&period=P" + days + "D&format=rdb");

            ArrayList<String[]> out = new ArrayList<>();

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line = reader.readLine();

            if (line.startsWith("#  No sites found matching all criteria")) return null;

            String[] splitLine;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("USGS")) {
                    splitLine = line.split("\t");


                    //==================================================================
                    // Equipment Error Handling
                    //==================================================================

                    // TODO : handle equipment errors

                    if (splitLine[4].equals("Eqp")) {

                        if (out.isEmpty()) {
                            continue;
                        } else {
                            out.add(new String[]{splitLine[2], out.getLast()[1]});
                        }

                    } else if (splitLine[4].equals("Ice")) {

                        if (out.isEmpty()) {
                            continue;
                        } else {
                            out.add(new String[]{splitLine[2], out.getLast()[1]});
                        }

                    } else if (splitLine[4].equals("Ssn")) {

                        if (out.isEmpty()) {
                            continue;
                        } else {
                            out.add(new String[]{splitLine[2], out.getLast()[1]});
                        }

                    } else {
                        out.add(new String[]{splitLine[2], splitLine[4]});
                    }
                }
            }

            reader.close();

            return out.toArray(new String[][]{});

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Retrieves instantaneous values for a given data site.
     * <p>
     *     Sends a https request to waterservices.usgs.gov to retrieve instantaneous water data for the given data site.
     * </p>
     *
     * @param siteNo the site number
     * @param paramCd the data type to retrieve
     * @return the value of the data type at the given data site
     */
    public static String retrieveData(String siteNo, String paramCd) {
        try {

            URL url = new URL("https://waterservices.usgs.gov/nwis/iv?sites=" + siteNo + "&parameterCd=" + paramCd + "&format=rdb");

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line = reader.readLine();

            if (line.startsWith("#  No sites found matching all criteria")) return null;

            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                line = nextLine;
            }

            reader.close();

            if (line.startsWith("USGS")) {
                return line.split("\t")[4];
            } else {
                throw new RuntimeException("Problem retrieving instantaneous values.");
            }

        } catch (IOException e) {
            throw new RuntimeException("Problem retrieving instantaneous values.", e);
        }
    }


    /**
     * Retrieves the assigned name of a USGS data site associated with the given site number.
     * <p>
     *     Sends a https request to waterservices.usgs.gov to retrieve the name associated with the given site number.
     * </p>
     *
     * @param siteNumber the data site number
     * @return the name of the data site
     */
    public static String retrieveName(String siteNumber) {
        try {

            URL url = new URL("https://waterservices.usgs.gov/nwis/iv?sites=" + siteNumber + "&format=rdb");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line = reader.readLine();

            if (line.equals("#  No sites found matching all criteria")) return null;

            while ((line = reader.readLine()) != null) {
                if (line.contains(siteNumber)) {
                    reader.close();
                    return line.substring(line.indexOf(siteNumber) + siteNumber.length() + 1);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Problem retrieving site name.", e);
        }
        return null;
    }
}
