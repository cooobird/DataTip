package com.cooobird.datatip.client;

import com.cooobird.datatip.Datatip;
import com.cooobird.datatip.internal.util.SchemaExporter;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Datatip.MODID, value = Dist.CLIENT)
public final class DatatipClientCommands {
    private DatatipClientCommands() {
    }

    @SubscribeEvent
    public static void registerClientCommands(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("datatip")
            .then(Commands.literal("export_schema").executes(context -> {
                var output = SchemaExporter.exportDefaultSchema();
                if (output == null) {
                    context.getSource().sendFailure(Component.translatable("command.datatip.export_schema.failed"));
                    return 0;
                }
                context.getSource().sendSuccess(
                    () -> Component.translatable("command.datatip.export_schema.success", output.toString()), false);
                return 1;
            })));
    }

}
