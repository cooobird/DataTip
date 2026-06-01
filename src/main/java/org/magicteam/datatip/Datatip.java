package org.magicteam.datatip;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import org.magicteam.datatip.config.DatatipConfig;
import org.magicteam.datatip.event.TooltipEventHandler;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

@Mod(Datatip.MODID)
public class Datatip {
    public static final String MODID = "datatip";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Datatip(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, DatatipConfig.SPEC);

        modEventBus.addListener(RegisterClientReloadListenersEvent.class, event ->
            event.registerReloadListener((stage, rm, prepProf, reloadProf, bgExec, gameExec) -> {
                LOGGER.info("DataTip reload triggered");
                return CompletableFuture
                    .supplyAsync(() -> null, bgExec)
                    .thenCompose(stage::wait)
                    .thenRunAsync(() -> {
                        LOGGER.info("DataTip marked dirty");
                        TooltipEventHandler.LOADER.markDirty();
                    }, gameExec);
            })
        );

        LOGGER.info("DataTip loaded");
    }
}
