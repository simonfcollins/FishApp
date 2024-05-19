package com.WaterWatcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public abstract class PopupPanes extends JOptionPane {


    /**
     * Shows the "add site" dialog.
     *
     * @param gui
     */
    public static void addSiteDialog(GUI gui) {
        AddSiteMessage messagePanel = new AddSiteMessage();
        ImageIcon addLocationIcon = new ImageIcon("src/main/resources/icons/add_location.png");


        while (true) {
            int result = showConfirmDialog(null, messagePanel, "", OK_CANCEL_OPTION, PLAIN_MESSAGE, addLocationIcon);

            if (result == OK_OPTION) {

                String siteNumber = messagePanel.siteNumberField.getText();

                if (!messagePanel.siteNameField.getText().isEmpty()) {

                    if (8 <= siteNumber.length() && siteNumber.length() <= 15 && siteNumber.matches("[0-9]+")) {

                        try {

                            URLConnection url = new URL(
                                    "https://waterservices.usgs.gov/nwis/iv?sites="
                                            + siteNumber
                                            + "&parameterCd=00065&period=P1D&format=rdb"
                            ).openConnection();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(url.getInputStream()));
                            String line = reader.readLine();
                            reader.close();

                            if (line.equals("#  No sites found matching all criteria")) {
                                messagePanel.errorMessage.setText("No site found matching this number.");
                            } else {
                                gui.addSite(messagePanel.siteNameField.getText(), siteNumber);
                                break;
                            }
                        } catch (IOException exception) {
                            messagePanel.errorMessage.setText("Something went wrong. Please try again.");
                        }
                    } else {
                        messagePanel.errorMessage.setText("Site number must be between 8 and 15 digits.");
                    }
                } else {
                    messagePanel.errorMessage.setText("Site name field cannot be blank.");
                }
            } else /* If the user presses cancel or esc */ {
                break;
            }
        }
    }


    /**
     * Shows the "delete site" confirmation dialog.
     *
     * @param sitePanelName the name of the com.WaterWatcher.SitePanel being deleted.
     */
    public static boolean deleteSiteDialog(String sitePanelName) {
        int result = showConfirmDialog(
                null,
                "Are you want to delete " + sitePanelName + "?",
                "",
                YES_NO_OPTION,
                QUESTION_MESSAGE,
                new ImageIcon("src/main/resources/icons/trashcan.png"));
        return result == YES_OPTION;
    }


    public static String editSiteDialog() {
        return showInputDialog(null, "Name: ", null, JOptionPane.PLAIN_MESSAGE);
    }

    public static void siteLimitDialog(ImageIcon icon) {
        JLabel message = new JLabel("<html>You have too many saved locations!<br>Please remove a location before adding more</html>");
        message.setHorizontalAlignment(SwingConstants.CENTER);
        showMessageDialog(null, message, null, JOptionPane.WARNING_MESSAGE, icon);
    }


    /**
     * A helper class to construct the message for the "add site" dialog.
     */
    private static class AddSiteMessage extends JPanel {
        private final JTextField siteNameField;
        private final JTextField siteNumberField;
        private final JLabel errorMessage;

        private AddSiteMessage() {
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            JLabel siteNameLabel = new JLabel("Site name:");
            c.gridx = 0;
            c.gridy = 0;
            c.weightx = 0.5;
            c.weighty = 0;
            c.insets = new Insets(12, 8, 12, 8);
            add(siteNameLabel, c);

            siteNameField = new JTextField();
            siteNameField.setPreferredSize(new Dimension(100, 18));
            c.gridx = 1;
            add(siteNameField, c);

            JLabel siteNumberLabel = new JLabel("Site number:");
            c.gridx = 0;
            c.gridy = 1;
            add(siteNumberLabel, c);

            siteNumberField = new JTextField();
            siteNumberField.setPreferredSize(new Dimension(100, 18));
            c.gridx = 1;
            add(siteNumberField, c);

            errorMessage = new JLabel(" ");
            errorMessage.setForeground(Color.RED);
            errorMessage.setFont(StyleLibrary.body);
            errorMessage.setMinimumSize(new Dimension(300, 18));
            c.gridx = 0;
            c.gridy = 2;
            c.weightx = 1;
            c.gridwidth = 2;
            add(errorMessage, c);
        }
    }
}