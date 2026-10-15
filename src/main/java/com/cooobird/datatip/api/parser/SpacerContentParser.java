package com.cooobird.datatip.api.parser;

import com.cooobird.datatip.api.ContentParser;
import com.cooobird.datatip.api.ParseContext;
import com.cooobird.datatip.api.content.SpacerContent;
import com.google.gson.JsonObject;

/**
 * SpacerContent 解析器。
 * <p>
 * 负责将 JSON 对象解析为 {@link SpacerContent} 实例。<br>
 * 间距内容用于在 tooltip 中添加空白间距。
 * </p>
 *
 * <p>支持的 JSON 格式</p>
 * <pre>{@code
 * // 基础间距
 * {
 *   "type": "spacer",
 *   "height": 8
 * }
 *
 * // 小间距
 * {
 *   "type": "spacer",
 *   "height": 4
 * }
 * }</pre>
 *
 * <p>字段说明</p>
 * <pre>{@code
 *   字段 | 类型 | 默认值 | 说明
 *   height | int | 4 | 间距高度（像素）
 * }</pre>
 *
 * @author cooobird
 * @see SpacerContent 间距内容类
 * @since 1.2.0
 */
public class SpacerContentParser implements ContentParser {

    @Override
    public SpacerContent parse(JsonObject json, ParseContext context) {
        int height = context.getInt(json, "height", 4);
        return new SpacerContent(height);
    }
}
