package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.content.ChartContent;
import com.cooobird.datatip.api.text.LocalizedText;

import java.util.Objects;

/**
 * ChartContent.ChartEntry 的可复用选项；未设置的属性保留来源值，apply 创建新内容。
 */
public final class ChartEntryOptions {
    public ChartEntryOptions label(String value) {
        return labelText(value);
    }

    public ChartEntryOptions label(java.util.Map<String, String> value) {
        return labelText(value);
    }

    public ChartEntryOptions label(net.minecraft.network.chat.Component value) {
        return labelText(value);
    }

    private LocalizedText labelText;
    private String valueExpr;
    private Integer color;

    public ChartEntryOptions labelText(LocalizedText value) {
        labelText = Objects.requireNonNull(value, "labelText");
        return this;
    }

    public ChartEntryOptions labelText(String value) {
        return labelText(value == null ? null : com.cooobird.datatip.api.text.LocalizedText.literal(value));
    }

    public ChartEntryOptions labelText(java.util.Map<String, String> value) {
        return labelText(value == null ? null : OptionText.languages(value));
    }

    public ChartEntryOptions labelText(net.minecraft.network.chat.Component value) {
        return labelText(value == null ? null : value instanceof LocalizedText localized ? localized : LocalizedText.component(value));
    }

    public ChartEntryOptions valueExpr(String value) {
        valueExpr = Objects.requireNonNull(value, "valueExpr");
        return this;
    }

    public ChartEntryOptions color(int value) {
        color = value;
        return this;
    }

    public ChartEntryOptions color(String value) {
        return color(com.cooobird.datatip.api.util.ColorParser.parseStrict(value));
    }

    public ChartContent.ChartEntry apply(ChartContent.ChartEntry source) {
        Objects.requireNonNull(source, "source");
        return new ChartContent.ChartEntry(
            labelText != null ? labelText : source.labelText(),
            valueExpr != null ? valueExpr : source.valueExpr(),
            color != null ? color : source.color()
        );
    }
}
