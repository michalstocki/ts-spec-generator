package eu.ydp.idea.ts.spec.generator.actions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SpecGenerator {
    public boolean generate(File specFile, String editorFileRelativePath) {
        try {
            TSFileDataExtractor extractor = new TSFileDataExtractor(editorFileRelativePath);
            FileWriter fileWriter = new FileWriter(specFile);
            String content = TEMPLATE;
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

    private String TEMPLATE = "/// <reference path=\"$TestsRelativePath$references.ts\" />\n" +
            "import {SingleUnitIsolator} from '$TestsRelativePath$utils/mocking/SingleUnitIsolator';\n" +
            "import {$ClassName$} from '$TestsRelativePath$../src/$classRelativePath$/$ClassName$';\n" +
            "\n" +
            "describe('$ClassName$', () => {\n" +
            "  let $ObjName$:$ClassName$;\n" +
            "\n" +
            "  beforeEach(() => {\n" +
            "    const isolator:SingleUnitIsolator = SingleUnitIsolator.around($ClassName$);\n" +
            "    $ObjName$ = isolator.get($ClassName$);\n" +
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
}
