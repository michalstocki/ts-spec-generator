package eu.ydp.idea.ts.spec.generator;

import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.lang.StringUtils;

public class TSFileDataExtractor {
    private VirtualFile editorFilePath;
    private String editorFileRelativePath;

    public TSFileDataExtractor(VirtualFile editorFilePath, String editorFileRelativePath) {
        this.editorFilePath = editorFilePath;
        this.editorFileRelativePath = editorFileRelativePath;
    }

    public String getClassRelativePath() {
        System.out.println(editorFileRelativePath);
        return editorFileRelativePath;
    }

    public String getTestsRelativePath() {
        Integer neededParents = StringUtils.countMatches(editorFileRelativePath, "/");
        return "../" + new String(new char[neededParents]).replace("\0", "../");
    }

    public String getObjectName() {
        String className = getClassName();
        return className;
    }

    public String getClassName() {

        return this.editorFilePath.getNameWithoutExtension();
    }
}
