package eu.ydp.idea.ts.spec.generator;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import eu.ydp.idea.ts.spec.generator.settings.SpecSettings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SpecGenerator {

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

    public SpecGenerator(Project project) {
        properties = PropertiesComponent.getInstance(project);
    }

    public boolean generate(File specFile, String editorFileRelativePath) {
        try {
            TSFileDataExtractor extractor = new TSFileDataExtractor(editorFileRelativePath);
            FileWriter fileWriter = new FileWriter(specFile);
            String content = properties.getValue(SpecSettings.SPEC_TEMPLATE_KEY, TEMPLATE);
            content = content.replaceAll("\\$ClassName\\$", extractor.getClassName());
            content = content.replaceAll("\\$ObjName\\$", extractor.getObjectName());
            content = content.replaceAll("\\$TestsRelativePath\\$", extractor.getTestsRelativePath());
            content = content.replaceAll("\\$classRelativePath\\$", extractor.getClassRelativePath());
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
            return true;
        } catch (IOException e1) {
            e1.printStackTrace();
            return false;
        }
    }
}
