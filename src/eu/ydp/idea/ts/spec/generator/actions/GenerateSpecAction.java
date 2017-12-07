package eu.ydp.idea.ts.spec.generator.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import eu.ydp.idea.ts.spec.generator.EditorFileAdapter;
import eu.ydp.idea.ts.spec.generator.EditorFileNavigator;
import eu.ydp.idea.ts.spec.generator.FileChildCreator;
import eu.ydp.idea.ts.spec.generator.FilePathValidator;
import eu.ydp.idea.ts.spec.generator.FileUtils;
import eu.ydp.idea.ts.spec.generator.ProjectPathsProvider;
import eu.ydp.idea.ts.spec.generator.SpecGenerator;

import java.io.IOException;

public class GenerateSpecAction extends AnAction
{
    @Override
    public void actionPerformed(AnActionEvent e)
    {
        try
        {
            Project project = e.getProject();
            ProjectPathsProvider projectPathsProvider = new ProjectPathsProvider(project);
            EditorFileAdapter editorFileAdapter = new EditorFileAdapter(project, e.getData(LangDataKeys.PSI_FILE));
            EditorFileNavigator editorFileNavigator = new EditorFileNavigator(project);
            FilePathValidator filePathValidator = new FilePathValidator(projectPathsProvider.getSourcePath());
            boolean isTSFile = filePathValidator.isTSFile(editorFileAdapter.getFile());
            boolean isInSourcePath = filePathValidator.isInSourcePath(editorFileAdapter.getFile());
            VirtualFile editorFilePath = editorFileAdapter.getFile();
            VirtualFile specPath = projectPathsProvider.getSpecPath();
            VirtualFile sourcePath = projectPathsProvider.getSourcePath();
            if (isTSFile && isInSourcePath)
            {
                String editorFileRelativePath = editorFilePath.getParent().getCanonicalPath().substring(sourcePath.getCanonicalPath().length());
                VirtualFile targetSpecFilePath = FileUtils.ensurePathExists(specPath, editorFileRelativePath);
                String srcfileName = editorFilePath.getNameWithoutExtension() + ".spec.ts";
                VirtualFile testSpecFile = targetSpecFilePath.findFileByRelativePath(srcfileName);

                if (testSpecFile == null)
                {
                    FileChildCreator fileChildCreator = new FileChildCreator();
                    VirtualFile testSpecFileToGenerate = fileChildCreator.createFileChild(targetSpecFilePath, srcfileName);
                    SpecGenerator specGenerator = new SpecGenerator(project);
                    specGenerator.generateAndNavigateTo(editorFilePath, editorFileRelativePath, testSpecFileToGenerate, editorFileNavigator);

                }
                else
                {
                    editorFileNavigator.navigateTo(testSpecFile);
                }
            }
            else if (editorFilePath.getCanonicalPath().toLowerCase().trim().endsWith(".spec.ts") && editorFilePath.getCanonicalPath().indexOf(specPath.getCanonicalPath()) == 0)
            {
                String editorFileRelativePath = editorFilePath.getParent().getCanonicalPath().substring(specPath.getCanonicalPath().length());
                VirtualFile targetSourceFileDirectoryPath = sourcePath.findFileByRelativePath(editorFileRelativePath);
                String nameWithoutExtension = editorFilePath.getNameWithoutExtension();
                if (nameWithoutExtension.toLowerCase().endsWith(".spec"))
                {
                    nameWithoutExtension = nameWithoutExtension.substring(0, nameWithoutExtension.length() - 5);
                    VirtualFile targetSourceFilePath = targetSourceFileDirectoryPath.findFileByRelativePath(nameWithoutExtension + ".ts");
                    if (targetSourceFilePath != null)
                    {
                        editorFileNavigator.navigateTo(targetSourceFilePath);
                    }
                }
            }
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(AnActionEvent e)
    {
        PsiFile currentEditorPsiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (currentEditorPsiFile == null || editor == null)
        {
            e.getPresentation().setEnabled(false);
        }
        else
        {
            e.getPresentation().setEnabled(true);
        }
    }
}
