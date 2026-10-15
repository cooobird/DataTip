package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.content.ImageContent;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/**
 * ImageContent 的可复用选项；未设置的属性保留来源值，apply 创建新内容。
 */
public final class ImageOptions {
    private ResourceLocation texture;
    private Integer width;
    private Integer height;
    private Integer u;
    private Integer v;
    private Integer textureWidth;
    private Integer textureHeight;
    private Float scale;
    private Integer offsetX;
    private Integer offsetY;

    public ImageOptions texture(ResourceLocation value) {
        texture = Objects.requireNonNull(value, "texture");
        return this;
    }

    public ImageOptions texture(String value) {
        return texture(ResourceLocation.parse(value));
    }

    public ImageOptions width(int value) {
        width = value;
        return this;
    }

    public ImageOptions height(int value) {
        height = value;
        return this;
    }

    public ImageOptions u(int value) {
        u = value;
        return this;
    }

    public ImageOptions v(int value) {
        v = value;
        return this;
    }

    public ImageOptions textureWidth(int value) {
        textureWidth = value;
        return this;
    }

    public ImageOptions textureHeight(int value) {
        textureHeight = value;
        return this;
    }

    public ImageOptions scale(float value) {
        scale = value;
        return this;
    }

    public ImageOptions offsetX(int value) {
        offsetX = value;
        return this;
    }

    public ImageOptions offsetY(int value) {
        offsetY = value;
        return this;
    }

    public ImageContent apply(ImageContent source) {
        Objects.requireNonNull(source, "source");
        return new ImageContent(
            texture != null ? texture : source.texture(),
            width != null ? width : source.width(),
            height != null ? height : source.height(),
            u != null ? u : source.u(),
            v != null ? v : source.v(),
            textureWidth != null ? textureWidth : source.textureWidth(),
            textureHeight != null ? textureHeight : source.textureHeight(),
            scale != null ? scale : source.scale(),
            offsetX != null ? offsetX : source.offsetX(),
            offsetY != null ? offsetY : source.offsetY()
        );
    }
}
