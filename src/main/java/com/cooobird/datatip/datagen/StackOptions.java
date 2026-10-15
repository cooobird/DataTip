package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.TipContent;
import com.cooobird.datatip.api.content.StackContent;

import java.util.List;
import java.util.Objects;

/**
 * StackContent 的可复用选项；未设置的属性保留来源值，apply 创建新内容。
 */
public final class StackOptions {
    private List<TipContent> children;
    private Integer padding;
    private StackContent.HorizontalAlign horizontalAlign;
    private StackContent.VerticalAlign verticalAlign;

    public StackOptions children(List<TipContent> value) {
        children = List.copyOf(value);
        return this;
    }

    public StackOptions padding(int value) {
        padding = value;
        return this;
    }

    public StackOptions horizontalAlign(StackContent.HorizontalAlign value) {
        horizontalAlign = Objects.requireNonNull(value, "horizontalAlign");
        return this;
    }

    public StackOptions horizontalAlign(String value) {
        return horizontalAlign(StackContent.HorizontalAlign.valueOf(value.toUpperCase(java.util.Locale.ROOT)));
    }

    public StackOptions verticalAlign(StackContent.VerticalAlign value) {
        verticalAlign = Objects.requireNonNull(value, "verticalAlign");
        return this;
    }

    public StackOptions verticalAlign(String value) {
        return verticalAlign(StackContent.VerticalAlign.valueOf(value.toUpperCase(java.util.Locale.ROOT)));
    }

    public StackContent apply(StackContent source) {
        Objects.requireNonNull(source, "source");
        return new StackContent(
            children != null ? children : source.children(),
            padding != null ? padding : source.padding(),
            horizontalAlign != null ? horizontalAlign : source.horizontalAlign(),
            verticalAlign != null ? verticalAlign : source.verticalAlign()
        );
    }
}
