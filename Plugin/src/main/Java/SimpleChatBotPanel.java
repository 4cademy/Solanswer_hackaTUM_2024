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
        mainPanel = new JPanel(new BorderLayout(10, 10)); // Add some padding
        inputTextField = new JTextField();
        outputTextArea = new JTextArea();

        // Set up the output area
        outputTextArea.setEditable(false);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Set a readable font
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        scrollPane.setPreferredSize(new Dimension(500, 300)); // Set preferred size for output area

        // Create a panel for the input and button
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5)); // Add some padding
        inputTextField.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Set a readable font
        inputPanel.add(inputTextField, BorderLayout.CENTER);

        // Create the send button as a local variable
        JButton sendButton = new JButton("Send");
        sendButton.setFont(new Font("SansSerif", Font.BOLD, 14)); // Set a bold font for the button
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Add components to the main panel
        mainPanel.add(inputPanel, BorderLayout.SOUTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add action listener to the button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputTextField.getText();
                if (!inputText.trim().isEmpty()) {
                    // Simulate a response - in a real app, call your chatbot logic
                    String response = "You said: " + inputText;
                    outputTextArea.append("You: " + inputText + "\n");
                    outputTextArea.append("JetSetters bot: " + response + "\n\n");
                    inputTextField.setText(""); // Clear the input field after submission
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}