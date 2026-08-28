package com.github.sean111.wowtoc.completion;

import com.github.sean111.wowtoc.constant.Constants;
import com.github.sean111.wowtoc.lang.TocLanguage;
import com.github.sean111.wowtoc.psi.TocTypes;
import com.github.sean111.wowtoc.util.TocUtil;
import com.github.sean111.wowtoc.spec.TocSpec;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class TocCompletionContributor extends CompletionContributor {
    public TocCompletionContributor() {
        // Complete official tag names while entering a tag name.
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(TocTypes.TAG_NAME).withLanguage(TocLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters completionParameters,
                                                  @NotNull ProcessingContext processingContext,
                                                  @NotNull CompletionResultSet completionResultSet) {
                        for (String tagName : Constants.TAG_NAMES) {
                            completionResultSet.addElement(LookupElementBuilder.create(tagName));
                        }
                        completionResultSet.addElement(LookupElementBuilder.create("RequiredDep"));
                        completionResultSet.addElement(LookupElementBuilder.create("RequiredDeps"));
                        PsiElement psiElement = completionParameters.getOriginalPosition();
                        if (psiElement != null) {
                            String text = psiElement.getText();
                            if (text.startsWith("Title") || text.startsWith("Notes") || text.startsWith("Category")) {
                                int separator = text.indexOf('-');
                                String baseName = separator >= 0 ? text.substring(0, separator) : text;
                                for (String localization : Constants.LOCALIZATION) {
                                    completionResultSet.addElement(LookupElementBuilder.create(baseName + "-" + localization));
                                }
                            }
                        }
                    }
                });

        // Complete Lua and XML files in the current directory and its subdirectories.
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(TocTypes.FILE_NAME).withLanguage(TocLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters completionParameters,
                                                  @NotNull ProcessingContext processingContext,
                                                  @NotNull CompletionResultSet completionResultSet) {
                        PsiElement psiElement = completionParameters.getOriginalPosition();
                        if (psiElement != null) {
                            Set<String> fileNames = TocUtil.getFileNames(psiElement);
                            for (String fileName : fileNames) {
                                completionResultSet.addElement(LookupElementBuilder.create(fileName));
                            }
                            for (String variable : TocSpec.FILE_VARIABLES) {
                                completionResultSet.addElement(LookupElementBuilder.create("[" + variable + "]"));
                            }
                            for (String condition : TocSpec.CONDITIONS) {
                                completionResultSet.addElement(LookupElementBuilder.create("[" + condition + " ]"));
                            }
                        }
                    }
                }
        );

        extend(CompletionType.BASIC, PlatformPatterns.psiElement(TocTypes.TAG_VALUE).withLanguage(TocLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters,
                                                  @NotNull ProcessingContext context,
                                                  @NotNull CompletionResultSet result) {
                        PsiElement element = parameters.getOriginalPosition();
                        String line = element == null ? "" : element.getParent().getText();
                        if (line.matches("(?i).*AllowLoadGameType.*")) {
                            addAll(result, TocSpec.GAME_TYPES);
                        } else if (line.matches("(?i).*AllowLoadTextLocale.*")) {
                            addAll(result, TocSpec.LOCALES);
                        } else if (line.matches("(?i).*AllowLoad.*")) {
                            addAll(result, new String[]{"Both", "Game", "Glue"});
                        } else if (line.matches("(?i).*DefaultState.*")) {
                            addAll(result, new String[]{"disabled"});
                        }
                    }
                });
    }

    private static void addAll(CompletionResultSet result, String[] values) {
        for (String value : values) {
            result.addElement(LookupElementBuilder.create(value));
        }
    }
}
