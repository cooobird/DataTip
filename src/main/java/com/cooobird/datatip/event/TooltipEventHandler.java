package com.cooobird.datatip.event;

import com.cooobird.datatip.config.DatatipConfig;
import com.cooobird.datatip.data.TooltipEntry;
import com.cooobird.datatip.data.TooltipLoader;
import com.cooobird.datatip.tooltip.TooltipMatcher;
import com.cooobird.datatip.tooltip.TooltipRenderer;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class TooltipEventHandler {

    private static final Logger LOGGER = LogUtils.getLogger();

    static final KeyMapping SHOW_TIP = new KeyMapping(
        "key.datatip.show_tip",
        InputConstants.KEY_LSHIFT,
        "key.categories.datatip"
    );

    public static final TooltipLoader LOADER = new TooltipLoader();

    @SubscribeEvent
    public static void registerKeyMapping(RegisterKeyMappingsEvent event) {
        event.register(SHOW_TIP);
    }

    @SubscribeEvent
    public static void onRegisterReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener((stage, rm, prepProf, reloadProf, bgExec, gameExec) -> {
            LOGGER.info("DataTip reload triggered");
            return CompletableFuture
                .supplyAsync(() -> null, bgExec)
                .thenCompose(stage::wait)
                .thenRunAsync(() -> {
                    LOGGER.info("DataTip marked dirty");
                    LOADER.markDirty();
                }, gameExec);
        });
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (!DatatipConfig.ENABLED.get()) return;

        LOADER.loadIfNeeded(Minecraft.getInstance().getResourceManager());

        ItemStack stack = event.getItemStack();
        List<TooltipEntry> matched = TooltipMatcher.matchAll(stack, LOADER.getEntries());
        if (matched.isEmpty()) return;

        boolean anyShift = matched.stream().anyMatch(TooltipEntry::shift);
        if (anyShift && !isShowTipDown()) {
            event.getToolTip().add(Component.translatable("tooltip.datatip.hold_shift", SHOW_TIP.getTranslatedKeyMessage())
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
            return;
        }

        List<Component> custom = new ArrayList<>();
        String lang = Minecraft.getInstance().getLanguageManager().getSelected();
        for (TooltipEntry entry : matched) {
            var color = TooltipRenderer.parseColor(entry.defaultColor());
            for (var line : entry.getText(lang)) {
                custom.add(TooltipRenderer.render(line, stack, color));
            }
        }

        if (custom.isEmpty()) return;

        if (matched.get(0).prepend()) {
            event.getToolTip().addAll(1, custom);
        } else {
            event.getToolTip().addAll(custom);
        }
    }

    private static boolean isShowTipDown() {
        var window = Minecraft.getInstance().getWindow().getWindow();
        return InputConstants.isKeyDown(window, SHOW_TIP.getKey().getValue());
    }
}
