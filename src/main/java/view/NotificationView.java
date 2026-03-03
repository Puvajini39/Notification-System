package view;

import controller.NotificationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotificationView extends JFrame {
    private JTextField deliveryPersonIdField;
    private JTextField emailField;  // New field for email
    private JTextArea messageArea;
    private JButton sendButton;
    private JTextArea outputArea;

    private NotificationController controller;

    public NotificationView(NotificationController controller) {
        this.controller = controller;
        setTitle("Send Notification to Delivery Personnel");
        setSize(500, 500);  // Increased size to accommodate new field
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null);  // Center on screen
    }

    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 1, 5, 5));  // Changed to 5 rows
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Delivery Personnel ID
        inputPanel.add(new JLabel("Delivery Personnel ID:"));
        deliveryPersonIdField = new JTextField();
        inputPanel.add(deliveryPersonIdField);

        // Email Address (new field)
        inputPanel.add(new JLabel("Email Address:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        // Notification Message
        inputPanel.add(new JLabel("Notification Message:"));
        messageArea = new JTextArea(3, 20);
        inputPanel.add(new JScrollPane(messageArea));

        sendButton = new JButton("Send Notification");
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(sendButton, BorderLayout.CENTER);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        add(panel);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dpId = deliveryPersonIdField.getText().trim();
                String email = emailField.getText().trim();  // Get email from field
                String message = messageArea.getText().trim();

                if (dpId.isEmpty() || email.isEmpty() || message.isEmpty()) {
                    JOptionPane.showMessageDialog(NotificationView.this,
                            "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate email format
                if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    JOptionPane.showMessageDialog(NotificationView.this,
                            "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean success = controller.sendNotification(dpId, message);
                if (success) {
                    outputArea.append("Notification sent to " + dpId + " (" + email + "): " + message + "\n");
                } else {
                    outputArea.append("Failed to send notification to " + dpId + " (" + email + ")\n");
                }
            }
        });
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            NotificationController controller = new NotificationController();
            NotificationView view = new NotificationView(controller);
            view.setVisible(true);
        });
    }
}