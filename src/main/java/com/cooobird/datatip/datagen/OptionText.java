package com.cooobird.datatip.datagen;

import com.cooobird.datatip.api.text.LocalizedText;
import net.minecraft.network.chat.Component;

import java.util.LinkedHashMap;
import java.util.Map;

final class OptionText {
    private OptionText() {
    }

    static LocalizedText languages(Map<String, String> values) {
        Map<String, Component> components = new LinkedHashMap<>();
        values.forEach((language, text) -> components.put(language, Component.literal(text)));
        return LocalizedText.languages(components);
    }
}
