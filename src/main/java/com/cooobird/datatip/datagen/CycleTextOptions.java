package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.content.CyclingTextContent;
import com.cooobird.datatip.api.content.TextContent;

import java.util.List;
import java.util.Objects;

/**
 * CyclingTextContent 的可复用选项；未设置的属性保留来源值，apply 创建新内容。
 */
public final class CycleTextOptions {
    private TextOptions textOptions;
    private TextContent text;
    private List<Integer> colors;
    private Double cycleSeconds;
    private CyclingTextContent.Transition transition;
    private Double phase;

    public CycleTextOptions text(TextContent value) {
        text = Objects.requireNonNull(value, "text");
        return this;
    }

    public CycleTextOptions colors(List<Integer> value) {
        colors = List.copyOf(value);
        return this;
    }

    public CycleTextOptions cycleSeconds(double value) {
        cycleSeconds = value;
        return this;
    }

    public CycleTextOptions transition(CyclingTextContent.Transition value) {
        transition = Objects.requireNonNull(value, "transition");
        return this;
    }

    public CycleTextOptions transition(String value) {
        return transition(CyclingTextContent.Transition.valueOf(value.toUpperCase(java.util.Locale.ROOT)));
    }

    public CycleTextOptions phase(double value) {
        phase = value;
        return this;
    }

    public CycleTextOptions colors(String... values) {
        return colors(java.util.Arrays.stream(values).map(com.cooobird.datatip.api.util.ColorParser::parseStrict).toList());
    }

    public CycleTextOptions text(TextOptions options) {
        textOptions = Objects.requireNonNull(options);
        return this;
    }

    public CyclingTextContent apply(CyclingTextContent source) {
        Objects.requireNonNull(source, "source");
        TextContent resolvedText = text != null ? text : source.textContent();
        if (textOptions != null) resolvedText = textOptions.apply(resolvedText);
        return new CyclingTextContent(
            resolvedText,
            colors != null ? colors : source.colors(),
            cycleSeconds != null ? cycleSeconds : source.cycleSeconds(),
            transition != null ? transition : source.transition(),
            phase != null ? phase : source.phase()
        );
    }
}
