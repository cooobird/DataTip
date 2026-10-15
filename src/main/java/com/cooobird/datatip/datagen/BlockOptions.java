package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.content.BlockContent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;

import java.util.Objects;

/**
 * BlockContent 的可复用选项；未设置的属性保留来源值，apply 创建新内容。
 */
public final class BlockOptions {
    public BlockOptions clearLabel() {
        return label((Component) null);
    }

    private Block block;
    private Integer size;
    private Float rotationSpeed;
    private Boolean autoRotate;
    private Component label;
    private boolean labelSet;
    private Integer offsetX;
    private Integer offsetY;

    public BlockOptions block(Block value) {
        block = Objects.requireNonNull(value, "block");
        return this;
    }

    public BlockOptions size(int value) {
        size = value;
        return this;
    }

    public BlockOptions rotationSpeed(float value) {
        rotationSpeed = value;
        return this;
    }

    public BlockOptions autoRotate(boolean value) {
        autoRotate = value;
        return this;
    }

    public BlockOptions label(Component value) {
        label = value;
        labelSet = true;
        return this;
    }

    public BlockOptions label(String value) {
        return label(value == null ? null : com.cooobird.datatip.api.text.LocalizedText.literal(value));
    }

    public BlockOptions label(java.util.Map<String, String> value) {
        return label(value == null ? null : OptionText.languages(value));
    }

    public BlockOptions offsetX(int value) {
        offsetX = value;
        return this;
    }

    public BlockOptions offsetY(int value) {
        offsetY = value;
        return this;
    }

    public BlockContent apply(BlockContent source) {
        Objects.requireNonNull(source, "source");
        return new BlockContent(
            block != null ? block : source.block(),
            size != null ? size : source.size(),
            rotationSpeed != null ? rotationSpeed : source.rotationSpeed(),
            autoRotate != null ? autoRotate : source.autoRotate(),
            labelSet ? label : source.label(),
            offsetX != null ? offsetX : source.offsetX(),
            offsetY != null ? offsetY : source.offsetY()
        );
    }
}
