package com.github.sean111.wowtoc.psi;

import com.github.sean111.wowtoc.lang.TocLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class TocElementType extends IElementType {
    public TocElementType(@NotNull String debugName) {
        super(debugName, TocLanguage.INSTANCE);
    }
}
