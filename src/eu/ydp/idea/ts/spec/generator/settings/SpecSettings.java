package eu.ydp.idea.ts.spec.generator.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.Disposer;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SpecSettings implements Configurable {

    @Nullable
    private SpecSettingsForm settingsForm = null;

    @Nls
    @Override
    public String getDisplayName() {
        return "TypeScript Spec Generator";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return getForm().getComponent();
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {

    }

    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {
        if (settingsForm != null) {
            Disposer.dispose(settingsForm);
        }
        settingsForm = null;
    }

    private SpecSettingsForm getForm() {
        if (settingsForm == null) {
            settingsForm = new SpecSettingsForm();
        }
        return settingsForm;
    }
}
