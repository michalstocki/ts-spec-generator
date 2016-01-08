package eu.ydp.idea.ts.spec.generator;

import org.apache.commons.lang.StringUtils;

public class TSFileDataExtractor {
    private String editorFileRelativePath;

    public TSFileDataExtractor(String editorFileRelativePath) {
        this.editorFileRelativePath = editorFileRelativePath;
    }

    public String getClassRelativePath() {
        return editorFileRelativePath.substring(0, editorFileRelativePath.lastIndexOf("/"));
    }

    public String getTestsRelativePath() {
        Integer neededParents = StringUtils.countMatches(editorFileRelativePath, "/");
        return "../" + new String(new char[neededParents]).replace("\0", "../");
    }

    public String getClassName() {
        int startIndex = 0;
        if (editorFileRelativePath.contains("/")) {
            startIndex = editorFileRelativePath.lastIndexOf("/") + 1;
        }
        return editorFileRelativePath.substring(startIndex, editorFileRelativePath.length() - 3);
    }

    public String getObjectName() {
        String className = getClassName();
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }
}
