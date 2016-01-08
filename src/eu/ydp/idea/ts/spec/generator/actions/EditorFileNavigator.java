package eu.ydp.idea.ts.spec.generator.actions;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

public class EditorFileNavigator {
    private Project project;

    public EditorFileNavigator(Project project) {
        this.project = project;
    }

    public void navigateTo(String targetSpecFilePath) {
        VirtualFile virtualSpecFile = LocalFileSystem.getInstance().findFileByPath(targetSpecFilePath);
        if (virtualSpecFile != null) {
            PsiFile psiSpecFile = PsiManager.getInstance(project).findFile(virtualSpecFile);
            if (psiSpecFile != null) {
                psiSpecFile.navigate(true);
            }
        }
    }
}
