package pro.mobicode.mobile.ide.util.dialogs;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CoTeaCreateClassesDialog extends DialogWrapper {

    private JPanel mainPanel;
    private JTextField classPrefix;

    public CoTeaCreateClassesDialog(@Nullable Project project) {
        super(project);
        setTitle("Create CoTea Classes");
        init();

//        setOKActionEnabled(false);
//
//        Supplier<? extends ValidationInfo> validator = () -> {
//            return null;
//        };
//
//        new ComponentValidator(getDisposable())
//                .withValidator(validator)
//                .installOn(classPrefix);
    }

    public String getCoTeaClassPrefix() {
        return classPrefix.getText();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return mainPanel;
    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return classPrefix;
    }
}
