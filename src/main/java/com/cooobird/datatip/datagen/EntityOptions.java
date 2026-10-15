package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.content.EntityContent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;

import java.util.Objects;

/**
 * EntityContent 的可复用选项；未设置的属性保留来源值，apply 创建新内容。
 */
public final class EntityOptions {
    public EntityOptions clearLabel() {
        return label((Component) null);
    }

    private EntityType<?> entityType;
    private Integer size;
    private Float rotationSpeed;
    private Boolean autoRotate;
    private Integer offsetX;
    private Integer offsetY;
    private Component label;
    private boolean labelSet;

    public EntityOptions entityType(EntityType<?> value) {
        entityType = Objects.requireNonNull(value, "entityType");
        return this;
    }

    public EntityOptions size(int value) {
        size = value;
        return this;
    }

    public EntityOptions rotationSpeed(float value) {
        rotationSpeed = value;
        return this;
    }

    public EntityOptions autoRotate(boolean value) {
        autoRotate = value;
        return this;
    }

    public EntityOptions offsetX(int value) {
        offsetX = value;
        return this;
    }

    public EntityOptions offsetY(int value) {
        offsetY = value;
        return this;
    }

    public EntityOptions label(Component value) {
        label = value;
        labelSet = true;
        return this;
    }

    public EntityOptions label(String value) {
        return label(value == null ? null : com.cooobird.datatip.api.text.LocalizedText.literal(value));
    }

    public EntityOptions label(java.util.Map<String, String> value) {
        return label(value == null ? null : OptionText.languages(value));
    }

    public EntityContent apply(EntityContent source) {
        Objects.requireNonNull(source, "source");
        return new EntityContent(
            entityType != null ? entityType : source.entityType(),
            size != null ? size : source.size(),
            rotationSpeed != null ? rotationSpeed : source.rotationSpeed(),
            autoRotate != null ? autoRotate : source.autoRotate(),
            offsetX != null ? offsetX : source.offsetX(),
            offsetY != null ? offsetY : source.offsetY(),
            labelSet ? label : source.label()
        );
    }
}
