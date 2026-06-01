package com.cooobird.datatip.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class DatatipConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLED = BUILDER
        .comment("Enable JSON-driven tooltips. When disabled, no custom tooltips are added.")
        .define("enabled", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
