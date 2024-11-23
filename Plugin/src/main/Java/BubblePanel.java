import javax.swing.*;
import java.awt.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;

public class BubblePanel extends JPanel {
    private final String message;
    private final boolean isUserMessage;

    public BubblePanel(String message, boolean isUserMessage) {
        this.message = message;
        this.isUserMessage = isUserMessage;
        setOpaque(false); // Transparent background
        setLayout(new BorderLayout());

        // Parse Markdown to HTML
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        String htmlMessage = renderer.render(parser.parse(message));

        // Create JEditorPane to display HTML
        JEditorPane editorPane = new JEditorPane("text/html", htmlMessage);
        editorPane.setEditable(false);
        editorPane.setOpaque(false);
        editorPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Set background color based on message type
        editorPane.setBackground(isUserMessage ? new Color(173, 216, 230) : new Color(211, 211, 211));

        add(editorPane, BorderLayout.CENTER);
    }
    @Override
    public Dimension getPreferredSize() {
        FontMetrics fm = getFontMetrics(getFont());
        int maxWidth = 0;
        int totalHeight = 0;
        int padding = 20; // Padding around the text

        // Calculate the width and height based on each line
        String[] lines = message.split("\n");
        for (String line : lines) {
            int lineWidth = fm.stringWidth(line);
            maxWidth = Math.max(maxWidth, lineWidth);
            totalHeight += fm.getHeight();
        }

        int bubbleWidth = maxWidth + padding * 2;
        int bubbleHeight = totalHeight + padding * 2;

        return new Dimension(bubbleWidth, bubbleHeight);
    }
}