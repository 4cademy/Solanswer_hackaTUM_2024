import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class SimpleChatBotPanel {
    private final JPanel mainPanel;
    private final JTextArea inputTextArea;
    private final JScrollPane scrollPane;
    private final JPanel chatBoxPanel;

    public SimpleChatBotPanel() {
        // Main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Chat box panel (holds chat bubbles)
        chatBoxPanel = new JPanel();
        chatBoxPanel.setLayout(new BoxLayout(chatBoxPanel, BoxLayout.Y_AXIS));

        // Scroll panel for chat box
        scrollPane = new JScrollPane(chatBoxPanel);
        scrollPane.setPreferredSize(new Dimension(1000, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input field (multi-line text area)
        inputTextArea = new JTextArea(3, 40);
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        inputTextArea.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Add KeyListener for Enter/Shift+Enter functionality
        inputTextArea.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (e.isShiftDown()) {
                        // Add a new line if Shift + Enter is pressed
                        inputTextArea.append("\n");
                    } else {
                        // Send the message when Enter is pressed
                        e.consume(); // Prevent the default newline action in the text area
                        sendMessage();
                    }
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        // Input panel with the text area
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        inputPanel.add(inputScrollPane, BorderLayout.CENTER);

        // Send button
        JButton sendButton = new JButton("Send");
        sendButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        sendButton.setBackground(new Color(0, 123, 255));
        sendButton.setForeground(Color.WHITE);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Add components to main panel
        mainPanel.add(scrollPane);
        mainPanel.add(inputPanel);

        // Send button action listener
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String inputText = inputTextArea.getText().trim();
        if (!inputText.isEmpty()) {
            // Append the user's message
            appendMessage("You: " + inputText, true);

            // Send the question to the backend and get the response
            try {
                ChatBotService chatBotService = new ChatBotService();
                String response = chatBotService.sendQuestion(inputText);
                appendMessage("Bot: " + response, false);
            } catch (IOException e) {
                appendMessage("Bot: Error communicating with the server.", false);
            }

            inputTextArea.setText(""); // Clear the input field
        }
    }

    private void appendMessage(String message, boolean isUserMessage) {
        // Bubble panel for each message
        BubblePanel bubble = new BubblePanel(message, isUserMessage);
        bubble.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Align bubbles
        JPanel bubbleContainer = new JPanel();
        bubbleContainer.setOpaque(false);
        bubbleContainer.setLayout(new FlowLayout(isUserMessage ? FlowLayout.RIGHT : FlowLayout.LEFT, 10, 10));

        if (isUserMessage) {
            bubbleContainer.add(Box.createHorizontalGlue()); // Align to right
            bubbleContainer.add(bubble);
        } else {
            bubbleContainer.add(bubble);
            bubbleContainer.add(Box.createHorizontalGlue()); // Align to left
        }

        // Add bubble to chat box
        chatBoxPanel.add(bubbleContainer);
        chatBoxPanel.add(Box.createVerticalStrut(10)); // Spacing between bubbles
        chatBoxPanel.revalidate();
        chatBoxPanel.repaint();

        // Scroll to bottom
        JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
