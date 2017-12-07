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
    public static String SPEC_PATH_KEY = "eu.ydp.idea.ts.spec.generator.settings.spec.path";
    public static String SOURCE_PATH_KEY = "eu.ydp.idea.ts.spec.generator.settings.source.path";

    @Nullable
    private SpecSettingsForm settingsForm = null;
    private PropertiesComponent properties = null;

    public SpecSettings(@NotNull Project project) {
        properties = PropertiesComponent.getInstance(project);
        if (!properties.isValueSet(SPEC_TEMPLATE_KEY)) {
           properties.setValue(SPEC_TEMPLATE_KEY, SpecGenerator.TEMPLATE);
        }

        if (!properties.isValueSet(SPEC_PATH_KEY)) {
            properties.setValue(SPEC_PATH_KEY, SpecGenerator.DEFAULT_SPEC_PATH);
        }

        if (!properties.isValueSet(SOURCE_PATH_KEY)) {
            properties.setValue(SOURCE_PATH_KEY, SpecGenerator.DEFAULT_SOURCE_PATH);
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
        boolean modified1 = !Objects.equals(getForm().getSpecTemplate(), properties.getValue(SPEC_TEMPLATE_KEY));
        boolean modified2 = !Objects.equals(getForm().getSpecPath(), properties.getValue(SPEC_PATH_KEY));
        boolean modified3 = !Objects.equals(getForm().getSourcePath(), properties.getValue(SOURCE_PATH_KEY));
        return modified1||modified2|modified3;
    }

    @Override
    public void apply() throws ConfigurationException {
        properties.setValue(SPEC_TEMPLATE_KEY, getForm().getSpecTemplate());
        properties.setValue(SPEC_PATH_KEY, getForm().getSpecPath());
        properties.setValue(SOURCE_PATH_KEY, getForm().getSourcePath());
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
            settingsForm.setSpecPath(properties.getValue(SPEC_PATH_KEY));
            settingsForm.setSourcePath(properties.getValue(SOURCE_PATH_KEY));
        }
        return settingsForm;
    }
}
