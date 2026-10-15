package com.cooobird.datatip.api.parser;

import com.cooobird.datatip.api.ContentParser;
import com.cooobird.datatip.api.ParseContext;
import com.cooobird.datatip.api.TipContent;
import com.cooobird.datatip.api.content.CarouselContent;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Locale;

/**
 * CarouselContent 解析器。
 * <p>
 * 负责将 JSON 对象解析为 {@link CarouselContent} 实例。<br>
 * 轮播容器会在多个内容帧之间自动切换。
 * </p>
 *
 * <p>支持的 JSON 格式</p>
 * <pre>{@code
 * // 基础轮播
 * {
 *   "type": "carousel",
 *   "intervalSeconds": 3,
 *   "frames": [
 *     {"type": "text", "text": "第一帧", "color": "white"},
 *     {"type": "text", "text": "第二帧", "color": "gold"},
 *     {"type": "text", "text": "第三帧", "color": "aqua"}
 *   ]
 * }
 *
 * // 带过渡效果
 * {
 *   "type": "carousel",
 *   "intervalSeconds": 5,
 *   "transition": "slide",
 *   "frames": [...]
 * }
 * }</pre>
 *
 * <p>字段说明</p>
 * <pre>{@code
 *   字段 | 类型 | 默认值 | 说明
 *   intervalSeconds | int | 3 | 帧切换间隔（秒）
 *   transition | String | "fade" | 过渡效果（fade/slide/none）
 *   frames | Array | [] | 内容帧数组
 * }</pre>
 *
 * @author cooobird
 * @see CarouselContent 轮播内容类
 * @since 1.2.0
 */
public class CarouselContentParser implements ContentParser {

    @Override
    public CarouselContent parse(JsonObject json, ParseContext context) {
        // 获取帧切换间隔（秒）
        int intervalSeconds = context.getInt(json, "intervalSeconds", 3);

        // 获取过渡效果类型
        String transitionStr = context.getString(json, "transition", "fade");
        CarouselContent.TransitionType transition = switch (transitionStr.toLowerCase(Locale.ROOT)) {
            case "slide" -> CarouselContent.TransitionType.SLIDE;
            case "none" -> CarouselContent.TransitionType.NONE;
            default -> CarouselContent.TransitionType.FADE;
        };

        // 解析帧内容
        List<TipContent> frames = List.of();
        if (context.has(json, "frames")) {
            JsonArray framesArray = context.getArray(json, "frames");
            if (framesArray != null) {
                frames = context.parseContentArray(framesArray);
            }
        }

        return new CarouselContent(frames, intervalSeconds, transition);
    }
}
