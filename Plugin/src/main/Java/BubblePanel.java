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
        this.message = message;
        this.isUserMessage = isUserMessage;

        // Panel settings
        setOpaque(false); // Make the background transparent
        setLayout(new BorderLayout());

        // Create and style the bubble content
        JEditorPane editorPane = createStyledEditorPane(message);
        add(editorPane, BorderLayout.CENTER);
    }

    /**
     * Creates a styled JEditorPane for rendering the bubble's content.
     *
     * @param message The message to render in the bubble.
     * @return A styled JEditorPane.
     */
    private JEditorPane createStyledEditorPane(String message) {
        // Parse Markdown to HTML
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        String htmlMessage = renderer.render(parser.parse(message));

        // Configure the JEditorPane
        JEditorPane editorPane = new JEditorPane("text/html", htmlMessage);
        editorPane.setEditable(false);
        editorPane.setOpaque(false);
        editorPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 30));
        editorPane.setPreferredSize(new Dimension(MAX_BUBBLE_WIDTH, 0)); // Set preferred size for wrapping
        editorPane.setSize(new Dimension(MAX_BUBBLE_WIDTH, editorPane.getPreferredSize().height)); // Ensure it can grow vertically

        // Set bubble background color based on message type
        Color bubbleColor = isUserMessage ? new Color(173, 216, 230) : new Color(211, 211, 211);
        editorPane.setBackground(bubbleColor);

        // Add CSS to enable word wrapping
        editorPane.setContentType("text/html");
        editorPane.setText("<html><body style='word-wrap: break-word;'>" + htmlMessage + "</body></html>");

        // Apply CSS to ensure text is black
        editorPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        editorPane.setContentType("text/html");
        editorPane.setText("<html><body style='color: black; font-family: Arial;'>" + htmlMessage + "</body></html>");

        return editorPane;
    }

    @Override
    public Dimension getPreferredSize() {
        // Calculate dimensions for the bubble
        FontMetrics fontMetrics = getFontMetrics(getFont());
        int padding = 20; // Padding around the text

        int maxWidth = 0;
        int totalHeight = 0;

        for (String line : message.split("\n")) {
            int lineWidth = fontMetrics.stringWidth(line);
            maxWidth = Math.max(maxWidth, lineWidth);
            totalHeight += fontMetrics.getHeight();
        }

        int bubbleWidth = Math.min(maxWidth + padding * 2, MAX_BUBBLE_WIDTH);
        int bubbleHeight = totalHeight + padding * 2;

        return new Dimension(bubbleWidth, bubbleHeight);
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

        // Determine bubble color
        Color bubbleColor = isUserMessage ? new Color(173, 216, 230) : new Color(211, 211, 211);

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