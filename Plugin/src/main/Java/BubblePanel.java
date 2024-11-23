import javax.swing.*;
import java.awt.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;

/**
 * A custom panel for displaying chat bubbles with Markdown support.
 */
public class BubblePanel extends JPanel {
    private final String message;
    private final boolean isUserMessage;
    private static final int MAX_BUBBLE_WIDTH = 800; // Maximum bubble width

    /**
     * Constructs a BubblePanel with a message and type (user or bot).
     *
     * @param message The text to display in the bubble.
     * @param isUserMessage Indicates whether the message is from the user.
     */
    public BubblePanel(String message, boolean isUserMessage) {
        this.message = wrapText(message); // Ensure text and code are wrapped
        this.isUserMessage = isUserMessage;

        // Panel settings
        setOpaque(false); // Make the background transparent
        setLayout(new BorderLayout());

        // Create and style the bubble content

        JLabel label = createWrappedLabel(this.message);
        add(label, BorderLayout.CENTER);
    }

    /**
     * Wraps text manually if it exceeds the maximum width. Applies to both sentences and code blocks.
     *
     * @param text The input message.
     * @return A wrapped version of the message.
     */
    private String wrapText(String text) {
        FontMetrics metrics = getFontMetrics(getFont());
        StringBuilder wrappedText = new StringBuilder();
        String[] lines = text.split("\n"); // Split by lines first (handles code blocks)

        for (String line : lines) {
            int lineWidth = 0;
            String[] words = line.split(" ");

            for (String word : words) {
                int wordWidth = metrics.stringWidth(word + " ");
                if (lineWidth + wordWidth > MAX_BUBBLE_WIDTH - 40) { // Adjust for padding
                    wrappedText.append("<br>"); // Add line break
                    lineWidth = 0;
                }
                wrappedText.append(word).append(" ");
                lineWidth += wordWidth;
            }
            wrappedText.append("<br>"); // Preserve original line breaks
        }

        return wrappedText.toString().trim();
    }

    /**
     * Creates a JLabel that wraps the text automatically within the maximum width.
     *
     * @param message The message to display.
     * @return A styled JLabel with wrapped text.
     */
    private JLabel createWrappedLabel(String message) {
        // Parse Markdown to plain HTML text
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        String htmlMessage = renderer.render(parser.parse(message));

        // Configure JLabel for text wrapping
        JLabel label = new JLabel("<html><div style='width:" + (MAX_BUBBLE_WIDTH - 20) + "px; word-wrap: break-word; white-space: pre-wrap;'>" + htmlMessage + "</div></html>");
        label.setOpaque(false);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Set font color and alignment
        label.setForeground(Color.BLACK);
        label.setVerticalAlignment(SwingConstants.TOP);

        return label;
    }

    @Override
    public Dimension getPreferredSize() {

        // Calculate the preferred size dynamically
        Component label = getComponent(0);
        Dimension size = label.getPreferredSize();

        // Add padding
        int padding = 20;
        int width = Math.min(size.width + padding, MAX_BUBBLE_WIDTH);
        int height = size.height + padding;


        return new Dimension(width, height);
    }

    /**
     * Paints the background bubble shape.
     *
     * @param g The graphics context.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;


        // Enable anti-aliasing for smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Determine bubble color based on the user message
        Color bubbleColor = isUserMessage ? new Color(173, 216, 230) : new Color(211, 211, 211); // Light blue for user, light gray for bot


        // Calculate the bubble shape
        int arcSize = 20; // Rounded corners
        Insets insets = getInsets();
        int x = insets.left;
        int y = insets.top;
        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;

        // Draw the bubble
        g2.setColor(bubbleColor);
        g2.fillRoundRect(x, y, width, height, arcSize, arcSize);
    }
}
