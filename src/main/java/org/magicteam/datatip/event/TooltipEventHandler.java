package org.magicteam.datatip.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.magicteam.datatip.client.key.DatatipKeyMappings;
import org.magicteam.datatip.config.DatatipConfig;
import org.magicteam.datatip.data.TooltipEntry;
import org.magicteam.datatip.data.TooltipLoader;
import org.magicteam.datatip.tooltip.TooltipMatcher;
import org.magicteam.datatip.tooltip.TooltipRenderer;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(value = Dist.CLIENT)
public class TooltipEventHandler {

    public static final TooltipLoader LOADER = new TooltipLoader();

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (!DatatipConfig.ENABLED.get()) return;

        LOADER.loadIfNeeded(Minecraft.getInstance().getResourceManager());

        ItemStack stack = event.getItemStack();
        List<TooltipEntry> matched = TooltipMatcher.matchAll(stack, LOADER.getEntries());
        if (matched.isEmpty()) return;

        boolean anyShift = matched.stream().anyMatch(TooltipEntry::shift);
        if (anyShift && !isShowTipDown()) {
            event.getToolTip().add(Component.translatable("tooltip.datatip.hold_shift",
                    DatatipKeyMappings.SHOW_TIP.getTranslatedKeyMessage())
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

        if (matched.getFirst().prepend()) {
            event.getToolTip().addAll(1, custom);
        } else {
            event.getToolTip().addAll(custom);
        }
    }

    private static boolean isShowTipDown() {
        var window = Minecraft.getInstance().getWindow().getWindow();
        return InputConstants.isKeyDown(window, DatatipKeyMappings.SHOW_TIP.getKey().getValue());
    }
}
