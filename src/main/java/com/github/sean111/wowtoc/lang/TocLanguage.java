package com.github.sean111.wowtoc.lang;

import com.intellij.lang.Language;

public class TocLanguage extends Language {
    public static final TocLanguage INSTANCE = new TocLanguage();

    private TocLanguage() {
        super("TOC");
    }
}
