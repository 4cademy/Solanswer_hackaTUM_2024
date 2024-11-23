import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SimpleChatBotAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        SimpleChatBotDialog dialog = new SimpleChatBotDialog();
        dialog.show();
    }

    private static class SimpleChatBotDialog extends DialogWrapper {
        private final SimpleChatBotPanel simpleChatBotPanel;

        protected SimpleChatBotDialog() {
            super(true); // Use current window as parent
            simpleChatBotPanel = new SimpleChatBotPanel();
            init();
            setTitle("JetSetters Chat Bot");
        }

        @Nullable
        @Override
        protected JComponent createCenterPanel() {
            return simpleChatBotPanel.getMainPanel();
        }
    }
}
