package eu.ydp.idea.ts.spec.generator.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiFile;

import java.io.File;

public class GenerateSpecAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        ProjectPathsProvider projectPathsProvider = new ProjectPathsProvider(project);
        EditorFileAdapter editorFileAdapter = new EditorFileAdapter(project, e.getData(LangDataKeys.PSI_FILE));
        EditorFileNavigator editorFileNavigator = new EditorFileNavigator(project);
        FilePathValidator filePathValidator = new FilePathValidator(projectPathsProvider.getSourcePath());
        boolean isTSFile = filePathValidator.isTSFile(editorFileAdapter.getFilePath());
        boolean isInSourcePath = filePathValidator.isInSourcePath(editorFileAdapter.getFilePath());
        String editorFilePath = editorFileAdapter.getFilePath();
        String specPath = projectPathsProvider.getSpecPath();
        String sourcePath = projectPathsProvider.getSourcePath();
        if (isTSFile && isInSourcePath) {
            String editorFileRelativePath = editorFilePath.substring(sourcePath.length());
            String targetSpecFilePath = specPath + editorFileRelativePath;
            targetSpecFilePath = targetSpecFilePath.substring(0, targetSpecFilePath.length() - 3) + ".spec.ts";
            File specFile = new File(targetSpecFilePath);
            if (!specFile.exists()) {
                if(new SpecGenerator().generate(specFile, editorFileRelativePath)) {
                    VirtualFileManager.getInstance().syncRefresh();
                    editorFileNavigator.navigateTo(targetSpecFilePath);
                }
            } else {
                editorFileNavigator.navigateTo(targetSpecFilePath);
            }
        } else if (editorFilePath.substring(editorFilePath.length() - 8).equals(".spec.ts") && editorFilePath.indexOf(specPath) == 0) {
            String editorFileRelativePath = editorFilePath.substring(specPath.length());
            String targetSourceFilePath = sourcePath + editorFileRelativePath.substring(0, editorFileRelativePath.length() - 8) + ".ts";
            File sourceFile = new File(targetSourceFilePath);
            if (sourceFile.exists()) {
                editorFileNavigator.navigateTo(targetSourceFilePath);
            }
        }
    }

    @Override
    public void update(AnActionEvent e) {
        PsiFile currentEditorPsiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (currentEditorPsiFile == null || editor == null) {
            e.getPresentation().setEnabled(false);
        } else {
            e.getPresentation().setEnabled(true);
        }
    }
}
