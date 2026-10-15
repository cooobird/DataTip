package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.content.BaseTextContent;
import com.cooobird.datatip.api.content.TextContent;
import com.cooobird.datatip.api.util.ColorParser;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/**
 * 可自由组合的文本属性。未设置的属性保留来源文本的值；每次应用都会创建新的内容。
 * <pre>{@code
 * translate("tip.example", textOptions().color("gold").italic(true).maxWidth(180));
 * }</pre>
 */
public final class TextOptions {
    private Integer color;
    private String colorExpression;
    private boolean colorExpressionSet;
    private ResourceLocation font;
    private boolean fontSet;
    private Boolean shadow, bold, italic, underlined, strikethrough, shift;
    private BaseTextContent.TextAlign align;
    private Integer lineHeight, maxWidth;

    public TextOptions color(String value) {
        return color(ColorParser.parseStrict(value));
    }

    public TextOptions color(int value) {
        color = value;
        colorExpression = null;
        colorExpressionSet = true;
        return this;
    }

    public TextOptions colorExpression(String value) {
        colorExpression = value;
        colorExpressionSet = true;
        return this;
    }

    public TextOptions clearColorExpression() {
        return colorExpression(null);
    }

    public TextOptions clearFont() {
        return font(null);
    }

    /**
     * 设置字体；传入 null 清除字体。
     */
    public TextOptions font(String value) {
        font = value == null ? null : DatagenJsonUtils.parseResourceLocation(value);
        fontSet = true;
        return this;
    }

    public TextOptions shadow(boolean value) {
        shadow = value;
        return this;
    }

    public TextOptions bold(boolean value) {
        bold = value;
        return this;
    }

    public TextOptions italic(boolean value) {
        italic = value;
        return this;
    }

    public TextOptions underlined(boolean value) {
        underlined = value;
        return this;
    }

    public TextOptions strikethrough(boolean value) {
        strikethrough = value;
        return this;
    }

    public TextOptions shift(boolean value) {
        shift = value;
        return this;
    }

    public TextOptions align(BaseTextContent.TextAlign value) {
        align = Objects.requireNonNull(value);
        return this;
    }

    public TextOptions align(String value) {
        return align(BaseTextContent.TextAlign.valueOf(value.toUpperCase(java.util.Locale.ROOT)));
    }

    public TextOptions lineHeight(int value) {
        lineHeight = value;
        return this;
    }

    /**
     * 最大宽度，0 表示不限制。
     */
    public TextOptions maxWidth(int value) {
        maxWidth = value;
        return this;
    }

    /**
     * 可为现有文本（包括逐语言样式文本）应用任意属性组合。
     */
    public TextContent apply(TextContent source) {
        Objects.requireNonNull(source);
        return new TextContent(source.text(), source.component(), source.formattedText(),
            source.langText(), source.langStyledText(), fontSet ? font : source.font(),
            color != null ? color : source.color(),
            colorExpressionSet ? colorExpression : source.colorExpression(),
            shadow != null ? shadow : source.shadow(), align != null ? align : source.align(),
            lineHeight != null ? lineHeight : source.lineHeight(), maxWidth != null ? maxWidth : source.maxWidth(),
            bold != null ? bold : source.bold(), italic != null ? italic : source.italic(),
            underlined != null ? underlined : source.underlined(),
            strikethrough != null ? strikethrough : source.strikethrough(), shift != null ? shift : source.shift());
    }
}
