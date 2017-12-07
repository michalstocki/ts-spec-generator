package eu.ydp.idea.ts.spec.generator;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import eu.ydp.idea.ts.spec.generator.settings.SpecSettings;

import java.io.IOException;

public class ProjectPathsProvider
{

    private Project project;

    public ProjectPathsProvider(Project project)
    {
        this.project = project;
    }

    public VirtualFile getSourcePath()
    {
        PropertiesComponent properties = PropertiesComponent.getInstance(project);
        String sourcePath = properties.getValue(SpecSettings.SOURCE_PATH_KEY);
        return project.getBaseDir().findFileByRelativePath(sourcePath);
    }

    public VirtualFile getSpecPath()
    {

        try
        {

            VirtualFile baseDir = project.getBaseDir();
            PropertiesComponent properties = PropertiesComponent.getInstance(project);
            return FileUtils.ensurePathExists(baseDir, properties.getValue(SpecSettings.SPEC_PATH_KEY));

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }


    }
}
