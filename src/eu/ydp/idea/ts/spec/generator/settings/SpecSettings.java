package eu.ydp.idea.ts.spec.generator.settings;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import eu.ydp.idea.ts.spec.generator.SpecGenerator;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Objects;

public class SpecSettings implements Configurable {

    public static String SPEC_TEMPLATE_KEY = "eu.ydp.idea.ts.spec.generator.settings.spec.template";

    @Nullable
    private SpecSettingsForm settingsForm = null;
    private PropertiesComponent properties = null;

    public SpecSettings(@NotNull Project project) {
        properties = PropertiesComponent.getInstance(project);
        if (!properties.isValueSet(SPEC_TEMPLATE_KEY)) {
           properties.setValue(SPEC_TEMPLATE_KEY, SpecGenerator.TEMPLATE);
        }
    }

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
        return !Objects.equals(getForm().getSpecTemplate(), properties.getValue(SPEC_TEMPLATE_KEY));
    }

    @Override
    public void apply() throws ConfigurationException {
        properties.setValue(SPEC_TEMPLATE_KEY, getForm().getSpecTemplate());
    }

    @Override
    public void reset() {
        getForm().setSpecTemplate(properties.getValue(SPEC_TEMPLATE_KEY));
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
            settingsForm.setSpecTemplate(properties.getValue(SPEC_TEMPLATE_KEY));
        }
        return settingsForm;
    }
}
