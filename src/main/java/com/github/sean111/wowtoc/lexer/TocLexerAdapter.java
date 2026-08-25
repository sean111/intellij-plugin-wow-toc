package com.github.sean111.wowtoc.lexer;

import com.intellij.lexer.FlexAdapter;

public class TocLexerAdapter extends FlexAdapter {
    public TocLexerAdapter() {
        super(new TocLexer(null));
    }
}
