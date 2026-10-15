package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.content.ChartContent;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Objects;

/**
 * ChartContent 的可复用选项；未设置的属性保留来源值，apply 创建新内容。
 */
public final class ChartOptions {
    public ChartOptions clearTitle() {
        return title((Component) null);
    }

    private ChartContent.ChartType type;
    private List<ChartContent.ChartEntry> entries;
    private Integer width;
    private Integer height;
    private Component title;
    private boolean titleSet;
    private Boolean showLabels;
    private Boolean showValues;
    private Integer titleColor;
    private Integer labelColor;
    private Integer valueColor;
    private Integer zeroLineColor;

    public ChartOptions type(ChartContent.ChartType value) {
        type = Objects.requireNonNull(value, "type");
        return this;
    }

    public ChartOptions type(String value) {
        return type(ChartContent.ChartType.valueOf(value.toUpperCase(java.util.Locale.ROOT)));
    }

    public ChartOptions entries(List<ChartContent.ChartEntry> value) {
        entries = List.copyOf(value);
        return this;
    }

    public ChartOptions width(int value) {
        width = value;
        return this;
    }

    public ChartOptions height(int value) {
        height = value;
        return this;
    }

    public ChartOptions title(Component value) {
        title = value;
        titleSet = true;
        return this;
    }

    public ChartOptions title(String value) {
        return title(value == null ? null : com.cooobird.datatip.api.text.LocalizedText.literal(value));
    }

    public ChartOptions title(java.util.Map<String, String> value) {
        return title(value == null ? null : OptionText.languages(value));
    }

    public ChartOptions showLabels(boolean value) {
        showLabels = value;
        return this;
    }

    public ChartOptions showValues(boolean value) {
        showValues = value;
        return this;
    }

    public ChartOptions titleColor(int value) {
        titleColor = value;
        return this;
    }

    public ChartOptions titleColor(String value) {
        return titleColor(com.cooobird.datatip.api.util.ColorParser.parseStrict(value));
    }

    public ChartOptions labelColor(int value) {
        labelColor = value;
        return this;
    }

    public ChartOptions labelColor(String value) {
        return labelColor(com.cooobird.datatip.api.util.ColorParser.parseStrict(value));
    }

    public ChartOptions valueColor(int value) {
        valueColor = value;
        return this;
    }

    public ChartOptions valueColor(String value) {
        return valueColor(com.cooobird.datatip.api.util.ColorParser.parseStrict(value));
    }

    public ChartOptions zeroLineColor(int value) {
        zeroLineColor = value;
        return this;
    }

    public ChartOptions zeroLineColor(String value) {
        return zeroLineColor(com.cooobird.datatip.api.util.ColorParser.parseStrict(value));
    }

    public ChartContent apply(ChartContent source) {
        Objects.requireNonNull(source, "source");
        return new ChartContent(
            type != null ? type : source.type(),
            entries != null ? entries : source.entries(),
            width != null ? width : source.width(),
            height != null ? height : source.height(),
            titleSet ? title : source.title(),
            showLabels != null ? showLabels : source.showLabels(),
            showValues != null ? showValues : source.showValues(),
            titleColor != null ? titleColor : source.titleColor(),
            labelColor != null ? labelColor : source.labelColor(),
            valueColor != null ? valueColor : source.valueColor(),
            zeroLineColor != null ? zeroLineColor : source.zeroLineColor()
        );
    }
}
