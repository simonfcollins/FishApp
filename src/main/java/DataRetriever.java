import org.jfree.data.category.DefaultCategoryDataset;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DataRetriever {
    public static final String GAUGE_HEIGHT = "00065";
    public static final String DISCHARGE = "00060";
    public static final String TEMPERATURE = "00010";


    public static String[][] retrieveData(String siteNo, String paramCd, int days) {
        try {

            URL url = new URL("https://waterservices.usgs.gov/nwis/iv?sites=" + siteNo + "&parameterCd=" + paramCd + "&period=P" + days + "D&format=rdb");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/" + siteNo + paramCd + "P" + days + "D.txt"));

            String line;
            while ((line = reader.readLine()) != null)
                writer.write(line + '\n');

            reader.close();
            writer.close();

            return formatData("src/main/resources/" + siteNo + paramCd + "P" + days + "D.txt");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String retrieveData(String siteNo, String paramCd) {
        try {

            URL url = new URL("https://waterservices.usgs.gov/nwis/iv?sites=" + siteNo + "&parameterCd=" + paramCd + "&format=rdb");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line = reader.readLine();
            if (line.startsWith("#  No sites found matching all criteria")) {
                return "__";
            }

            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                line = nextLine;
            }

            reader.close();

            //System.out.println(line.split("\t")[0] + " -----------------");

            /*for (String s : line.split("\t")) {
                System.out.println("---" + s + "---");
            }*/

            if (line.startsWith("USGS")) {
                String returnValue = line.split("\t")[4];
                if (returnValue.equals("Ssn")) return "__";
                else return line.split("\t")[4];
            } else {
                throw new RuntimeException("IDFK what happened");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String[][] formatData(String filePath) throws IOException {

            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            ArrayList<String[]> out = new ArrayList<>();
            String line = reader.readLine();
            if (line.startsWith("#  No sites found matching all criteria")) {
                return null;
            }
            String[] splitLine;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("USGS")) {
                    splitLine = line.split("\t");
                    // TODO : handle equipment errors
                    out.add(new String[]{splitLine[2], (splitLine[4].equals("Eqp") || splitLine[4].equals("Ice")) ? out.getLast()[1] : splitLine[4]});
                }
            }

            reader.close();
            return out.toArray(new String[][]{});
    }

    //TODO: move this shit somewhere else
    public static DefaultCategoryDataset createDataset(String[][] rawData, String unit) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String[] rawDatum : rawData) {
            dataset.addValue(Double.parseDouble(rawDatum[1]), unit, rawDatum[0]);
        }
        return dataset;
    }


    public static String retrieveName(String filePath, String siteNumber) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains(siteNumber)) {
                    return line.substring(line.indexOf(siteNumber) + siteNumber.length() + 1);
                }
            }

            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
