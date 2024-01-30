package pro.mobicode.mobile.ide.util.dialogs;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CoTeaCreateScreenDialog extends DialogWrapper {
    private JPanel mainPanel;
    private JTextField screenName;

    public CoTeaCreateScreenDialog(@Nullable Project project) {
        super(project);
        setTitle("Create CoTea Screen");
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return mainPanel;
    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return screenName;
    }

    public String getCoTeaScreenName() {
        return screenName.getText();
    }
}
