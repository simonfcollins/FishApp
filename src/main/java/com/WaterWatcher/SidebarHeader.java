package com.WaterWatcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SidebarHeader extends JPanel{
    private JButton addButton;
    private JLabel text;
    private ImageIcon addIcon, addIconInverted;


    public SidebarHeader() {
        create();
    }


    private void create() {
        GridBagConstraints c = new GridBagConstraints();
        setLayout(new GridBagLayout());
        setOpaque(false);

        Image addImage, addImageInverted;
        try {
            addImage = ImageIO.read(new File("src/main/resources/icons/add_sage.png"));
            addImage = addImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
            addImageInverted = ImageIO.read(new File("src/main/resources/icons/add_sage_inverted.png"));
            addImageInverted = addImageInverted.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        addIcon = new ImageIcon(addImage);
        addIconInverted = new ImageIcon(addImageInverted);

        addButton = new JButton(addIcon);
        addButton.setBorder(BorderFactory.createLineBorder(GUI.SAGE, 1, true));
        addButton.setPreferredSize(new Dimension(20, 20));

        text = new JLabel("USGS Water Data Sites");
        text.setHorizontalAlignment(SwingConstants.LEADING);
        text.setFont(new Font("SansSerif", Font.BOLD, 14));
        text.setForeground(GUI.PINE);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 1;
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_START;
        add(text, c);


        c.gridx = 2;
        c.weightx = .1;
        c.fill = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.LINE_END;
        add(addButton, c);
    }


    /**
     * Returns the addButton component.
     *
     * @return the addButton.
     */
    public JButton getAddButton() {
        return addButton;
    }


    /**
     * Inverts the colors of the addButton.
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
    public void invertAddButton(boolean b) {
        addButton.setIcon(b ? addIconInverted : addIcon);
    }
}
