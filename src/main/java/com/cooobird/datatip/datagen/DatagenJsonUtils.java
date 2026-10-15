package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.TipContent;
import com.cooobird.datatip.api.util.ColorParser;
import com.google.gson.JsonArray;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * 数据生成 JSON 写出辅助方法。
 */
final class DatagenJsonUtils {
    private DatagenJsonUtils() {
    }

    static ResourceLocation parseResourceLocation(String value) {
        ResourceLocation id = ResourceLocation.tryParse(value);
        if (id == null) {
            throw new IllegalArgumentException("Invalid resource location: " + value);
        }
        return id;
    }

    static JsonArray childrenToJson(List<TipContent> children) {
        JsonArray childrenJson = new JsonArray();
        for (TipContent child : children) {
            childrenJson.add(TipContentJsonSerializer.toJson(child));
        }
        return childrenJson;
    }

    static String colorToHex(int argb) {
        return ColorParser.toHex(argb);
    }
}
