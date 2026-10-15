package com.cooobird.datatip.api.parser;

import com.cooobird.datatip.api.ContentParser;
import com.cooobird.datatip.api.ParseContext;
import com.cooobird.datatip.api.TipContent;
import com.cooobird.datatip.api.content.HBoxContent;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Locale;

/**
 * HBoxContent 解析器。
 * <p>
 * 负责将 JSON 对象解析为 {@link HBoxContent} 实例。<br>
 * 水平布局容器将子元素从左到右排列。
 * </p>
 *
 * <p>支持的 JSON 格式</p>
 * <pre>{@code
 * // 基础水平布局
 * {
 *   "type": "hbox",
 *   "children": [
 *     {"type": "item", "item": "minecraft:diamond"},
 *     {"type": "text", "text": "钻石"}
 *   ]
 * }
 *
 * // 带间距和内边距
 * {
 *   "type": "hbox",
 *   "gap": 8,
 *   "padding": 4,
 *   "children": [...]
 * }
 *
 * // 垂直居中对齐
 * {
 *   "type": "hbox",
 *   "align": "center",
 *   "children": [...]
 * }
 * }</pre>
 *
 * <p>字段说明</p>
 * <pre>{@code
 *   字段 | 类型 | 默认值 | 说明
 *   gap | int | 0 | 子元素间距（像素）
 *   padding | int | 0 | 内边距（像素）
 *   align | String | "top" | 子元素垂直对齐（top/center/bottom）
 *   children | Array | [] | 子元素数组
 * }</pre>
 *
 * @author cooobird
 * @see HBoxContent 水平布局内容类
 * @since 1.2.0
 */
public class HBoxContentParser implements ContentParser {

    @Override
    public HBoxContent parse(JsonObject json, ParseContext context) {
        // 获取布局选项
        int gap = context.getInt(json, "gap", 0);
        int padding = context.getInt(json, "padding", 0);

        // 解析对齐方式
        String alignStr = context.getString(json, "align", "top");
        HBoxContent.VerticalAlign align = switch (alignStr.toLowerCase(Locale.ROOT)) {
            case "center" -> HBoxContent.VerticalAlign.CENTER;
            case "bottom" -> HBoxContent.VerticalAlign.BOTTOM;
            default -> HBoxContent.VerticalAlign.TOP;
        };

        // 创建 HBoxContent
        HBoxContent hbox = new HBoxContent(List.of(), gap, padding, align);

        // 解析子元素
        if (context.has(json, "children")) {
            JsonArray childrenArray = context.getArray(json, "children");
            if (childrenArray != null) {
                List<TipContent> children = context.parseContentArray(childrenArray);
                children.forEach(hbox::addChild);
            }
        }

        return hbox;
    }
}
