package eu.ydp.idea.ts.spec.generator;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

public class EditorFileNavigator
{
    private Project project;

    public EditorFileNavigator(Project project)
    {
        this.project = project;
    }

    public void navigateTo(VirtualFile targetSpecFilePath)
    {
        PsiFile psiSpecFile = PsiManager.getInstance(project).findFile(targetSpecFilePath);
        if (psiSpecFile != null)
        {
            psiSpecFile.navigate(true);
        }
    }
}
