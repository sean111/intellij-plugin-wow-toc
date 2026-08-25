package com.github.sean111.wowtoc.annotator;

import com.github.sean111.wowtoc.constant.Constants;
import com.github.sean111.wowtoc.WowTocBundle;
import com.github.sean111.wowtoc.psi.TocRefer;
import com.github.sean111.wowtoc.psi.TocTag;
import com.github.sean111.wowtoc.psi.impl.TocPsiImplUtil;
import com.github.sean111.wowtoc.quickfix.CreateFileQuickFix;
import com.github.sean111.wowtoc.quickfix.RemoveReferQuickFix;
import com.github.sean111.wowtoc.util.TocUtil;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TocAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (psiElement instanceof TocTag) {
            // Warn when the tag name is missing or is not an official tag name.
            TocTag tag = (TocTag) psiElement;
            TextRange range = TocPsiImplUtil.getKeyRange(tag);
            if (range != null) {
                String tagName = TocPsiImplUtil.getTagName(tag);
                // Match tag names without regard to case.
                Pattern pattern = Pattern.compile(Constants.REGEX_TAG_NAME, Pattern.CASE_INSENSITIVE);
                if (tagName == null || !pattern.matcher(tagName).matches()) {
                    annotationHolder.newAnnotation(HighlightSeverity.WARNING,
                            WowTocBundle.message("inspection.unresolved.tag.name")).range(range).create();
                }
            }
        } else if (psiElement instanceof TocRefer) {
            TocRefer refer = (TocRefer) psiElement;
            String fileName = TocPsiImplUtil.getFileName(refer);
            Set<String> fileNames = TocUtil.getFileNames(psiElement);
            if (fileName != null) {
                if (!fileName.matches(Constants.REGEX_FILE_NAME)) {
                    // Only Lua and XML files can be referenced.
                    annotationHolder.newAnnotation(HighlightSeverity.ERROR,
                            WowTocBundle.message("inspection.unresolved.file.type"))
                            .withFix(new RemoveReferQuickFix(psiElement)).create();
                    // fileNames uses backslashes, so normalize slash-separated paths.
                } else if (!fileNames.contains(fileName.replaceAll("/", Matcher.quoteReplacement("\\")))) {
                    // Offer creation or removal when the referenced file is missing.
                    annotationHolder.newAnnotation(HighlightSeverity.ERROR,
                            WowTocBundle.message("inspection.unresolved.file"))
                            .withFix(new CreateFileQuickFix(fileName))
                            .withFix(new RemoveReferQuickFix(psiElement)).create();
                }
            }
        }
    }
}
