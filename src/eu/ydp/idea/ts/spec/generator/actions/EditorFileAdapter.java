package eu.ydp.idea.ts.spec.generator.actions;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;

public class EditorFileAdapter {
    private Project project;
    private PsiFile psiFile;

    public EditorFileAdapter(Project project, PsiFile psiFile) {
        this.project = project;
        this.psiFile = psiFile;
    }

    public String getFilePath() {
        return psiFile.getVirtualFile().getPath();
    }
}
