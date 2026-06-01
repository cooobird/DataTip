package com.cooobird.datatip.data;

import com.google.common.collect.ListMultimap;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record TooltipEntry(
    String match,
    MatchType matchType,
    ListMultimap<String, TooltipLine> langText,
    boolean shift,
    boolean prepend,
    @Nullable String defaultColor,
    @Nullable Map<String, Object> nbt,
    List<Condition> conditions
) {
    public enum MatchType {EXACT, TAG, WILDCARD}

    public record Condition(String type, Object value) {
    }

    public List<TooltipLine> getText(String lang) {
        List<TooltipLine> t = langText.get(lang);
        if (!t.isEmpty()) return t;
        return langText.asMap().values().stream()
            .findFirst()
            .<List<TooltipLine>>map(ArrayList::new)
            .orElseGet(List::of);
    }

    public boolean wildcardMatch(String id, String pattern) {
        return id.matches(pattern.replace("*", ".*").replace("?", "."));
    }
}
