package com.cooobird.datatip.api.parser;

import com.cooobird.datatip.api.ContentParser;
import com.cooobird.datatip.api.ParseContext;
import com.cooobird.datatip.api.content.DividerContent;
import com.google.gson.JsonObject;

import java.util.Locale;

/**
 * DividerContent 解析器。
 * <p>
 * 负责将 JSON 对象解析为 {@link DividerContent} 实例。<br>
 * 分割线用于在 tooltip 中分隔不同内容区域。
 * </p>
 *
 * <p>支持的 JSON 格式</p>
 * <pre>{@code
 * // 基础分割线（填充整个宽度）
 * {
 *   "type": "divider",
 *   "color": "#555555"
 * }
 *
 * // 固定宽度
 * {
 *   "type": "divider",
 *   "color": "#555555",
 *   "width": 100,
 *   "widthMode": "fixed"
 * }
 *
 * // 居中显示
 * {
 *   "type": "divider",
 *   "color": "#555555",
 *   "width": 80,
 *   "widthMode": "centered"
 * }
 *
 * // 虚线样式
 * {
 *   "type": "divider",
 *   "color": "#555555",
 *   "style": "dashed"
 * }
 * }</pre>
 *
 * <p>字段说明</p>
 * <pre>{@code
 *   字段 | 类型 | 默认值 | 说明
 *   color | String | "#555555" | 颜色（命名色或十六进制）
 *   thickness | int | 1 | 线条粗细（像素）
 *   width | int | 0 | 宽度（像素，0=自动）
 *   widthMode | String | "fill" | 宽度模式（fill/fixed/centered）
 *   marginTop | int | 2 | 上边距（像素）
 *   marginBottom | int | 2 | 下边距（像素）
 *   style | String | "solid" | 线条样式（solid/dashed/dotted）
 * }</pre>
 *
 * @author cooobird
 * @see DividerContent 分割线内容类
 * @since 1.2.0
 */
public class DividerContentParser implements ContentParser {

    @Override
    public DividerContent parse(JsonObject json, ParseContext context) {
        // 获取基本属性
        int color = context.getColor(json, "color", 0xFF555555);
        int thickness = context.getInt(json, "thickness", 1);
        int width = context.getInt(json, "width", 0);
        int marginTop = context.getInt(json, "marginTop", 2);
        int marginBottom = context.getInt(json, "marginBottom", 2);

        // 解析线条样式
        String styleStr = context.getString(json, "style", "solid");
        DividerContent.DividerStyle style = switch (styleStr.toLowerCase(Locale.ROOT)) {
            case "dashed" -> DividerContent.DividerStyle.DASHED;
            case "dotted" -> DividerContent.DividerStyle.DOTTED;
            default -> DividerContent.DividerStyle.SOLID;
        };

        // 解析宽度模式
        String widthModeStr = context.getString(json, "widthMode", "fill");
        DividerContent.WidthMode widthMode = switch (widthModeStr.toLowerCase(Locale.ROOT)) {
            case "fixed" -> DividerContent.WidthMode.FIXED;
            case "centered" -> DividerContent.WidthMode.CENTERED;
            default -> DividerContent.WidthMode.FILL;
        };

        return new DividerContent(color, thickness, width, marginTop, marginBottom, style, widthMode);
    }
}
