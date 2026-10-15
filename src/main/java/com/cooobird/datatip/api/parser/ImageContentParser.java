package com.cooobird.datatip.api.parser;

import com.cooobird.datatip.api.ContentParser;
import com.cooobird.datatip.api.ParseContext;
import com.cooobird.datatip.api.content.ImageContent;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

/**
 * ImageContent 解析器。
 * <p>
 * 负责将 JSON 对象解析为 {@link ImageContent} 实例。<br>
 * 图片内容用于渲染自定义纹理，支持 UV 偏移和缩放。
 * </p>
 *
 * <p>支持的 JSON 格式</p>
 * <pre>{@code
 * // 基础图片
 * {
 *   "type": "image",
 *   "texture": "mymod:textures/gui/image.png",
 *   "width": 64,
 *   "height": 64
 * }
 *
 * // 带 UV 偏移
 * {
 *   "type": "image",
 *   "texture": "minecraft:textures/gui/icons.png",
 *   "width": 16,
 *   "height": 16,
 *   "u": 0,
 *   "v": 0,
 *   "textureWidth": 256,
 *   "textureHeight": 256
 * }
 *
 * // 带缩放
 * {
 *   "type": "image",
 *   "texture": "mymod:textures/gui/icon.png",
 *   "width": 32,
 *   "height": 32,
 *   "scale": 2.0
 * }
 * }</pre>
 *
 * <p>字段说明</p>
 * <pre>{@code
 *   字段 | 类型 | 默认值 | 说明
 *   texture | String | - | 纹理资源路径
 *   width | int | 64 | 渲染宽度
 *   height | int | 64 | 渲染高度
 *   u | int | 0 | 纹理 U 偏移
 *   v | int | 0 | 纹理 V 偏移
 *   textureWidth | int | width | 纹理总宽度
 *   textureHeight | int | height | 纹理总高度
 *   scale | float | 1.0 | 缩放倍数
 *   offsetX | int | 0 | X 轴偏移量
 *   offsetY | int | 0 | Y 轴偏移量
 * }</pre>
 *
 * @author cooobird
 * @see ImageContent 图片内容类
 * @since 1.2.0
 */
public class ImageContentParser implements ContentParser {

    @Override
    public ImageContent parse(JsonObject json, ParseContext context) {
        // 解析纹理位置
        String textureStr = context.getString(json, "texture", "minecraft:textures/gui/icons.png");
        ResourceLocation texture = ResourceLocation.parse(textureStr);

        // 获取尺寸
        int width = context.getInt(json, "width", 64);
        int height = context.getInt(json, "height", 64);

        // 获取纹理偏移
        int u = context.getInt(json, "u", 0);
        int v = context.getInt(json, "v", 0);

        // 获取纹理尺寸
        int textureWidth = context.getInt(json, "textureWidth", width);
        int textureHeight = context.getInt(json, "textureHeight", height);

        // 获取缩放
        float scale = context.getFloat(json, "scale", 1.0f);

        // 获取偏移量
        return new ImageContent(texture, width, height, u, v, textureWidth, textureHeight, scale, 0, 0);
    }
}
