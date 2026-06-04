package com.cooobird.datatip.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class DatatipConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue ENABLED = BUILDER
        .comment("Enable JSON-driven tooltips. When disabled, no custom tooltips are added.")
        .define("enabled", true);

    public static final ForgeConfigSpec SPEC = BUILDER.build();
}
