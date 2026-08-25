package com.github.sean111.wowtoc.psi;

import com.github.sean111.wowtoc.lang.TocLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class TocTokenType extends IElementType {
    public TocTokenType(@NotNull String debugName) {
        super(debugName, TocLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "TocTokenType." + super.toString();
    }
}
