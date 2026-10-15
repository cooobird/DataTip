package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.TipContent;
import com.cooobird.datatip.api.content.VBoxContent;

import java.util.List;
import java.util.Objects;

/**
 * VBoxContent 的可复用选项；未设置的属性保留来源值，apply 创建新内容。
 */
public final class VBoxOptions {
    public VBoxOptions align(String value) {
        return horizontalAlign(value);
    }

    public VBoxOptions align(VBoxContent.HorizontalAlign value) {
        return horizontalAlign(value);
    }

    private List<TipContent> children;
    private Integer gap;
    private Integer padding;
    private VBoxContent.HorizontalAlign horizontalAlign;

    public VBoxOptions children(List<TipContent> value) {
        children = List.copyOf(value);
        return this;
    }

    public VBoxOptions gap(int value) {
        gap = value;
        return this;
    }

    public VBoxOptions padding(int value) {
        padding = value;
        return this;
    }

    public VBoxOptions horizontalAlign(VBoxContent.HorizontalAlign value) {
        horizontalAlign = Objects.requireNonNull(value, "horizontalAlign");
        return this;
    }

    public VBoxOptions horizontalAlign(String value) {
        return horizontalAlign(VBoxContent.HorizontalAlign.valueOf(value.toUpperCase(java.util.Locale.ROOT)));
    }

    public VBoxContent apply(VBoxContent source) {
        Objects.requireNonNull(source, "source");
        return new VBoxContent(
            children != null ? children : source.children(),
            gap != null ? gap : source.gap(),
            padding != null ? padding : source.padding(),
            horizontalAlign != null ? horizontalAlign : source.horizontalAlign()
        );
    }
}
