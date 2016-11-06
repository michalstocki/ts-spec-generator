package eu.ydp.idea.ts.spec.generator.settings;

import com.intellij.ide.highlighter.HighlighterFactory;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.fileTypes.UnknownFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class SpecSettingsForm implements Disposable {
    private JPanel rootPanel;
    private JPanel myEditorPanel;

    @Nullable
    private Editor myEditor;

    public JComponent getComponent() {
        return rootPanel;
    }

    @Override
    public void dispose() {
        if (myEditor != null && !myEditor.isDisposed()) {
            EditorFactory.getInstance().releaseEditor(myEditor);
        }
        myEditor = null;
    }

    private void createUIComponents() {
        myEditorPanel = new JPanel(new BorderLayout());

        myEditor = createEditor();
        myEditorPanel.add(myEditor.getComponent(), BorderLayout.CENTER);
    }

    @NotNull
    private static Editor createEditor() {
        EditorFactory editorFactory = EditorFactory.getInstance();
        Document editorDocument = editorFactory.createDocument("");
        EditorEx editor = (EditorEx)editorFactory.createEditor(editorDocument);
        fillEditorSettings(editor.getSettings());
        setHighlighting(editor);
        return editor;
    }

    private static void setHighlighting(EditorEx editor) {
        final FileType tsFileType = FileTypeManager.getInstance().getFileTypeByExtension("ts");
        if (tsFileType == UnknownFileType.INSTANCE) {
            return;
        }
        final EditorHighlighter editorHighlighter =
                HighlighterFactory.createHighlighter(tsFileType, EditorColorsManager.getInstance().getGlobalScheme(), null);
        editor.setHighlighter(editorHighlighter);
    }

    private static void fillEditorSettings(final EditorSettings editorSettings) {
        editorSettings.setWhitespacesShown(false);
        editorSettings.setLineMarkerAreaShown(true);
        editorSettings.setIndentGuidesShown(true);
        editorSettings.setLineNumbersShown(true);
        editorSettings.setFoldingOutlineShown(false);
        editorSettings.setAdditionalColumnsCount(1);
        editorSettings.setAdditionalLinesCount(1);
        editorSettings.setUseSoftWraps(false);
    }

}
