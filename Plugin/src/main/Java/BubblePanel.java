import javax.swing.*;
import java.awt.*;

public class BubblePanel extends JPanel {
    private final String message;
    private final boolean isUserMessage;

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

        // Calculate bubble size based on message length
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(message);
        int textHeight = fm.getHeight();

        int padding = 15; // Padding around the text
        int tailSize = 10; // Tail size
        int bubbleWidth = textWidth + padding * 2;
        int bubbleHeight = textHeight + padding * 2;

        // Add more space if the message is multiline (to accommodate the text wrapping)
        String[] lines = message.split("\n");
        bubbleHeight = lines.length * fm.getHeight() + padding * 2; // Adjust for multiline

        // Draw bubble with rounded corners
        int arc = 20; // Roundness of the bubble
        int bubbleX = isUserMessage ? 10 : tailSize + 10; // X position for bubble
        int bubbleY = 0; // Y position for bubble

        g2d.fillRoundRect(bubbleX, bubbleY, bubbleWidth, bubbleHeight - tailSize, arc, arc);

        // Bubble tail
        int[] xPoints = isUserMessage
                ? new int[]{bubbleX + bubbleWidth - tailSize - 10, bubbleX + bubbleWidth - 10, bubbleX + bubbleWidth - tailSize - 10}
                : new int[]{bubbleX + tailSize + 10, bubbleX + 10, bubbleX + tailSize + 10};
        int[] yPoints = {bubbleY + bubbleHeight - tailSize, bubbleY + bubbleHeight - tailSize, bubbleY + bubbleHeight};
        g2d.fillPolygon(xPoints, yPoints, 3);

        // Draw the message inside the bubble
        g2d.setColor(Color.BLACK);
        g2d.setFont(getFont());

        // Handle drawing multiple lines
        int y = fm.getAscent() + 10;
        for (String line : lines) {
            g2d.drawString(line, bubbleX + padding, y);
            y += fm.getHeight();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        // Calculate preferred size dynamically based on message length
        FontMetrics fm = getFontMetrics(getFont());
        int textWidth = fm.stringWidth(message);
        int textHeight = fm.getHeight();
        int padding = 20; // Padding around the text

        // Handle multiline message height
        String[] lines = message.split("\n");
        int bubbleHeight = lines.length * textHeight + padding * 2;
        int bubbleWidth = textWidth + padding * 2;

        return new Dimension(bubbleWidth, bubbleHeight);
    }
}
