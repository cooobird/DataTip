package com.cooobird.datatip.api.parser;

import com.cooobird.datatip.api.ContentParser;
import com.cooobird.datatip.api.ParseContext;
import com.cooobird.datatip.api.content.BlockContent;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

/**
 * BlockContent 解析器。
 * <p>
 * 负责将 JSON 对象解析为 {@link BlockContent} 实例。<br>
 * 支持自动旋转和可选标签。
 * </p>
 *
 * <p>支持的 JSON 格式</p>
 * <pre>{@code
 * // 基础方块渲染
 * {
 *   "type": "block",
 *   "block": "minecraft:stone",
 *   "size": 32
 * }
 *
 * // 带旋转动画
 * {
 *   "type": "block",
 *   "block": "minecraft:crafting_table",
 *   "size": 48,
 *   "rotationSpeed": 0.5,
 *   "autoRotate": true
 * }
 *
 * // 带标签
 * {
 *   "type": "block",
 *   "block": "minecraft:diamond_block",
 *   "size": 32,
 *   "label": "钻石块"
 * }
 * }</pre>
 *
 * <p>字段说明</p>
 * <pre>{@code
 *   字段 | 类型 | 默认值 | 说明
 *   block | String | "minecraft:stone" | 方块 ID
 *   size | int | 32 | 渲染大小
 *   rotationSpeed | float | 0.5 | 旋转速度
 *   autoRotate | boolean | true | 是否自动旋转
 *   offsetX | int | 0 | X 轴偏移量
 *   offsetY | int | 0 | Y 轴偏移量
 *   label | String | null | 标签文本
 * }</pre>
 *
 * @author cooobird
 * @see BlockContent 方块内容类
 * @since 1.2.0
 */
public class BlockContentParser implements ContentParser {

    @Override
    public BlockContent parse(JsonObject json, ParseContext context) {
        // 解析方块类型
        String blockId = context.getString(json, "block", "minecraft:stone");
        ResourceLocation blockLocation = ResourceLocation.parse(blockId);

        Block block = BuiltInRegistries.BLOCK.get(blockLocation);
        if (block == null) {
            block = Blocks.STONE;  // 回退到默认方块
        }

        // 获取渲染选项
        int size = context.getInt(json, "size", 32);
        float rotationSpeed = context.getFloat(json, "rotationSpeed", 0.5f);
        boolean autoRotate = context.getBoolean(json, "autoRotate", true);

        // 获取标签（可选）
        Component label = null;
        if (context.has(json, "label")) {
            label = LocalizedTextParser.parse(json, "label", context);
        }

        return new BlockContent(block, size, rotationSpeed, autoRotate, label, 0, 0);
    }
}
