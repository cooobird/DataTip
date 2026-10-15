package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.content.BaseTextContent;
import com.cooobird.datatip.api.content.TypewriterContent;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * TypewriterContent 的可复用选项；未设置的属性保留来源值，apply 创建新内容。
 */
public final class TypewriterOptions {
    public TypewriterOptions clearLangLines() {
        return langLines((Map<String, List<String>>) null);
    }

    public TypewriterOptions clearLangStyledLines() {
        return langStyledLines((Map<String, List<BaseTextContent.LangStyle>>) null);
    }

    public TypewriterOptions clearColorExpression() {
        return colorExpression((String) null);
    }

    public TypewriterOptions clearFont() {
        return font((ResourceLocation) null);
    }

    private List<String> lines;
    private Map<String, List<String>> langLines;
    private boolean langLinesSet;
    private Map<String, List<BaseTextContent.LangStyle>> langStyledLines;
    private boolean langStyledLinesSet;
    private Integer charsPerSecond;
    private Integer pauseSeconds;
    private Boolean loop;
    private Integer color;
    private String colorExpression;
    private boolean colorExpressionSet;
    private ResourceLocation font;
    private boolean fontSet;
    private Boolean bold;
    private Boolean italic;
    private Boolean underlined;
    private Boolean strikethrough;
    private BaseTextContent.TextAlign align;
    private Boolean shadow;
    private Integer lineHeight;
    private Boolean shift;

    public TypewriterOptions lines(List<String> value) {
        lines = List.copyOf(value);
        return this;
    }

    public TypewriterOptions langLines(Map<String, List<String>> value) {
        langLines = value == null ? null : value.entrySet().stream().collect(java.util.stream.Collectors.toUnmodifiableMap(Map.Entry::getKey, entry -> List.copyOf(entry.getValue())));
        langLinesSet = true;
        return this;
    }

    public TypewriterOptions langStyledLines(Map<String, List<BaseTextContent.LangStyle>> value) {
        langStyledLines = value == null ? null : value.entrySet().stream().collect(java.util.stream.Collectors.toUnmodifiableMap(Map.Entry::getKey, entry -> List.copyOf(entry.getValue())));
        langStyledLinesSet = true;
        return this;
    }

    public TypewriterOptions charsPerSecond(int value) {
        charsPerSecond = value;
        return this;
    }

    public TypewriterOptions pauseSeconds(int value) {
        pauseSeconds = value;
        return this;
    }

    public TypewriterOptions loop(boolean value) {
        loop = value;
        return this;
    }

    public TypewriterOptions color(int value) {
        color = value;
        colorExpression = null;
        colorExpressionSet = true;
        return this;
    }

    public TypewriterOptions color(String value) {
        return color(com.cooobird.datatip.api.util.ColorParser.parseStrict(value));
    }

    public TypewriterOptions colorExpression(String value) {
        colorExpression = value;
        colorExpressionSet = true;
        return this;
    }

    public TypewriterOptions font(ResourceLocation value) {
        font = value;
        fontSet = true;
        return this;
    }

    public TypewriterOptions font(String value) {
        return font(value == null ? null : ResourceLocation.parse(value));
    }

    public TypewriterOptions bold(boolean value) {
        bold = value;
        return this;
    }

    public TypewriterOptions italic(boolean value) {
        italic = value;
        return this;
    }

    public TypewriterOptions underlined(boolean value) {
        underlined = value;
        return this;
    }

    public TypewriterOptions strikethrough(boolean value) {
        strikethrough = value;
        return this;
    }

    public TypewriterOptions align(BaseTextContent.TextAlign value) {
        align = Objects.requireNonNull(value, "align");
        return this;
    }

    public TypewriterOptions align(String value) {
        return align(BaseTextContent.TextAlign.valueOf(value.toUpperCase(java.util.Locale.ROOT)));
    }

    public TypewriterOptions shadow(boolean value) {
        shadow = value;
        return this;
    }

    public TypewriterOptions lineHeight(int value) {
        lineHeight = value;
        return this;
    }

    public TypewriterOptions shift(boolean value) {
        shift = value;
        return this;
    }

    public TypewriterContent apply(TypewriterContent source) {
        Objects.requireNonNull(source, "source");
        return new TypewriterContent(
            lines != null ? lines : source.getLines(),
            langLinesSet ? langLines : source.getLangLines(),
            langStyledLinesSet ? langStyledLines : source.getLangStyledLines(),
            charsPerSecond != null ? charsPerSecond : source.getCharsPerSecond(),
            pauseSeconds != null ? pauseSeconds : source.getPauseSeconds(),
            loop != null ? loop : source.isLoop(),
            color != null ? color : source.color(),
            colorExpressionSet ? colorExpression : source.colorExpression(),
            fontSet ? font : source.font(),
            bold != null ? bold : source.bold(),
            italic != null ? italic : source.italic(),
            underlined != null ? underlined : source.underlined(),
            strikethrough != null ? strikethrough : source.strikethrough(),
            align != null ? align : source.align(),
            shadow != null ? shadow : source.shadow(),
            lineHeight != null ? lineHeight : source.lineHeight(),
            shift != null ? shift : source.shift()
        );
    }
}
