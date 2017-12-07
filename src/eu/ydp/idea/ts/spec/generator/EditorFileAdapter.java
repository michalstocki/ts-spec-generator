package eu.ydp.idea.ts.spec.generator;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;

public class EditorFileAdapter {
    private Project project;
    private PsiFile psiFile;

    public EditorFileAdapter(Project project, PsiFile psiFile) {
        this.project = project;
        this.psiFile = psiFile;
    }

    public VirtualFile getFile() {
        return psiFile.getVirtualFile();
    }
}
