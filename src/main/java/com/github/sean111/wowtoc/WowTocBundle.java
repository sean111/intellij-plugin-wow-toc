package com.github.sean111.wowtoc;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

public final class WowTocBundle extends DynamicBundle {
    private static final WowTocBundle INSTANCE = new WowTocBundle();

    private WowTocBundle() {
        super("messages.WowTocBundle");
    }

    public static @Nls @NotNull String message(@PropertyKey(resourceBundle = "messages.WowTocBundle") @NotNull String key,
                                                Object... params) {
        return INSTANCE.getMessage(key, params);
    }
}
