package com.cooobird.datatip.data;

import org.jetbrains.annotations.Nullable;

public record TooltipLine(
    String text,
    @Nullable String color,
    @Nullable Boolean bold,
    @Nullable Boolean italic,
    @Nullable Boolean underlined,
    @Nullable Boolean strikethrough,
    @Nullable String font
) {
    public static TooltipLine of(String plain) {
        return new TooltipLine(plain, null, null, null, null, null, null);
    }
}
