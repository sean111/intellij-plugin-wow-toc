package com.github.sean111.wowtoc.codestyle;

import com.github.sean111.wowtoc.constant.Constants;
import com.github.sean111.wowtoc.lang.TocLanguage;
import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TocLanguageCodeStyleSettingsProvider extends LanguageCodeStyleSettingsProvider {

    @Override
    public void customizeSettings(@NotNull CodeStyleSettingsCustomizable consumer, @NotNull SettingsType settingsType) {
        if (settingsType == SettingsType.SPACING_SETTINGS) {
            consumer.showCustomOption(TocCodeStyleSettings.class, "spaceBetweenTagPrefixAndTagName",
                    "Between tag prefix and tag name", "Other");
            consumer.showCustomOption(TocCodeStyleSettings.class, "spaceBetweenTagNameAndSeparator",
                    "Between tag name and separator", "Other");
            consumer.showCustomOption(TocCodeStyleSettings.class, "spaceBetweenSeparatorAndTagValue",
                    "Between separator and tag value", "Other");
        } else if (settingsType == SettingsType.BLANK_LINES_SETTINGS) {
            consumer.showCustomOption(TocCodeStyleSettings.class, "keepBlankLinesInTag", "In tag",
                    "Keep Maximum Blank Lines");
            consumer.showCustomOption(TocCodeStyleSettings.class, "keepBlankLinesInRefer", "In refer",
                    "Keep Maximum Blank Lines");
            consumer.showCustomOption(TocCodeStyleSettings.class, "keepBlankLinesBetweenTagAndRefer",
                    "Between tag and refer", "Keep Maximum Blank Lines");
            consumer.showCustomOption(TocCodeStyleSettings.class, "blankLinesInTag", "In tag",
                    "Minimum Blank Lines");
            consumer.showCustomOption(TocCodeStyleSettings.class, "blankLinesInRefer", "In refer",
                    "Minimum Blank Lines");
            consumer.showCustomOption(TocCodeStyleSettings.class, "blankLinesBetweenTagAndRefer",
                    "Between tag and refer", "Minimum Blank Lines");
        } else if (settingsType == SettingsType.COMMENTER_SETTINGS) {
            consumer.showStandardOptions("LINE_COMMENT_AT_FIRST_COLUMN", "LINE_COMMENT_ADD_SPACE");
        }
    }

    @Nullable
    @Override
    public String getCodeSample(@NotNull SettingsType settingsType) {
        return Constants.DEMO_TEXT;
    }

    @NotNull
    @Override
    public Language getLanguage() {
        return TocLanguage.INSTANCE;
    }

    @Override
    protected void customizeDefaults(@NotNull CommonCodeStyleSettings commonSettings,
                                     @NotNull CommonCodeStyleSettings.IndentOptions indentOptions) {
        commonSettings.LINE_COMMENT_AT_FIRST_COLUMN = false;
        commonSettings.LINE_COMMENT_ADD_SPACE = true;
    }
}
