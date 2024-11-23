import javax.swing.*;
import java.awt.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;

public class BubblePanel extends JPanel {
    private final String message;
    private final boolean isUserMessage;
    private static final int MAX_BUBBLE_WIDTH = 400; // Fixed bubble width

    public BubblePanel(String message, boolean isUserMessage) {
        this.message = message;
        this.isUserMessage = isUserMessage;
        setOpaque(false); // Transparent background

        // Parse Markdown to HTML
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        String htmlMessage = renderer.render(parser.parse(message));

        // Add CSS to ensure text is black
        String styledHtmlMessage = "<html><body style='color: black;'>" + htmlMessage + "</body></html>";

        // Create JEditorPane to display HTML
        JEditorPane editorPane = new JEditorPane("text/html", styledHtmlMessage);
        editorPane.setEditable(false);
        editorPane.setOpaque(false);
        editorPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Set layout and add editorPane
        setLayout(new BorderLayout());
        add(editorPane, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set bubble color
        g2d.setColor(isUserMessage ? new Color(173, 216, 230) : new Color(211, 211, 211)); // Light blue for user, light gray for bot

        // Calculate bubble size
        FontMetrics fm = g2d.getFontMetrics();
        int padding = 15; // Padding around the text
        int tailSize = 10; // Tail size

        // Calculate wrapped text dimensions
        String[] lines = wrapText(message, fm, MAX_BUBBLE_WIDTH - padding * 2);
        int bubbleWidth = Math.min(MAX_BUBBLE_WIDTH, fm.stringWidth(message) + padding * 2);
        int bubbleHeight = lines.length * fm.getHeight() + padding * 2;

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
    }

    @Override
    public Dimension getPreferredSize() {
        FontMetrics fm = getFontMetrics(getFont());
        int padding = 20; // Padding around the text

        // Calculate preferred size based on wrapped text
        String[] lines = wrapText(message, fm, MAX_BUBBLE_WIDTH - padding * 2);
        int bubbleHeight = lines.length * fm.getHeight() + padding * 2;
        int bubbleWidth = Math.min(MAX_BUBBLE_WIDTH, fm.stringWidth(message) + padding * 2);

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