import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class SidebarView extends JPanel {
    private final ArrayList<SitePanel> sitePanelList;
    private final JPanel sitePanelView;
    private final GUI gui;

    public SidebarView(GUI gui) {
        this.gui = gui;
        sitePanelList = new ArrayList<>();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createRaisedSoftBevelBorder());

        JLabel locationHeader = new JLabel();
        locationHeader.setText("USGS Water Data Sites");
        locationHeader.setHorizontalAlignment(SwingConstants.CENTER);
        locationHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0;
        add(locationHeader, c);

        sitePanelView = new JPanel();
        sitePanelView.setLayout(new GridBagLayout());
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        c.weighty = 1;
        c.gridy = 100;
        sitePanelView.add(spacer, c);

        JScrollPane locationScrollPane = new JScrollPane();
        locationScrollPane.setViewportView(sitePanelView);
        locationScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        locationScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        c.gridy = 1;
        c.weightx = 1;
        add(locationScrollPane, c);

        JPanel editBar = new JPanel();
        editBar.setBackground(Color.lightGray);
        editBar.setLayout(new GridBagLayout());
        c.gridy = 2;
        c.weighty = 0.05;
        add(editBar, c);

        JButton add = new JButton("Add");
        c.gridy = 0;
        c.weighty = 1;
        editBar.add(add, c);

        add.addActionListener(e -> {
            AddSiteDialog dialog = new AddSiteDialog();
            dialog.setLocation((this.getWidth() * 5 - dialog.getWidth()) / 2, (this.getHeight() - dialog.getHeight()) / 2);
        });

        JButton delete = new JButton("Delete");
        c.gridx = 1;
        editBar.add(delete, c);
    }

    public void addSite(String siteName, String siteNumber) {
        DataSite newSite = new DataSite(siteNumber);
        SitePanel sitePanel = new SitePanel(siteName, newSite);

        sitePanel.addActionListener(e -> {
            gui.getChartView().setSite(newSite);
        });

        gui.getSiteList().add(newSite);
        sitePanelList.add(sitePanel);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.weightx = 1;
        c.weighty = 0;
        c.gridy = sitePanelList.size();
        sitePanelView.add(sitePanel, c);
        sitePanelView.revalidate();
    }


    private class AddSiteDialog extends JDialog {

        private AddSiteDialog() {

            setTitle("Add new USGS Site");
            setSize(360, 240);
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            setResizable(false);
            setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;

            JLabel siteNameLabel = new JLabel("Site name:");
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.weightx = 0.5;
            constraints.weighty = 0;
            constraints.insets = new Insets(12, 8, 12, 8);
            add(siteNameLabel, constraints);

            JTextField siteNameField = new JTextField();
            siteNameField.setPreferredSize(new Dimension(100, 18));
            constraints.gridx = 1;
            add(siteNameField, constraints);

            JLabel siteNumberLabel = new JLabel("Site number:");
            constraints.gridx = 0;
            constraints.gridy = 1;
            add(siteNumberLabel, constraints);

            JTextField siteNumberField = new JTextField();
            siteNumberField.setPreferredSize(new Dimension(100, 18));
            constraints.gridx = 1;
            add(siteNumberField, constraints);

            JLabel errorMessage = new JLabel(" ");
            errorMessage.setForeground(Color.RED);
            errorMessage.setFont(FontClass.body);
            errorMessage.setMinimumSize(new Dimension(300, 18));
            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.weightx = 1;
            constraints.gridwidth = 2;
            add(errorMessage, constraints);

            Dimension buttonSize = new Dimension(150, 35);

            JButton enterButton = new JButton("Accept");
            enterButton.setPreferredSize(buttonSize);
            enterButton.setFont(FontClass.body);
            constraints.gridx = 1;
            constraints.gridy = 3;
            constraints.weightx = 0.5;
            constraints.gridwidth = 1;
            add(enterButton, constraints);

            JButton cancelButton = new JButton("Cancel");
            cancelButton.setPreferredSize(buttonSize);
            cancelButton.setFont(FontClass.body);
            constraints.gridx = 0;
            add(cancelButton, constraints);

            enterButton.addActionListener(e -> {
                String siteNumber = siteNumberField.getText();
                if (!siteNameField.getText().isEmpty()) {
                    if (8 <= siteNumber.length() && siteNumber.length() <= 15 && siteNumber.matches("[0-9]+")) {
                        try {
                            URLConnection url = new URL("https://waterservices.usgs.gov/nwis/iv?sites=" + siteNumber + "&parameterCd=00065&period=P1D&format=rdb").openConnection();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(url.getInputStream()));
                            String line = reader.readLine();
                            reader.close();
                            if (line.equals("#  No sites found matching all criteria")) {
                                errorMessage.setText("No site found matching this number.");
                            } else {
                                addSite(siteNameField.getText(), siteNumber);
                                this.dispose();
                            }
                        } catch (IOException exception) {
                            errorMessage.setText("Something went wrong. Please try again.");
                        }
                    } else {
                        errorMessage.setText("Site number must be between 8 and 15 digits.");
                    }
                } else {
                    errorMessage.setText("Site name field cannot be blank.");
                }
            });

            cancelButton.addActionListener(e -> this.dispose());
            setVisible(true);
        }
    }
}
