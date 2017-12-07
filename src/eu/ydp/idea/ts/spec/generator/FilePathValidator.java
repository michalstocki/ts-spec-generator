package eu.ydp.idea.ts.spec.generator;

import com.intellij.openapi.vfs.VirtualFile;

public class FilePathValidator
{
    private VirtualFile sourcePath;

    public FilePathValidator(VirtualFile sourcePath)
    {
        this.sourcePath = sourcePath;
    }

    public boolean isTSFile(VirtualFile filePath)
    {
        return "ts".equals(filePath.getExtension());
    }

    public boolean isInSourcePath(VirtualFile filePath)
    {
        String canonicalPath = filePath.getCanonicalPath();
        return canonicalPath.indexOf(sourcePath.getCanonicalPath()) == 0;
    }
}
