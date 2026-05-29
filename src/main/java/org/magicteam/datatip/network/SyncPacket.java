package org.magicteam.datatip.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.magicteam.datatip.event.TooltipEventHandler;

public record SyncPacket(String json) implements CustomPacketPayload {
    public static final Type<SyncPacket> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath("datatip", "sync"));

    public static final StreamCodec<FriendlyByteBuf, SyncPacket> STREAM_CODEC =
        new StreamCodec<>() {
            @Override
            public SyncPacket decode(FriendlyByteBuf buf) {
                return new SyncPacket(buf.readUtf());
            }

            @Override
            public void encode(FriendlyByteBuf buf, SyncPacket pkt) {
                buf.writeUtf(pkt.json());
            }
        };

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SyncPacket packet, net.neoforged.neoforge.network.handling.IPayloadContext ctx) {
        ctx.enqueueWork(() -> TooltipEventHandler.DATA.loadFromString(packet.json()));
    }
}
