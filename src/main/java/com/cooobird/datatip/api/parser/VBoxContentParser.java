package com.cooobird.datatip.api.parser;

import com.cooobird.datatip.api.ContentParser;
import com.cooobird.datatip.api.ParseContext;
import com.cooobird.datatip.api.TipContent;
import com.cooobird.datatip.api.content.VBoxContent;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Locale;

/**
 * VBoxContent 解析器。
 * <p>
 * 负责将 JSON 对象解析为 {@link VBoxContent} 实例。<br>
 * 垂直布局容器将子元素从上到下排列。
 * </p>
 *
 * <p>支持的 JSON 格式</p>
 * <pre>{@code
 * // 基础垂直布局
 * {
 *   "type": "vbox",
 *   "children": [
 *     {"type": "text", "text": "第一行"},
 *     {"type": "text", "text": "第二行"}
 *   ]
 * }
 *
 * // 带间距和内边距
 * {
 *   "type": "vbox",
 *   "gap": 4,
 *   "padding": 2,
 *   "children": [...]
 * }
 *
 * // 居中对齐
 * {
 *   "type": "vbox",
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
 *   align | String | "left" | 子元素水平对齐（left/center/right）
 *   children | Array | [] | 子元素数组
 * }</pre>
 *
 * @author cooobird
 * @see VBoxContent 垂直布局内容类
 * @since 1.2.0
 */
public class VBoxContentParser implements ContentParser {

    @Override
    public VBoxContent parse(JsonObject json, ParseContext context) {
        // 获取布局选项
        int gap = context.getInt(json, "gap", 0);
        int padding = context.getInt(json, "padding", 0);

        // 解析对齐方式
        String alignStr = context.getString(json, "align", "left");
        VBoxContent.HorizontalAlign align = switch (alignStr.toLowerCase(Locale.ROOT)) {
            case "center" -> VBoxContent.HorizontalAlign.CENTER;
            case "right" -> VBoxContent.HorizontalAlign.RIGHT;
            default -> VBoxContent.HorizontalAlign.LEFT;
        };

        // 创建 VBoxContent
        VBoxContent vbox = new VBoxContent(List.of(), gap, padding, align);

        // 解析子元素
        if (context.has(json, "children")) {
            JsonArray childrenArray = context.getArray(json, "children");
            if (childrenArray != null) {
                List<TipContent> children = context.parseContentArray(childrenArray);
                children.forEach(vbox::addChild);
            }
        }

        return vbox;
    }
}
