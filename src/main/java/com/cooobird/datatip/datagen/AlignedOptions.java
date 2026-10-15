package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.TipContent;
import com.cooobird.datatip.api.content.AlignedContent;
import com.cooobird.datatip.api.content.VBoxContent;

import java.util.Objects;

/**
 * AlignedContent 的可复用选项；未设置的属性保留来源值，apply 创建新内容。
 */
public final class AlignedOptions {
    private TipContent content;
    private VBoxContent.HorizontalAlign align;

    public AlignedOptions content(TipContent value) {
        content = Objects.requireNonNull(value, "content");
        return this;
    }

    public AlignedOptions align(VBoxContent.HorizontalAlign value) {
        align = Objects.requireNonNull(value, "align");
        return this;
    }

    public AlignedOptions align(String value) {
        return align(VBoxContent.HorizontalAlign.valueOf(value.toUpperCase(java.util.Locale.ROOT)));
    }

    public AlignedContent apply(AlignedContent source) {
        Objects.requireNonNull(source, "source");
        return new AlignedContent(
            content != null ? content : source.inner(),
            align != null ? align : source.align()
        );
    }
}
