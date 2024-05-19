package com.WaterWatcher;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public abstract class StyleLibrary {
    public static final Font headings = new Font(Font.SANS_SERIF, Font.BOLD, 24);
    public static final Font body = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    public static final Font locationHeading = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    public static final Font siteDetails = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
    public static Border DEFAULT_BORDER = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.LIGHT_GRAY);



}
