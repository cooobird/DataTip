package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.content.DividerContent;

import java.util.Objects;

/**
 * DividerContent 的可复用选项；未设置的属性保留来源值，apply 创建新内容。
 */
public final class DividerOptions {
    private Integer color;
    private Integer thickness;
    private Integer width;
    private Integer marginTop;
    private Integer marginBottom;
    private DividerContent.DividerStyle style;
    private DividerContent.WidthMode widthMode;

    public DividerOptions color(int value) {
        color = value;
        return this;
    }

    public DividerOptions color(String value) {
        return color(com.cooobird.datatip.api.util.ColorParser.parseStrict(value));
    }

    public DividerOptions thickness(int value) {
        thickness = value;
        return this;
    }

    public DividerOptions width(int value) {
        width = value;
        return this;
    }

    public DividerOptions marginTop(int value) {
        marginTop = value;
        return this;
    }

    public DividerOptions marginBottom(int value) {
        marginBottom = value;
        return this;
    }

    public DividerOptions style(DividerContent.DividerStyle value) {
        style = Objects.requireNonNull(value, "style");
        return this;
    }

    public DividerOptions style(String value) {
        return style(DividerContent.DividerStyle.valueOf(value.toUpperCase(java.util.Locale.ROOT)));
    }

    public DividerOptions widthMode(DividerContent.WidthMode value) {
        widthMode = Objects.requireNonNull(value, "widthMode");
        return this;
    }

    public DividerOptions widthMode(String value) {
        return widthMode(DividerContent.WidthMode.valueOf(value.toUpperCase(java.util.Locale.ROOT)));
    }

    public DividerContent apply(DividerContent source) {
        Objects.requireNonNull(source, "source");
        return new DividerContent(
            color != null ? color : source.color(),
            thickness != null ? thickness : source.thickness(),
            width != null ? width : source.width(),
            marginTop != null ? marginTop : source.marginTop(),
            marginBottom != null ? marginBottom : source.marginBottom(),
            style != null ? style : source.style(),
            widthMode != null ? widthMode : source.widthMode()
        );
    }
}
