import javax.swing.*;
import java.awt.*;

public class BubblePanel extends JPanel {
    private final String message;
    private final boolean isUserMessage;
    private static final int MAX_BUBBLE_WIDTH = 400; // Fixed bubble width

    public BubblePanel(String message, boolean isUserMessage) {
        this.message = message;
        this.isUserMessage = isUserMessage;
        setOpaque(false); // Transparent background
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set bubble color
        g2d.setColor(isUserMessage ? new Color(173, 216, 230) : new Color(211, 211, 211)); // Light blue for user, light gray for bot

        // Calculate wrapped text and its dimensions
        FontMetrics fm = g2d.getFontMetrics();
        int padding = 15; // Padding around the text
        int tailSize = 10; // Tail size

        // Wrap the message to fit within the bubble's fixed width
        String[] lines = wrapText(message, fm, MAX_BUBBLE_WIDTH - padding * 2);

        // Bubble dimensions
        int bubbleWidth = Math.min(MAX_BUBBLE_WIDTH, fm.stringWidth(message) + padding * 2);
        int bubbleHeight = lines.length * fm.getHeight() + padding * 2;

        // Draw bubble with rounded corners
        int arc = 20; // Roundness of the bubble
        int bubbleX = isUserMessage ? 10 : tailSize + 10; // X position for bubble
        int bubbleY = 0; // Y position for bubble

        g2d.fillRoundRect(bubbleX, bubbleY, bubbleWidth, bubbleHeight - tailSize, arc, arc);

        // Bubble tail - Adjust based on the direction (for user or bot)
        int[] xPoints = isUserMessage
                ? new int[]{bubbleX + bubbleWidth - tailSize - 10, bubbleX + bubbleWidth - 10, bubbleX + bubbleWidth - tailSize - 10}
                : new int[]{bubbleX + tailSize + 10, bubbleX + 10, bubbleX + tailSize + 10};
        int[] yPoints = {bubbleY + bubbleHeight - tailSize, bubbleY + bubbleHeight - tailSize, bubbleY + bubbleHeight};
        g2d.fillPolygon(xPoints, yPoints, 3);

        // Draw the wrapped message inside the bubble
        g2d.setColor(Color.BLACK);
        g2d.setFont(getFont());

        // Handle drawing multiple lines
        int y = fm.getAscent() + padding; // Starting Y position for the first line
        for (String line : lines) {
            g2d.drawString(line, bubbleX + padding, y);
            y += fm.getHeight();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        // Calculate preferred size based on wrapped text
        FontMetrics fm = getFontMetrics(getFont());
        int padding = 20; // Padding around the text

        // Wrap the message to fit the bubble's width
        String[] lines = wrapText(message, fm, MAX_BUBBLE_WIDTH - padding * 2);

        // Height and width of the bubble
        int bubbleHeight = lines.length * fm.getHeight() + padding * 2;
        int bubbleWidth = Math.min(MAX_BUBBLE_WIDTH, fm.stringWidth(message) + padding * 2);

        // Ensure that the bubble width is adjusted correctly with the tail
        if (!isUserMessage) {
            bubbleWidth += 10; // For bot bubbles, we add some extra width for the tail
        }

        return new Dimension(bubbleWidth, bubbleHeight);
    }

    // Utility method to wrap the text based on the given width
    private String[] wrapText(String text, FontMetrics fm, int maxWidth) {
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();
        java.util.List<String> lines = new java.util.ArrayList<>();

        for (String word : words) {
            if (fm.stringWidth(currentLine + " " + word) <= maxWidth) {
                if (currentLine.length() > 0) {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            } else {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            }
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines.toArray(new String[0]);
    }
}
