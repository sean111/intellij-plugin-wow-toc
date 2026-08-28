package com.github.sean111.wowtoc.annotator;

import com.github.sean111.wowtoc.WowTocBundle;
import com.github.sean111.wowtoc.psi.TocRefer;
import com.github.sean111.wowtoc.psi.TocTag;
import com.github.sean111.wowtoc.psi.impl.TocPsiImplUtil;
import com.github.sean111.wowtoc.quickfix.CreateFileQuickFix;
import com.github.sean111.wowtoc.quickfix.RemoveReferQuickFix;
import com.github.sean111.wowtoc.util.TocUtil;
import com.github.sean111.wowtoc.spec.TocSpec;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.regex.Matcher;

public class TocAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (psiElement instanceof TocTag) {
            // Warn when the tag name is missing or is not an official tag name.
            TocTag tag = (TocTag) psiElement;
            TextRange range = TocPsiImplUtil.getKeyRange(tag);
            if (range != null) {
                String tagName = TocPsiImplUtil.getTagName(tag);
                if (!TocSpec.isMetadata(tagName)) {
                    annotationHolder.newAnnotation(HighlightSeverity.WARNING,
                            WowTocBundle.message("inspection.unresolved.tag.name")).range(range).create();
                } else {
                    validateValue(tag, tagName, annotationHolder);
                }
            }
        } else if (psiElement instanceof TocRefer) {
            TocRefer refer = (TocRefer) psiElement;
            String fileName = TocPsiImplUtil.getFileName(refer);
            Set<String> fileNames = TocUtil.getFileNames(psiElement);
            if (fileName != null) {
                validateConditions(refer.getText(), annotationHolder);
                if (refer.getTextLength() > 1024) {
                    annotationHolder.newAnnotation(HighlightSeverity.WARNING,
                            WowTocBundle.message("inspection.line.too.long")).create();
                }
                if (TocSpec.fileVariables(fileName).find()) {
                    return;
                }
                if (!fileName.matches(com.github.sean111.wowtoc.constant.Constants.REGEX_FILE_NAME)) {
                    // Only Lua and XML files can be referenced.
                    annotationHolder.newAnnotation(HighlightSeverity.ERROR,
                            WowTocBundle.message("inspection.unresolved.file.type"))
                            .withFix(new RemoveReferQuickFix(psiElement)).create();
                    // fileNames uses backslashes, so normalize slash-separated paths.
                } else if (!fileNames.contains(fileName.replace("/", "\\"))) {
                    // Offer creation or removal when the referenced file is missing.
                    annotationHolder.newAnnotation(HighlightSeverity.ERROR,
                            WowTocBundle.message("inspection.unresolved.file"))
                            .withFix(new CreateFileQuickFix(fileName))
                            .withFix(new RemoveReferQuickFix(psiElement)).create();
                }
            }
        }
    }

    private static void validateValue(TocTag tag, String tagName, AnnotationHolder holder) {
        String value = TocPsiImplUtil.getTagValue(tag);
        if (value == null) {
            return;
        }
        validateConditions(value, holder);
        String plainValue = TocSpec.stripConditions(value);
        boolean valid = true;
        if (tagName.equalsIgnoreCase("Interface")) {
            valid = plainValue.matches("\\d+(\\s*,\\s*\\d+)*");
        } else if (TocSpec.isBooleanMetadata(tagName)) {
            valid = plainValue.equals("1");
        } else if (tagName.equalsIgnoreCase("DefaultState")) {
            valid = plainValue.equalsIgnoreCase("disabled");
        } else if (tagName.equalsIgnoreCase("AllowLoad")) {
            valid = plainValue.equalsIgnoreCase("both") || plainValue.equalsIgnoreCase("game")
                    || plainValue.equalsIgnoreCase("glue");
        } else if (tagName.equalsIgnoreCase("AllowLoadGameType")) {
            valid = commaSeparatedValuesAreValid(plainValue, TocSpec::isGameType);
        }
        if (!valid) {
            holder.newAnnotation(HighlightSeverity.WARNING, WowTocBundle.message("inspection.invalid.tag.value"))
                    .create();
        }
        if (tag.getTextLength() > 1024) {
            holder.newAnnotation(HighlightSeverity.WARNING, WowTocBundle.message("inspection.line.too.long")).create();
        }
    }

    private static boolean commaSeparatedValuesAreValid(String value, java.util.function.Predicate<String> validator) {
        return !value.isBlank() && java.util.Arrays.stream(value.split(",")).map(String::trim).allMatch(validator);
    }

    private static void validateConditions(String text, AnnotationHolder holder) {
        Matcher matcher = TocSpec.conditions(text);
        while (matcher.find()) {
            String condition = matcher.group(1);
            String value = matcher.group(2) == null ? "" : matcher.group(2);
            if (TocSpec.isFileVariable(condition) && matcher.group(2) == null) {
                continue;
            }
            boolean valid = TocSpec.isCondition(condition);
            if (valid && condition.equalsIgnoreCase("AllowLoadGameType")) {
                valid = commaSeparatedValuesAreValid(value, TocSpec::isGameType);
            } else if (valid && condition.equalsIgnoreCase("AllowLoadTextLocale")) {
                valid = commaSeparatedValuesAreValid(value, TocSpec::isLocale);
            } else if (valid && condition.equalsIgnoreCase("AllowLoad")) {
                valid = value.equalsIgnoreCase("both") || value.equalsIgnoreCase("game") || value.equalsIgnoreCase("glue");
            }
            if (!valid) {
                holder.newAnnotation(HighlightSeverity.WARNING, WowTocBundle.message("inspection.invalid.condition"))
                        .create();
            }
        }
        Matcher variables = TocSpec.fileVariables(text);
        while (variables.find()) {
            String variable = variables.group(1);
            if (!TocSpec.isCondition(variable) && !TocSpec.isFileVariable(variable)) {
                holder.newAnnotation(HighlightSeverity.WARNING, WowTocBundle.message("inspection.invalid.file.variable"))
                        .create();
            }
        }
    }
}
