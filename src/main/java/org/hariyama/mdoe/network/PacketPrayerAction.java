package org.hariyama.mdoe.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;
import org.hariyama.mdoe.MdoeMod;

import java.util.function.Supplier;

public class PacketPrayerAction {
    public PacketPrayerAction() {
    }

    public static void encode(PacketPrayerAction msg, FriendlyByteBuf buf) {
    }

    public static PacketPrayerAction decode(FriendlyByteBuf buf) {
        return new PacketPrayerAction();
    }

    public static void handle(PacketPrayerAction msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player != null && player.level() != null) {
                player.level().playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        MdoeMod.BELL_SOUND.get(),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                );
            }
        });
        ctx.setPacketHandled(true);
    }
}
