package com.cooobird.datatip.api.parser;

import com.cooobird.datatip.api.ContentParser;
import com.cooobird.datatip.api.ParseContext;
import com.cooobird.datatip.api.content.ItemContent;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * ItemContent 解析器。
 * <p>
 * 负责将 JSON 对象解析为 {@link ItemContent} 实例。<br>
 * 物品内容用于渲染物品图标，支持显示数量、耐久、标签等。
 * </p>
 *
 * <p>支持的 JSON 格式</p>
 * <pre>{@code
 * // 基础物品渲染
 * {
 *   "type": "item",
 *   "item": "minecraft:diamond"
 * }
 *
 * // 指定数量和大小
 * {
 *   "type": "item",
 *   "item": "minecraft:golden_apple",
 *   "count": 64,
 *   "size": 32
 * }
 *
 * // 显示标签
 * {
 *   "type": "item",
 *   "item": "minecraft:diamond_sword",
 *   "size": 32,
 *   "showLabel": true,
 *   "label": "钻石剑",
 *   "labelColor": "#55FFFF"
 * }
 * }</pre>
 *
 * <p>字段说明</p>
 * <pre>{@code
 *   字段 | 类型 | 默认值 | 说明
 *   item | String | "minecraft:air" | 物品 ID
 *   count | int | 1 | 物品数量
 *   size | int | 16 | 渲染大小
 *   showCount | boolean | true | 是否显示数量
 *   showDurability | boolean | true | 是否显示耐久
 *   showLabel | boolean | false | 是否显示标签
 *   label | String | null | 自定义标签文本
 *   labelColor | String | "#FFFFFF" | 标签颜色
 *   offsetX | int | 0 | X 轴偏移量
 *   offsetY | int | 0 | Y 轴偏移量
 * }</pre>
 *
 * @author cooobird
 * @see ItemContent 物品内容类
 * @since 1.2.0
 */
public class ItemContentParser implements ContentParser {

    @Override
    public ItemContent parse(JsonObject json, ParseContext context) {
        // 解析物品 ID
        String itemId = context.getString(json, "item", "minecraft:air");
        ResourceLocation itemLocation = ResourceLocation.parse(itemId);

        // 获取物品
        Item item = BuiltInRegistries.ITEM.get(itemLocation);
        if (item == null || item == Items.AIR) {
            return ItemContent.of(ItemStack.EMPTY);
        }

        // 创建 ItemStack
        int count = context.getInt(json, "count", 1);
        ItemStack stack = new ItemStack(item, count);

        // 获取渲染选项
        int size = context.getInt(json, "size", 16);
        boolean showCount = context.getBoolean(json, "showCount", true);
        boolean showDurability = context.getBoolean(json, "showDurability", true);
        boolean showLabel = context.getBoolean(json, "showLabel", false);

        // 获取标签
        Component label = null;
        if (context.has(json, "label")) {
            label = LocalizedTextParser.parse(json, "label", context);
            showLabel = true;
        }

        // 获取标签颜色
        Integer labelColor = null;
        if (context.has(json, "labelColor")) {
            labelColor = context.getColor(json, "labelColor", 0xFFFFFF);
        }

        // 平面偏移由所有节点共享的 TipModifiers 统一处理，内容本身保持局部原点不变。
        return new ItemContent(stack, size, showCount, showDurability, showLabel, label, labelColor, 0, 0);
    }
}
