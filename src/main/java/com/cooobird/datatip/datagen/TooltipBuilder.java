package com.cooobird.datatip.datagen;

import java.util.*;

public class TooltipBuilder {
    private String key;
    private String defaultColor;
    private boolean shift, prepend;
    private final Map<String, List<Object>> langText = new LinkedHashMap<>();
    private Map<String, Object> nbt;
    private Map<String, Object> conditions;

    public static TooltipBuilder create() {
        return new TooltipBuilder();
    }


    public TooltipBuilder key(String key) {
        this.key = key;
        return this;
    }

    public TooltipBuilder color(String color) {
        this.defaultColor = color;
        return this;
    }

    public TooltipBuilder shift() {
        this.shift = true;
        return this;
    }

    public TooltipBuilder prepend() {
        this.prepend = true;
        return this;
    }

    public TooltipBuilder condition(String type, Object value) {
        if (conditions == null) conditions = new LinkedHashMap<>();
        conditions.put(type, value);
        return this;
    }

    public TooltipBuilder nbt(String key, String value) {
        if (nbt == null) nbt = new LinkedHashMap<>();
        nbt.put(key, value);
        return this;
    }

    /**
     * 无语言区分的简单行
     */
    public TooltipBuilder simpleLine(String... lines) {
        langText.putIfAbsent("", new ArrayList<>());
        for (String s : lines) langText.get("").add(s);
        return this;
    }

    /**
     * 指定语言的纯文本行
     */
    public TooltipBuilder line(String lang, String... lines) {
        langText.putIfAbsent(lang, new ArrayList<>());
        for (String s : lines) langText.get(lang).add(s);
        return this;
    }

    /**
     * 指定语言的带样式行
     */
    public TooltipBuilder line(String lang, TooltipLine... lines) {
        langText.putIfAbsent(lang, new ArrayList<>());
        for (TooltipLine l : lines) langText.get(lang).add(l.toMap());
        return this;
    }

    public Map.Entry<String, Object> build() {
        Map<String, Object> entry = new LinkedHashMap<>();
        if (!langText.isEmpty()) entry.put("text", new LinkedHashMap<>(langText));
        if (defaultColor != null) entry.put("color", defaultColor);
        if (shift) entry.put("shift", true);
        if (prepend) entry.put("prepend", true);
        if (nbt != null) entry.put("nbt", nbt);
        if (conditions != null) entry.put("conditions", conditions);

        // 重置状态，允许复用
        String builtKey = key;
        reset();
        return new AbstractMap.SimpleEntry<>(builtKey, entry);
    }

    private void reset() {
        key = null;
        defaultColor = null;
        shift = false;
        prepend = false;
        langText.clear();
        nbt = null;
        conditions = null;
    }

    /**
     * 行样式构造
     */
    public static class TooltipLine {
        private String text, color, font;
        private Boolean bold, italic, underlined, strikethrough;

        public static TooltipLine of(String text) {
            var l = new TooltipLine();
            l.text = text;
            return l;
        }

        public TooltipLine color(String c) {
            this.color = c;
            return this;
        }

        public TooltipLine bold() {
            this.bold = true;
            return this;
        }

        public TooltipLine italic() {
            this.italic = true;
            return this;
        }

        public TooltipLine underline() {
            this.underlined = true;
            return this;
        }

        public TooltipLine strike() {
            this.strikethrough = true;
            return this;
        }

        public TooltipLine font(String f) {
            this.font = f;
            return this;
        }

        Map<String, Object> toMap() {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("text", text);
            if (color != null) m.put("color", color);
            if (bold != null) m.put("bold", bold);
            if (italic != null) m.put("italic", italic);
            if (underlined != null) m.put("underlined", underlined);
            if (strikethrough != null) m.put("strikethrough", strikethrough);
            if (font != null) m.put("font", font);
            return m;
        }
    }
}
