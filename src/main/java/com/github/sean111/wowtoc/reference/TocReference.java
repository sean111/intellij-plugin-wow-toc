package com.github.sean111.wowtoc.reference;

import com.github.sean111.wowtoc.constant.Constants;
import com.github.sean111.wowtoc.psi.TocRefer;
import com.github.sean111.wowtoc.util.TocUtil;
import com.github.sean111.wowtoc.spec.TocSpec;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TocReference extends PsiReferenceBase {
    public TocReference(@NotNull PsiElement element, TextRange rangeInElement) {
        super(element, rangeInElement);
    }

    /**
     * Resolves the referenced file.
     *
     * @return the referenced element
     */
    @Nullable
    @Override
    public PsiElement resolve() {
        if (myElement instanceof TocRefer) {
            PsiDirectory directory = myElement.getContainingFile().getParent();
            String fileName = ((TocRefer) myElement).getFileName();
            if (fileName != null && !TocSpec.fileVariables(fileName).find() && fileName.matches(Constants.REGEX_FILE_NAME)) {
                directory = TocUtil.getDirectory(directory, fileName, false);
                fileName = TocUtil.getFileName(fileName);
                if (directory != null) {
                    return directory.findFile(fileName);
                }
            }
        }
        return null;
    }

    /**
     * Renames the referenced element.
     *
     * @param newElementName the new name
     * @return the renamed element
     * @throws IncorrectOperationException if the rename cannot be completed
     */
    @Override
    public PsiElement handleElementRename(@NotNull String newElementName) throws IncorrectOperationException {
        if (myElement instanceof TocRefer) {
            return ((TocRefer) myElement).setName(newElementName);
        }
        return null;
    }
}
