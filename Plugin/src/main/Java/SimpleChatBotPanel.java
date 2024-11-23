import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleChatBotPanel {
    private final JPanel mainPanel;
    private final JTextField inputTextField;
    private final JTextArea outputTextArea;

    public SimpleChatBotPanel() {
        // Initialize components
        mainPanel = new JPanel(new BorderLayout());
        inputTextField = new JTextField();
        outputTextArea = new JTextArea();

        // Set up the output area
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        // Create a panel for the input and button
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputTextField, BorderLayout.CENTER);

        // Create the send button as a local variable
        JButton sendButton = new JButton("Send");
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Add components to the main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add action listener to the button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputTextField.getText();
                // Simulate a response - in a real app, call your chatbot logic
                String response = "You said: " + inputText;
                outputTextArea.append(response + "\n");
                inputTextField.setText(""); // Clear the input field after submission
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}