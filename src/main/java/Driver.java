import java.awt.*;

public class Driver {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GUI gui = new GUI();
            gui.getSidebarView().addSite("Gardiner", "06192500");
            gui.getSidebarView().addSite("Logan", "06052500");
        });
    }
}
