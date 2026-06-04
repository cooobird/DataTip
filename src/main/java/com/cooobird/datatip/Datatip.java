package com.cooobird.datatip;

import com.cooobird.datatip.config.DatatipConfig;
import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(Datatip.MODID)
public class Datatip {
    public static final String MODID = "datatip";
    private static final Logger LOGGER = LogUtils.getLogger();

    @SuppressWarnings("removal")
    public Datatip() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DatatipConfig.SPEC);
        LOGGER.info("DataTip loaded");
    }
}
