package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.content.ItemContent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

/**
 * ItemContent 的可复用选项；未设置的属性保留来源值，apply 创建新内容。
 */
public final class ItemOptions {
    public ItemOptions clearLabel() {
        return label((Component) null);
    }

    public ItemOptions clearLabelColor() {
        return labelColor((Integer) null);
    }

    private ItemStack stack;
    private Integer size;
    private Boolean showCount;
    private Boolean showDurability;
    private Boolean showLabel;
    private Component label;
    private boolean labelSet;
    private Integer labelColor;
    private boolean labelColorSet;
    private Integer offsetX;
    private Integer offsetY;
    private Integer count;

    public ItemOptions stack(ItemStack value) {
        stack = Objects.requireNonNull(value, "stack");
        return this;
    }

    public ItemOptions size(int value) {
        size = value;
        return this;
    }

    public ItemOptions showCount(boolean value) {
        showCount = value;
        return this;
    }

    public ItemOptions showDurability(boolean value) {
        showDurability = value;
        return this;
    }

    public ItemOptions showLabel(boolean value) {
        showLabel = value;
        return this;
    }

    public ItemOptions label(Component value) {
        label = value;
        labelSet = true;
        return this;
    }

    public ItemOptions label(String value) {
        return label(value == null ? null : com.cooobird.datatip.api.text.LocalizedText.literal(value));
    }

    public ItemOptions label(java.util.Map<String, String> value) {
        return label(value == null ? null : OptionText.languages(value));
    }

    public ItemOptions labelColor(Integer value) {
        labelColor = value;
        labelColorSet = true;
        return this;
    }

    public ItemOptions labelColor(String value) {
        return labelColor(value == null ? null : com.cooobird.datatip.api.util.ColorParser.parseStrict(value));
    }

    public ItemOptions offsetX(int value) {
        offsetX = value;
        return this;
    }

    public ItemOptions offsetY(int value) {
        offsetY = value;
        return this;
    }

    public ItemOptions count(int value) {
        count = value;
        return this;
    }

    public ItemContent apply(ItemContent source) {
        Objects.requireNonNull(source, "source");
        ItemStack resolvedStack = (stack != null ? stack : source.stack()).copy();
        if (count != null) resolvedStack.setCount(count);
        return new ItemContent(
            resolvedStack,
            size != null ? size : source.size(),
            showCount != null ? showCount : source.showCount(),
            showDurability != null ? showDurability : source.showDurability(),
            showLabel != null ? showLabel : source.showLabel(),
            labelSet ? label : source.label(),
            labelColorSet ? labelColor : source.labelColor(),
            offsetX != null ? offsetX : source.offsetX(),
            offsetY != null ? offsetY : source.offsetY()
        );
    }
}
