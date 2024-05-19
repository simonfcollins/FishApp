package com.WaterWatcher;

import javax.swing.*;
import java.awt.*;

public class SitePanelScrollPane extends JScrollPane {
    private final JPanel sitePanelView;

    public SitePanelScrollPane() {
        sitePanelView = new JPanel();
        create();
    }


    private void create() {

        setBorder(GUI.sageLineBorder);
        setEnabled(true);
        setViewportView(sitePanelView);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        GridBagConstraints c = new GridBagConstraints();

        sitePanelView.setLayout(new GridBagLayout());
        sitePanelView.setBackground(GUI.CHALK);

        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        c.weighty = 1;
        c.gridy = 100;
        sitePanelView.add(spacer, c);
    }


    /**
     * <p>
     *     Add a SitePanel to the scroll pane.
     * </p>
     *
     * @param sitePanel the SitePanel to add
     */
    public void addPanel(SitePanel sitePanel) {
        GridBagConstraints c = new GridBagConstraints();
        sitePanel.setPosition(sitePanelView.getComponentCount() - 1);
        c.gridy = sitePanel.getPosition();
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.weightx = 1;
        c.weighty = 0;

        sitePanelView.add(sitePanel, c);
        revalidate();
        repaint();
    }


    /**
     * <p>
     *     Remove a SitePanel from the scroll pane.
     * </p>
     *
     * @param sitePanel the SitePanel to remove
     */
    public void removePanel(SitePanel sitePanel) {
        sitePanelView.remove(sitePanel);

        for (Component component : sitePanelView.getComponents()) {
            if (component.getClass() == SitePanel.class && ((SitePanel) component).getPosition() > sitePanel.getPosition()) {
                sitePanelView.remove(component);
                ((SitePanel) component).setPosition(((SitePanel) component).getPosition() - 1);

                GridBagConstraints c = new GridBagConstraints();
                c.gridy = ((SitePanel) component).getPosition();
                c.gridx = 0;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.anchor = GridBagConstraints.PAGE_START;
                c.weightx = 1;
                c.weighty = 0;

                sitePanelView.add(component, c);
            }
        }
        revalidate();
        repaint();
    }
}
