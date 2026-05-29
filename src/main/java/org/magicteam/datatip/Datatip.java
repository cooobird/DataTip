package org.magicteam.datatip;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.magicteam.datatip.config.DatatipConfig;
import org.slf4j.Logger;

@Mod(Datatip.MODID)
public class Datatip {
    public static final String MODID = "datatip";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Datatip(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, DatatipConfig.SPEC);
        LOGGER.info("DataTip loaded");
    }
}
