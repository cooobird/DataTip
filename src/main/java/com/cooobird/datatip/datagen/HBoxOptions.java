package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.TipContent;
import com.cooobird.datatip.api.content.HBoxContent;

import java.util.List;
import java.util.Objects;

/**
 * HBoxContent 的可复用选项；未设置的属性保留来源值，apply 创建新内容。
 */
public final class HBoxOptions {
    public HBoxOptions align(String value) {
        return verticalAlign(value);
    }

    public HBoxOptions align(HBoxContent.VerticalAlign value) {
        return verticalAlign(value);
    }

    private List<TipContent> children;
    private Integer gap;
    private Integer padding;
    private HBoxContent.VerticalAlign verticalAlign;

    public HBoxOptions children(List<TipContent> value) {
        children = List.copyOf(value);
        return this;
    }

    public HBoxOptions gap(int value) {
        gap = value;
        return this;
    }

    public HBoxOptions padding(int value) {
        padding = value;
        return this;
    }

    public HBoxOptions verticalAlign(HBoxContent.VerticalAlign value) {
        verticalAlign = Objects.requireNonNull(value, "verticalAlign");
        return this;
    }

    public HBoxOptions verticalAlign(String value) {
        return verticalAlign(HBoxContent.VerticalAlign.valueOf(value.toUpperCase(java.util.Locale.ROOT)));
    }

    public HBoxContent apply(HBoxContent source) {
        Objects.requireNonNull(source, "source");
        return new HBoxContent(
            children != null ? children : source.children(),
            gap != null ? gap : source.gap(),
            padding != null ? padding : source.padding(),
            verticalAlign != null ? verticalAlign : source.verticalAlign()
        );
    }
}
