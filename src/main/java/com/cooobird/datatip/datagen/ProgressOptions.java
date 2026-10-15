package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.content.ProgressContent;
import net.minecraft.network.chat.Component;

import java.util.Objects;

/**
 * ProgressContent 的可复用选项；未设置的属性保留来源值，apply 创建新内容。
 */
public final class ProgressOptions {
    public ProgressOptions clearColorFgLight() {
        return colorFgLight((Integer) null);
    }

    public ProgressOptions clearColorBgDark() {
        return colorBgDark((Integer) null);
    }

    public ProgressOptions clearCustomLabel() {
        return customLabel((Component) null);
    }

    public ProgressOptions label(String value) {
        return customLabel(value);
    }

    public ProgressOptions label(java.util.Map<String, String> value) {
        return customLabel(value);
    }

    public ProgressOptions label(Component value) {
        return customLabel(value);
    }

    private Float progress;
    private Integer width;
    private Integer height;
    private Integer colorFg;
    private Integer colorBg;
    private Integer colorFgLight;
    private boolean colorFgLightSet;
    private Integer colorBgDark;
    private boolean colorBgDarkSet;
    private ProgressContent.ProgressStyle style;
    private Boolean showLabel;
    private Component customLabel;
    private boolean customLabelSet;
    private ProgressContent.LabelAlign labelAlign;
    private Boolean animated;
    private Integer animSpeed;

    public ProgressOptions progress(float value) {
        progress = value;
        return this;
    }

    public ProgressOptions width(int value) {
        width = value;
        return this;
    }

    public ProgressOptions height(int value) {
        height = value;
        return this;
    }

    public ProgressOptions colorFg(int value) {
        colorFg = value;
        return this;
    }

    public ProgressOptions colorFg(String value) {
        return colorFg(com.cooobird.datatip.api.util.ColorParser.parseStrict(value));
    }

    public ProgressOptions colorBg(int value) {
        colorBg = value;
        return this;
    }

    public ProgressOptions colorBg(String value) {
        return colorBg(com.cooobird.datatip.api.util.ColorParser.parseStrict(value));
    }

    public ProgressOptions colorFgLight(Integer value) {
        colorFgLight = value;
        colorFgLightSet = true;
        return this;
    }

    public ProgressOptions colorFgLight(String value) {
        return colorFgLight(value == null ? null : com.cooobird.datatip.api.util.ColorParser.parseStrict(value));
    }

    public ProgressOptions colorBgDark(Integer value) {
        colorBgDark = value;
        colorBgDarkSet = true;
        return this;
    }

    public ProgressOptions colorBgDark(String value) {
        return colorBgDark(value == null ? null : com.cooobird.datatip.api.util.ColorParser.parseStrict(value));
    }

    public ProgressOptions style(ProgressContent.ProgressStyle value) {
        style = Objects.requireNonNull(value, "style");
        return this;
    }

    public ProgressOptions style(String value) {
        return style(ProgressContent.ProgressStyle.valueOf(value.toUpperCase(java.util.Locale.ROOT)));
    }

    public ProgressOptions showLabel(boolean value) {
        showLabel = value;
        return this;
    }

    public ProgressOptions customLabel(Component value) {
        customLabel = value;
        customLabelSet = true;
        return this;
    }

    public ProgressOptions customLabel(String value) {
        return customLabel(value == null ? null : com.cooobird.datatip.api.text.LocalizedText.literal(value));
    }

    public ProgressOptions customLabel(java.util.Map<String, String> value) {
        return customLabel(value == null ? null : OptionText.languages(value));
    }

    public ProgressOptions labelAlign(ProgressContent.LabelAlign value) {
        labelAlign = Objects.requireNonNull(value, "labelAlign");
        return this;
    }

    public ProgressOptions labelAlign(String value) {
        return labelAlign(ProgressContent.LabelAlign.valueOf(value.toUpperCase(java.util.Locale.ROOT)));
    }

    public ProgressOptions animated(boolean value) {
        animated = value;
        return this;
    }

    public ProgressOptions animSpeed(int value) {
        animSpeed = value;
        return this;
    }

    public ProgressContent apply(ProgressContent source) {
        Objects.requireNonNull(source, "source");
        ProgressContent.ProgressStyle resolvedStyle = style != null ? style : source.style();
        boolean resolvedAnimated = animated != null ? animated
            : style != null ? style == ProgressContent.ProgressStyle.ANIMATED : source.animated();
        if (Boolean.FALSE.equals(animated) && style == null
            && resolvedStyle == ProgressContent.ProgressStyle.ANIMATED) {
            resolvedStyle = ProgressContent.ProgressStyle.GRADIENT;
        }
        return new ProgressContent(
            progress != null ? progress : source.progress(),
            width != null ? width : source.width(),
            height != null ? height : source.height(),
            colorFg != null ? colorFg : source.colorFg(),
            colorBg != null ? colorBg : source.colorBg(),
            colorFgLightSet ? colorFgLight : source.colorFgLight(),
            colorBgDarkSet ? colorBgDark : source.colorBgDark(),
            resolvedStyle,
            showLabel != null ? showLabel : source.showLabel(),
            customLabelSet ? customLabel : source.customLabel(),
            labelAlign != null ? labelAlign : source.labelAlign(),
            resolvedAnimated,
            animSpeed != null ? animSpeed : source.animSpeed()
        );
    }
}
