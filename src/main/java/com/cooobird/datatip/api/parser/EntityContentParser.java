package com.cooobird.datatip.api.parser;

import com.cooobird.datatip.api.ContentParser;
import com.cooobird.datatip.api.ParseContext;
import com.cooobird.datatip.api.content.EntityContent;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

/**
 * EntityContent 解析器。
 * <p>
 * 负责将 JSON 对象解析为 {@link EntityContent} 实例。<br>
 * 支持自动旋转和可选标签。
 * </p>
 *
 * <p>支持的 JSON 格式</p>
 * <pre>{@code
 * // 基础实体渲染
 * {
 *   "type": "entity",
 *   "entity": "minecraft:wolf",
 *   "size": 48
 * }
 *
 * // 带旋转动画
 * {
 *   "type": "entity",
 *   "entity": "minecraft:creeper",
 *   "size": 48,
 *   "rotationSpeed": 1.0,
 *   "autoRotate": true
 * }
 *
 * // 带标签
 * {
 *   "type": "entity",
 *   "entity": "minecraft:wolf",
 *   "size": 48,
 *   "label": "狼"
 * }
 * }</pre>
 *
 * <p>字段说明</p>
 * <pre>{@code
 *   字段 | 类型 | 默认值 | 说明
 *   entity | String | "minecraft:pig" | 实体类型 ID
 *   size | int | 48 | 渲染大小
 *   rotationSpeed | float | 1.0 | 旋转速度
 *   autoRotate | boolean | true | 是否自动旋转
 *   offsetX | int | 0 | X 轴偏移量
 *   offsetY | int | 0 | Y 轴偏移量
 *   label | String | null | 标签文本
 * }</pre>
 *
 * @author cooobird
 * @see EntityContent 实体内容类
 * @since 1.2.0
 */
public class EntityContentParser implements ContentParser {

    @Override
    public EntityContent parse(JsonObject json, ParseContext context) {
        // 解析实体类型
        String entityId = context.getString(json, "entity", "minecraft:pig");
        ResourceLocation entityLocation = ResourceLocation.parse(entityId);

        EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(entityLocation);
        if (entityType == null) {
            entityType = EntityType.PIG;  // 回退到默认实体
        }

        // 获取渲染选项
        int size = context.getInt(json, "size", 48);
        float rotationSpeed = context.getFloat(json, "rotationSpeed", 1.0f);
        boolean autoRotate = context.getBoolean(json, "autoRotate", true);

        // 获取标签（可选）
        Component label = null;
        if (context.has(json, "label")) {
            label = LocalizedTextParser.parse(json, "label", context);
        }

        return new EntityContent(entityType, size, rotationSpeed, autoRotate, 0, 0, label);
    }
}
