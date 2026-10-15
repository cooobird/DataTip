package com.cooobird.datatip.api.parser;

import com.cooobird.datatip.api.ContentParser;
import com.cooobird.datatip.api.ParseContext;
import com.cooobird.datatip.api.content.AtlasContent;
import com.cooobird.datatip.api.text.LocalizedText;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

/**
 * AtlasContent 解析器。
 * <p>
 * 负责将 JSON 对象解析为 {@link AtlasContent} 实例。<br>
 * 支持三种方式指定纹理：直接路径、方块 ID、物品 ID。
 * </p>
 *
 * <p>支持的 JSON 格式</p>
 * <pre>{@code
 * // 直接指定纹理路径
 * {
 *   "type": "atlas",
 *   "texture": "minecraft:textures/block/stone.png",
 *   "width": 32,
 *   "height": 32
 * }
 *
 * // 使用方块 ID（自动转换路径）
 * {
 *   "type": "atlas",
 *   "block": "minecraft:red_concrete",
 *   "size": 32
 * }
 *
 * // 使用物品 ID（自动转换路径）
 * {
 *   "type": "atlas",
 *   "item": "minecraft:apple",
 *   "size": 32
 * }
 *
 * // 带标签
 * {
 *   "type": "atlas",
 *   "block": "minecraft:diamond_block",
 *   "size": 32,
 *   "label": "钻石块"
 * }
 * }</pre>
 *
 * <p>字段说明</p>
 * <pre>{@code
 *   字段 | 类型 | 默认值 | 说明
 *   texture | String | - | 完整纹理路径
 *   block | String | - | 方块 ID
 *   item | String | - | 物品 ID
 *   size | int | 16 | 渲染大小
 *   width | int | 16 | 渲染宽度
 *   height | int | 16 | 渲染高度
 *   label | String | null | 标签文本
 *   offsetX | int | 0 | X 轴偏移量
 *   offsetY | int | 0 | Y 轴偏移量
 * }</pre>
 *
 * @author cooobird
 * @see AtlasContent 纹理内容类
 * @since 1.2.0
 */
public class AtlasContentParser implements ContentParser {

    @Override
    public AtlasContent parse(JsonObject json, ParseContext context) {
        // 获取尺寸
        int width = context.getInt(json, "width", 16);
        int height = context.getInt(json, "height", width);
        int size = context.getInt(json, "size", 0);
        if (size > 0) {
            width = size;
            height = size;
        }

        // 获取标签
        LocalizedText label = context.has(json, "label")
            ? LocalizedTextParser.parse(json, "label", context)
            : null;

        // 获取偏移量

        // 优先级：texture > block > item
        // 1. 直接指定纹理路径
        if (context.has(json, "texture")) {
            String textureStr = context.getString(json, "texture", "");
            ResourceLocation texturePath = ResourceLocation.parse(textureStr);
            return new AtlasContent(texturePath, width, height, label, 0, 0);
        }

        // 2. 从方块 ID 自动转换
        if (context.has(json, "block")) {
            String blockStr = context.getString(json, "block", "");
            ResourceLocation blockId = ResourceLocation.parse(blockStr);
            ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(
                blockId.getNamespace(), "textures/block/" + blockId.getPath() + ".png");
            return new AtlasContent(texture, width, height, label, 0, 0);
        }

        // 3. 从物品 ID 自动转换
        if (context.has(json, "item")) {
            String itemStr = context.getString(json, "item", "");
            ResourceLocation itemId = ResourceLocation.parse(itemStr);
            ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(
                itemId.getNamespace(), "textures/item/" + itemId.getPath() + ".png");
            return new AtlasContent(texture, width, height, label, 0, 0);
        }

        // 默认返回石头纹理
        return AtlasContent.of(ResourceLocation.parse("minecraft:textures/block/stone.png"), width, height);
    }
}
