package com.cooobird.datatip.api.parser;

import com.cooobird.datatip.api.ContentParser;
import com.cooobird.datatip.api.ParseContext;
import com.cooobird.datatip.api.content.ProgressContent;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;

import java.util.Locale;

/**
 * ProgressContent 解析器。
 * <p>
 * 负责将 JSON 对象解析为 {@link ProgressContent} 实例。<br>
 * 进度条用于显示进度、血量、耐久等数值。
 * </p>
 *
 * <p>支持的 JSON 格式</p>
 * <pre>{@code
 * // 基础进度条
 * {
 *   "type": "progress",
 *   "progress": 0.75,
 *   "width": 100,
 *   "height": 8
 * }
 *
 * // 带标签
 * {
 *   "type": "progress",
 *   "progress": 0.75,
 *   "width": 100,
 *   "height": 6,
 *   "showLabel": true,
 *   "label": "75%",
 *   "labelAlign": "right"
 * }
 *
 * // 分段样式
 * {
 *   "type": "progress",
 *   "progress": 0.5,
 *   "width": 100,
 *   "height": 8,
 *   "style": "segmented"
 * }
 *
 * // 动画效果
 * {
 *   "type": "progress",
 *   "progress": 0.9,
 *   "width": 100,
 *   "height": 6,
 *   "colorFg": "#FFD700",
 *   "animated": true,
 *   "animSpeed": 3
 * }
 * }</pre>
 *
 * <p>字段说明</p>
 * <pre>{@code
 *   字段 | 类型 | 默认值 | 说明
 *   progress | float | 0.0 | 进度值（0.0-1.0）
 *   width | int | 100 | 宽度（像素）
 *   height | int | 8 | 高度（像素）
 *   colorFg | String | "#55FF55" | 前景色
 *   colorBg | String | "#333333" | 背景色
 *   colorFgLight | String | null | 渐变前景色（可选）
 *   colorBgDark | String | null | 渐变背景色（可选）
 *   style | String | "gradient" | 样式（gradient/flat/segmented/animated）
 *   showLabel | boolean | false | 是否显示标签
 *   label | String | null | 自定义标签文本
 *   labelAlign | String | "left" | 标签对齐（left/center/right）
 *   animated | boolean | false | 是否启用动画
 *   animSpeed | int | 2 | 动画速度
 * }</pre>
 *
 * @author cooobird
 * @see ProgressContent 进度条内容类
 * @since 1.2.0
 */
public class ProgressContentParser implements ContentParser {

    @Override
    public ProgressContent parse(JsonObject json, ParseContext context) {
        // 获取进度值
        float progress = context.getFloat(json, "progress", 0.0f);

        // 获取尺寸
        int width = context.getInt(json, "width", 100);
        int height = context.getInt(json, "height", 8);

        // 获取颜色
        int colorFg = context.getColor(json, "colorFg", 0xFF55FF55);
        int colorBg = context.getColor(json, "colorBg", 0xFF333333);

        // 可选的渐变颜色
        Integer colorFgLight = context.has(json, "colorFgLight") ?
            context.getColor(json, "colorFgLight", 0xFF81C784) : null;
        Integer colorBgDark = context.has(json, "colorBgDark") ?
            context.getColor(json, "colorBgDark", 0xFF1A1A1A) : null;

        // 解析样式
        String styleStr = context.getString(json, "style", "gradient");
        ProgressContent.ProgressStyle style = switch (styleStr.toLowerCase(Locale.ROOT)) {
            case "flat" -> ProgressContent.ProgressStyle.FLAT;
            case "segmented" -> ProgressContent.ProgressStyle.SEGMENTED;
            case "animated" -> ProgressContent.ProgressStyle.ANIMATED;
            default -> ProgressContent.ProgressStyle.GRADIENT;
        };

        // 获取标签选项
        boolean showLabel = context.getBoolean(json, "showLabel", false);
        Component customLabel = null;
        if (context.has(json, "label")) {
            customLabel = LocalizedTextParser.parse(json, "label", context);
            showLabel = true;
        }

        // 获取动画选项
        boolean animated = context.getBoolean(json, "animated", false);
        if (style == ProgressContent.ProgressStyle.ANIMATED) {
            animated = true;
        }
        int animSpeed = context.getInt(json, "animSpeed", 2);

        // 获取标签对齐方式
        String labelAlignStr = context.getString(json, "labelAlign", "left");
        ProgressContent.LabelAlign labelAlign = switch (labelAlignStr.toLowerCase(Locale.ROOT)) {
            case "center" -> ProgressContent.LabelAlign.CENTER;
            case "right" -> ProgressContent.LabelAlign.RIGHT;
            default -> ProgressContent.LabelAlign.LEFT;
        };

        return new ProgressContent(progress, width, height, colorFg, colorBg,
            colorFgLight, colorBgDark, style, showLabel, customLabel, labelAlign, animated, animSpeed);
    }
}
