package eu.ydp.idea.ts.spec.generator;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import eu.ydp.idea.ts.spec.generator.settings.SpecSettings;

import java.io.IOException;

public class SpecGenerator
{
    public static final String DEFAULT_SOURCE_PATH = "/src ";
    public static final String DEFAULT_SPEC_PATH = "/test/spec";

    public static String TEMPLATE = "import {$ClassName$} from '$TestsRelativePath$../src/$classRelativePath$/$ClassName$';\n" +
            "\n" +
            "describe('$ClassName$', () => {\n" +
            "  let $ObjName$:$ClassName$;\n" +
            "\n" +
            "  beforeEach(() => {\n" +
            "    $ObjName$ = new $ClassName$();\n" +
            "  });\n" +
            "\n" +
            "  describe('', () => {\n" +
            "    it('', () => {\n" +
            "      // given\n" +
            "\n" +
            "      // when\n" +
            "\n" +
            "      // then\n" +
            "    });\n" +
            "  });\n" +
            "});\n";

    private PropertiesComponent properties = null;

    public SpecGenerator(Project project)
    {

        properties = PropertiesComponent.getInstance(project);
    }

    public void generateAndNavigateTo(VirtualFile editorFilePath, String editorFileRelativePath, VirtualFile specFile, EditorFileNavigator editorFileNavigator) throws IOException
    {

        ApplicationManager.getApplication().runWriteAction(new Runnable()
        {
            @Override
            public void run()
            {
                TSFileDataExtractor extractor = new TSFileDataExtractor(editorFilePath,editorFileRelativePath);
                String content = properties.getValue(SpecSettings.SPEC_TEMPLATE_KEY, TEMPLATE);
                content = content.replaceAll("\\$ClassName\\$", extractor.getClassName());
                content = content.replaceAll("\\$ObjName\\$", extractor.getObjectName());
                content = content.replaceAll("\\$TestsRelativePath\\$", extractor.getTestsRelativePath());
                content = content.replaceAll("\\$classRelativePath\\$", extractor.getClassRelativePath());
                try
                {
                    specFile.setBinaryContent(content.getBytes());
                    editorFileNavigator.navigateTo(specFile);
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
