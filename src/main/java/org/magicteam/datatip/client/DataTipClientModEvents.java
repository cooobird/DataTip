package org.magicteam.datatip.client;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import org.magicteam.datatip.event.TooltipEventHandler;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

public class DataTipClientModEvents {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onRegisterReloadListeners(RegisterClientReloadListenersEvent event) {
        LOGGER.info("DataTip reload listener registered");
        event.registerReloadListener((stage, rm, prepProf, reloadProf, bgExec, gameExec) -> {
            LOGGER.info("DataTip reload() called — scheduling markDirty");
            return CompletableFuture
                .supplyAsync(() -> null, bgExec)
                .thenCompose(stage::wait)
                .thenRunAsync(() -> {
                    LOGGER.info("DataTip markDirty — will reload on next hover");
                    TooltipEventHandler.LOADER.markDirty();
                }, gameExec);
        });
    }
}
