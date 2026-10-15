package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.TipContent;
import com.cooobird.datatip.api.condition.ConditionChecker;
import com.cooobird.datatip.api.layout.OverflowPolicy;
import com.cooobird.datatip.api.node.TipModifiers;
import com.cooobird.datatip.api.node.TipNode;

import java.util.List;
import java.util.Objects;

/**
 * 所有内容共享的节点选项；未设置的属性保留来源值。
 */
public final class ModifierOptions {
    private Long width;
    private boolean widthSet;

    public ModifierOptions width(Long value) {
        width = value;
        widthSet = true;
        return this;
    }

    public ModifierOptions width(long value) {
        return width(Long.valueOf(value));
    }

    private Long height;
    private boolean heightSet;

    public ModifierOptions height(Long value) {
        height = value;
        heightSet = true;
        return this;
    }

    public ModifierOptions height(long value) {
        return height(Long.valueOf(value));
    }

    private Long minWidth;
    private boolean minWidthSet;

    public ModifierOptions minWidth(Long value) {
        minWidth = value;
        minWidthSet = true;
        return this;
    }

    public ModifierOptions minWidth(long value) {
        return minWidth(Long.valueOf(value));
    }

    private Long minHeight;
    private boolean minHeightSet;

    public ModifierOptions minHeight(Long value) {
        minHeight = value;
        minHeightSet = true;
        return this;
    }

    public ModifierOptions minHeight(long value) {
        return minHeight(Long.valueOf(value));
    }

    private Long maxWidth;
    private boolean maxWidthSet;

    public ModifierOptions maxWidth(Long value) {
        maxWidth = value;
        maxWidthSet = true;
        return this;
    }

    public ModifierOptions maxWidth(long value) {
        return maxWidth(Long.valueOf(value));
    }

    private Long maxHeight;
    private boolean maxHeightSet;

    public ModifierOptions maxHeight(Long value) {
        maxHeight = value;
        maxHeightSet = true;
        return this;
    }

    public ModifierOptions maxHeight(long value) {
        return maxHeight(Long.valueOf(value));
    }

    private Boolean shift;
    private List<ConditionChecker.Condition> conditions;
    private Long offsetX;
    private Long offsetY;
    private Long offsetZ;
    private TipModifiers.SelfAlignment selfAlignX;
    private TipModifiers.VerticalAlignment selfAlignY;
    private TipModifiers.Margins margins;
    private TipModifiers.SizeConstraints sizeConstraints;
    private Double scaleX;
    private Double scaleY;
    private Double rotation;
    private Double pivotX;
    private Double pivotY;
    private Double opacity;
    private Boolean visible;
    private OverflowPolicy overflow;

    public ModifierOptions shift(boolean value) {
        shift = value;
        return this;
    }

    public ModifierOptions conditions(List<ConditionChecker.Condition> value) {
        conditions = List.copyOf(value);
        return this;
    }

    public ModifierOptions offsetX(long value) {
        offsetX = value;
        return this;
    }

    public ModifierOptions offsetY(long value) {
        offsetY = value;
        return this;
    }

    public ModifierOptions offsetZ(long value) {
        offsetZ = value;
        return this;
    }

    public ModifierOptions selfAlignX(TipModifiers.SelfAlignment value) {
        selfAlignX = Objects.requireNonNull(value, "selfAlignX");
        return this;
    }

    public ModifierOptions selfAlignX(String value) {
        return selfAlignX(TipModifiers.SelfAlignment.valueOf(value.toUpperCase(java.util.Locale.ROOT)));
    }

    public ModifierOptions selfAlignY(TipModifiers.VerticalAlignment value) {
        selfAlignY = Objects.requireNonNull(value, "selfAlignY");
        return this;
    }

    public ModifierOptions selfAlignY(String value) {
        return selfAlignY(TipModifiers.VerticalAlignment.valueOf(value.toUpperCase(java.util.Locale.ROOT)));
    }

    public ModifierOptions margins(TipModifiers.Margins value) {
        margins = Objects.requireNonNull(value, "margins");
        return this;
    }

    public ModifierOptions sizeConstraints(TipModifiers.SizeConstraints value) {
        sizeConstraints = Objects.requireNonNull(value, "sizeConstraints");
        return this;
    }

    public ModifierOptions scaleX(double value) {
        scaleX = value;
        return this;
    }

    public ModifierOptions scaleY(double value) {
        scaleY = value;
        return this;
    }

    public ModifierOptions rotation(double value) {
        rotation = value;
        return this;
    }

    public ModifierOptions pivotX(double value) {
        pivotX = value;
        return this;
    }

    public ModifierOptions pivotY(double value) {
        pivotY = value;
        return this;
    }

    public ModifierOptions opacity(double value) {
        opacity = value;
        return this;
    }

    public ModifierOptions visible(boolean value) {
        visible = value;
        return this;
    }

    public ModifierOptions overflow(OverflowPolicy value) {
        overflow = Objects.requireNonNull(value, "overflow");
        return this;
    }

    public ModifierOptions overflow(String value) {
        return overflow(OverflowPolicy.valueOf(value.toUpperCase(java.util.Locale.ROOT)));
    }

    public ModifierOptions scale(double value) {
        return scaleX(value).scaleY(value);
    }

    public ModifierOptions margins(long top, long right, long bottom, long left) {
        return margins(new TipModifiers.Margins(top, right, bottom, left));
    }

    public ModifierOptions margins(long value) {
        return margins(value, value, value, value);
    }

    public TipModifiers apply(TipModifiers source) {
        Objects.requireNonNull(source, "source");
        TipModifiers.SizeConstraints constraints = sizeConstraints != null ? sizeConstraints : source.sizeConstraints();
        constraints = new TipModifiers.SizeConstraints(
            widthSet ? width : constraints.width(),
            heightSet ? height : constraints.height(),
            minWidthSet ? minWidth : constraints.minWidth(),
            minHeightSet ? minHeight : constraints.minHeight(),
            maxWidthSet ? maxWidth : constraints.maxWidth(),
            maxHeightSet ? maxHeight : constraints.maxHeight()
        );
        return new TipModifiers(
            shift != null ? shift : source.shift(),
            conditions != null ? conditions : source.conditions(),
            offsetX != null ? offsetX : source.offsetX(),
            offsetY != null ? offsetY : source.offsetY(),
            offsetZ != null ? offsetZ : source.offsetZ(),
            selfAlignX != null ? selfAlignX : source.selfAlignX(),
            selfAlignY != null ? selfAlignY : source.selfAlignY(),
            margins != null ? margins : source.margins(),
            constraints,
            scaleX != null ? scaleX : source.scaleX(),
            scaleY != null ? scaleY : source.scaleY(),
            rotation != null ? rotation : source.rotation(),
            pivotX != null ? pivotX : source.pivotX(),
            pivotY != null ? pivotY : source.pivotY(),
            opacity != null ? opacity : source.opacity(),
            visible != null ? visible : source.visible(),
            overflow != null ? overflow : source.overflow()
        );
    }

    public TipModifiers build() {
        return apply(TipModifiers.DEFAULT);
    }

    public TipNode apply(TipContent content) {
        Objects.requireNonNull(content, "content");
        if (content instanceof TipNode node) {
            return TipNode.wrap(node.inner(), apply(node.modifiers()));
        }
        return TipNode.wrap(content, build());
    }
}
