package view;

import controller.NotificationController;
import view.NotificationView;

public class NotificationApp {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            NotificationController controller = new NotificationController();
            NotificationView view = new NotificationView(controller);
            view.setVisible(true);
        });
    }
}
