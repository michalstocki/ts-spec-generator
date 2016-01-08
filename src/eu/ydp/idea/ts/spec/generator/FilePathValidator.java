package eu.ydp.idea.ts.spec.generator;

public class FilePathValidator {
    private String sourcePath;

    public FilePathValidator(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public boolean isTSFile(String filePath) {
        return filePath.substring(filePath.length() - 3).equals(".ts");
    }

    public boolean isInSourcePath(String filePath) {
        return filePath.indexOf(sourcePath) == 0;
    }
}
