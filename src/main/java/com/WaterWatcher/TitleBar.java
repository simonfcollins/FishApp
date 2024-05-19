package com.WaterWatcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TitleBar extends JPanel {
    private JLabel title, separator, logo;
    private ImageIcon refreshIcon, refreshIconInverted;
    private JButton refreshButton;
    private Font titleFont;

    public TitleBar() {

        try {
            titleFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Salsa-Regular.ttf")).deriveFont(32f);
        } catch (IOException | FontFormatException e) {
            titleFont = StyleLibrary.headings;
        }

        create();
    }

    private void create() {
        setLayout(new GridBagLayout());
        setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        Image fishImage, refreshImage, refreshImageInverted;
        try {

            fishImage = ImageIO.read(new File("src/main/resources/icons/rainbow_trout_corrected.jpg"));
            fishImage = fishImage.getScaledInstance(380/5, 316/5, Image.SCALE_SMOOTH);
            refreshImage = ImageIO.read(new File("src/main/resources/icons/refresh_sage.jpg"));
            refreshImage = refreshImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            refreshImageInverted = ImageIO.read(new File("src/main/resources/icons/refresh_sage_inverted.jpg"));
            refreshImageInverted = refreshImageInverted.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        logo = new JLabel(new ImageIcon(fishImage));
        logo.setOpaque(false);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 1;
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_START;
        add(logo, c);

        title = new JLabel("Water Watcher");
        title.setFont(titleFont);
        title.setForeground(GUI.PINE);
        title.setHorizontalAlignment(SwingConstants.LEADING);

        c.gridx = 1;
        c.weightx = 1;
        add(title, c);

        refreshIcon = new ImageIcon(refreshImage);
        refreshIconInverted = new ImageIcon(refreshImageInverted);

        refreshButton = new JButton();
        refreshButton.setIcon(refreshIcon);
        refreshButton.setBorder(BorderFactory.createLineBorder(GUI.SAGE, 1, true));
        refreshButton.setPreferredSize(new Dimension(30, 30));

        c.gridx = 2;
        c.weightx = .1;
        c.fill = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.LINE_END;
        add(refreshButton, c);

        separator = new JLabel();
        separator.setBorder(BorderFactory.createLineBorder(GUI.OLIVE, 2, true));

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        add(separator, c);
    }


    /**
     * Returns the refreshButton.
     *
     * @return the refreshButton.
     */
    public JButton getRefreshButton() {
        return refreshButton;
    }


    /**
     * Inverts the colors of the refreshButton.
     *
     * <p>
     *     If {@param b} is true, then the colors are inverted.
     * </p>
     * <p>
     *     If {@param b} is false, then the colors are set to normal.
     * </p>
     *
     * @param b a boolean value
     */
    public void invertRefresh(boolean b) {
        refreshButton.setIcon(b ? refreshIconInverted : refreshIcon);
    }
}
