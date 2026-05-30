package org.magicteam.datatip.client.key;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.magicteam.datatip.Datatip;

@EventBusSubscriber(modid = Datatip.MODID, value = Dist.CLIENT)
public class DatatipKeyMappings {
    public static final KeyMapping SHOW_TIP = new KeyMapping(
        "key.datatip.show_tip",
        InputConstants.KEY_LSHIFT,
        "key.categories.datatip"
    );

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent event) {
        event.register(SHOW_TIP);
    }
}
