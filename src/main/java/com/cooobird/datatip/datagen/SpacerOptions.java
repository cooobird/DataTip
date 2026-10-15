package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.content.SpacerContent;

import java.util.Objects;

/**
 * 间隔内容的选项；未设置高度时保留来源值。
 */
public final class SpacerOptions {
    private Integer height;

    public SpacerOptions height(int value) {
        height = value;
        return this;
    }

    public SpacerContent apply(SpacerContent source) {
        Objects.requireNonNull(source, "source");
        return new SpacerContent(height != null ? height : source.height());
    }
}
