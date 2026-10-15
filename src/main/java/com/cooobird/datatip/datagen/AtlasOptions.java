package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.content.AtlasContent;
import com.cooobird.datatip.api.text.LocalizedText;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/**
 * AtlasContent 的可复用选项；未设置的属性保留来源值，apply 创建新内容。
 */
public final class AtlasOptions {
    public AtlasOptions clearLabelText() {
        return labelText((LocalizedText) null);
    }

    public AtlasOptions label(String value) {
        return labelText(value);
    }

    public AtlasOptions label(java.util.Map<String, String> value) {
        return labelText(value);
    }

    public AtlasOptions label(net.minecraft.network.chat.Component value) {
        return labelText(value);
    }

    private ResourceLocation texturePath;
    private Integer width;
    private Integer height;
    private LocalizedText labelText;
    private boolean labelTextSet;
    private Integer offsetX;
    private Integer offsetY;

    public AtlasOptions texturePath(ResourceLocation value) {
        texturePath = Objects.requireNonNull(value, "texturePath");
        return this;
    }

    public AtlasOptions texturePath(String value) {
        return texturePath(ResourceLocation.parse(value));
    }

    public AtlasOptions width(int value) {
        width = value;
        return this;
    }

    public AtlasOptions height(int value) {
        height = value;
        return this;
    }

    public AtlasOptions labelText(LocalizedText value) {
        labelText = value;
        labelTextSet = true;
        return this;
    }

    public AtlasOptions labelText(String value) {
        return labelText(value == null ? null : com.cooobird.datatip.api.text.LocalizedText.literal(value));
    }

    public AtlasOptions labelText(java.util.Map<String, String> value) {
        return labelText(value == null ? null : OptionText.languages(value));
    }

    public AtlasOptions labelText(net.minecraft.network.chat.Component value) {
        return labelText(value == null ? null : value instanceof LocalizedText localized ? localized : LocalizedText.component(value));
    }

    public AtlasOptions offsetX(int value) {
        offsetX = value;
        return this;
    }

    public AtlasOptions offsetY(int value) {
        offsetY = value;
        return this;
    }

    public AtlasContent apply(AtlasContent source) {
        Objects.requireNonNull(source, "source");
        return new AtlasContent(
            texturePath != null ? texturePath : source.texturePath(),
            width != null ? width : source.width(),
            height != null ? height : source.height(),
            labelTextSet ? labelText : source.labelText(),
            offsetX != null ? offsetX : source.offsetX(),
            offsetY != null ? offsetY : source.offsetY()
        );
    }
}
