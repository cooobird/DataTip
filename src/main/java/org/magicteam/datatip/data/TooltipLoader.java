package org.magicteam.datatip.data;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

import java.io.InputStreamReader;
import java.util.*;

public class TooltipLoader {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setLenient().create();

    private List<TooltipEntry> entries = List.of();
    private boolean loaded;

    /**
     * 首次悬停时触发，后续直接跳过
     */
    public void loadIfNeeded(ResourceManager rm) {
        if (loaded) return;
        loaded = true;
        load(rm);
    }

    public void load(ResourceManager rm) {
        List<TooltipEntry> all = new ArrayList<>();

        rm.listResources("datatip", p -> p.getPath().endsWith(".json"))
            .forEach((loc, res) -> {
                try (var reader = new InputStreamReader(res.open())) {
                    Map<String, Object> raw = GSON.fromJson(reader,
                        new com.google.gson.reflect.TypeToken<Map<String, Object>>() {
                        }.getType());
                    if (raw != null) {
                        for (var e : raw.entrySet()) {
                            if (e.getKey() == null || e.getValue() == null) continue;
                            TooltipEntry te = parseEntry(e.getKey(), e.getValue());
                            if (te != null) all.add(te);
                        }
                    }
                } catch (Exception ex) {
                    LOGGER.error("Failed to load {}: {}", loc, ex.getMessage());
                }
            });

        entries = Collections.unmodifiableList(all);
        LOGGER.info("Loaded {} tooltip entries from resource packs", entries.size());
    }

    public List<TooltipEntry> getEntries() {
        return entries;
    }

    private static TooltipEntry parseEntry(String key, Object val) {
        if (!(val instanceof Map<?, ?> m)) return null;

        TooltipEntry.MatchType type;
        String matchKey;
        if (key.startsWith("#")) {
            type = TooltipEntry.MatchType.TAG;
            matchKey = key.substring(1);
        } else if (key.contains("*") || key.contains("?")) {
            type = TooltipEntry.MatchType.WILDCARD;
            matchKey = key;
        } else {
            type = TooltipEntry.MatchType.EXACT;
            matchKey = key;
        }

        ListMultimap<String, TooltipLine> lang = ArrayListMultimap.create();
        if (m.get("text") instanceof Map<?, ?> textMap) {
            for (var te : textMap.entrySet()) {
                String lk = te.getKey() != null ? te.getKey().toString() : "";
                if (lk.isEmpty()) continue;
                for (TooltipLine line : parseTextValue(te.getValue())) lang.put(lk, line);
            }
        } else {
            for (var me : m.entrySet()) {
                String k = me.getKey() != null ? me.getKey().toString() : "";
                if (k.isEmpty() || isMeta(k)) continue;
                for (TooltipLine line : parseTextValue(me.getValue())) lang.put(k, line);
            }
        }
        if (lang.isEmpty()) return null;

        Map<String, Object> nbt = null;
        if (m.get("nbt") instanceof Map<?, ?> nm) {
            nbt = new LinkedHashMap<>();
            for (var nme : nm.entrySet()) {
                if (nme.getKey() == null) continue;
                nbt.put(nme.getKey().toString(), nme.getValue() != null ? nme.getValue() : "");
            }
        }

        return new TooltipEntry(matchKey, type, lang, bool(m.get("shift")), bool(m.get("prepend")),
            str(m.get("color")), nbt, parseConds(m.get("conditions")));
    }

    private static List<TooltipLine> parseTextValue(Object val) {
        if (val instanceof String s) return List.of(TooltipLine.of(s));
        if (val instanceof List<?> l) return parseLines(l);
        return List.of();
    }

    private static List<TooltipLine> parseLines(List<?> list) {
        List<TooltipLine> r = new ArrayList<>();
        for (Object item : list) {
            switch (item) {
                case String s -> r.add(TooltipLine.of(s));
                case Map<?, ?> lm -> {
                    String text = str(lm.get("text"));
                    if (text == null) text = "";
                    r.add(new TooltipLine(text, str(lm.get("color")),
                        (Boolean) lm.get("bold"), (Boolean) lm.get("italic"),
                        (Boolean) lm.get("underlined"), (Boolean) lm.get("strikethrough"),
                        str(lm.get("font"))));
                }
                case null, default -> {
                }
            }
        }
        return r;
    }

    private static List<TooltipEntry.Condition> parseConds(Object v) {
        if (!(v instanceof Map<?, ?> cm)) return List.of();
        List<TooltipEntry.Condition> r = new ArrayList<>();
        cm.forEach((k, val) -> {
            if (k != null) r.add(new TooltipEntry.Condition(k.toString(), val));
        });
        return r;
    }

    private static boolean isMeta(String k) {
        return switch (k) {
            case "shift", "prepend", "color", "nbt", "conditions" -> true;
            default -> false;
        };
    }

    private static String str(Object v) {
        return v instanceof String s && !s.isEmpty() ? s : null;
    }

    private static boolean bool(Object v) {
        return Boolean.TRUE.equals(v);
    }
}
