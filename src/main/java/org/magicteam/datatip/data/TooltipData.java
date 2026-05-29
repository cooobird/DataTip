package org.magicteam.datatip.data;

import com.google.gson.*;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * 从 config/datatip.json 加载所有 tooltip 规则。
 * 支持 exact / #tag / wildcard 三种匹配。
 */
public class TooltipData {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setLenient().create();

    private List<TooltipEntry> entries = List.of();
    private String rawJson = "";

    public void load(Path jsonPath) {
        if (!Files.exists(jsonPath)) { entries = List.of(); rawJson = ""; return; }
        try {
            rawJson = Files.readString(jsonPath);
            Map<String, Object> raw = GSON.fromJson(rawJson,
                new com.google.gson.reflect.TypeToken<Map<String, Object>>() {}.getType());
            List<TooltipEntry> parsed = new ArrayList<>();
            if (raw != null) {
                for (var e : raw.entrySet()) {
                    TooltipEntry entry = parseEntry(e.getKey(), e.getValue());
                    if (entry != null) parsed.add(entry);
                }
            }
            entries = Collections.unmodifiableList(parsed);
            LOGGER.info("Loaded {} tooltip entries", entries.size());
        } catch (Exception ex) {
            LOGGER.error("Failed to parse datatip.json: {}", ex.getMessage());
            entries = List.of();
        }
    }

    @SuppressWarnings("unchecked")
    private static TooltipEntry parseEntry(String key, Object val) {
        if (!(val instanceof Map<?, ?> m)) return null;
        TooltipEntry.MatchType matchType;
        String matchKey;
        if (key.startsWith("#")) { matchType = TooltipEntry.MatchType.TAG; matchKey = key.substring(1); }
        else if (key.contains("*") || key.contains("?")) { matchType = TooltipEntry.MatchType.WILDCARD; matchKey = key; }
        else { matchType = TooltipEntry.MatchType.EXACT; matchKey = key; }

        Map<String, List<TooltipLine>> langText = new LinkedHashMap<>();
        for (var me : m.entrySet()) {
            String k = me.getKey().toString();
            if (isMeta(k)) continue;
            if (me.getValue() instanceof List<?> list) {
                List<TooltipLine> lines = parseLines(list);
                if (!lines.isEmpty()) langText.put(k, lines);
            }
        }
        if (langText.isEmpty()) return null;

        Map<String, Object> nbt = null;
        if (m.get("nbt") instanceof Map<?, ?> nm) {
            nbt = new LinkedHashMap<>();
            for (var nme : nm.entrySet()) nbt.put(nme.getKey().toString(), nme.getValue());
        }

        return new TooltipEntry(matchKey, matchType, langText,
            bool(m.get("shift")), str(m.get("color")), nbt,
            parseConditions(m.get("conditions")),
            parseHover(m.get("hover")));
    }

    @SuppressWarnings("unchecked")
    private static List<TooltipLine> parseLines(List<?> list) {
        List<TooltipLine> r = new ArrayList<>();
        for (Object item : list) {
            if (item instanceof String s) r.add(TooltipLine.of(s));
            else if (item instanceof Map<?, ?> lm) r.add(new TooltipLine(
                str(lm.get("text")), str(lm.get("color")),
                (Boolean) lm.get("bold"), (Boolean) lm.get("italic"),
                (Boolean) lm.get("underlined"), (Boolean) lm.get("strikethrough"),
                str(lm.get("icon")), str(lm.get("hover"))));
        }
        return r;
    }

    @SuppressWarnings("unchecked")
    private static List<TooltipEntry.Condition> parseConditions(Object v) {
        if (!(v instanceof Map<?, ?> cm)) return List.of();
        List<TooltipEntry.Condition> r = new ArrayList<>();
        cm.forEach((k, val) -> r.add(new TooltipEntry.Condition(k.toString(), val)));
        return r;
    }

    private static TooltipEntry.HoverEvent parseHover(Object v) {
        if (v instanceof Map<?, ?> hm)
            return new TooltipEntry.HoverEvent(str(hm.get("action")), hm.get("contents"));
        return null;
    }

    public void loadFromString(String json) {
        rawJson = json;
        if (json == null || json.isBlank()) { entries = List.of(); return; }
        try {
            Map<String, Object> raw = GSON.fromJson(json,
                new com.google.gson.reflect.TypeToken<Map<String, Object>>() {}.getType());
            List<TooltipEntry> parsed = new ArrayList<>();
            if (raw != null) for (var e : raw.entrySet()) {
                TooltipEntry entry = parseEntry(e.getKey(), e.getValue());
                if (entry != null) parsed.add(entry);
            }
            entries = Collections.unmodifiableList(parsed);
        } catch (Exception ex) {
            LOGGER.error("Failed to parse synced data: {}", ex.getMessage());
            entries = List.of();
        }
    }

    public List<TooltipEntry> getEntries() { return entries; }
    public String getRawJson() { return rawJson; }

    private static boolean isMeta(String k) {
        return switch (k) { case "shift","color","nbt","conditions","hover" -> true; default -> false; };
    }
    private static String str(Object v) { return v instanceof String s && !s.isEmpty() ? s : null; }
    private static boolean bool(Object v) { return Boolean.TRUE.equals(v); }
}
