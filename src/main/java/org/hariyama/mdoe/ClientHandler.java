package org.hariyama.mdoe;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.hariyama.mdoe.network.ModMessages;
import org.hariyama.mdoe.network.PacketPrayerAction;
import org.lwjgl.glfw.GLFW;

public class ClientHandler {
    public static final String KEY_CATEGORY = "key.categories.mdoe";
    public static final String KEY_ACTION = "key.mdoe.action";

    public static final KeyMapping ACTION_KEY = new KeyMapping(
            KEY_ACTION,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            KEY_CATEGORY
    );

    public static void triggerEffect() {
        // send packet to server so server plays sound at player position to all nearby players
        ModMessages.INSTANCE.sendToServer(new PacketPrayerAction());
    }

    @Mod.EventBusSubscriber(modid = MdoeMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
            event.register(ACTION_KEY);
        }
    }

    @Mod.EventBusSubscriber(modid = MdoeMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeBusEvents {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.END) return;

            while (ACTION_KEY.consumeClick()) {
                triggerEffect();
            }
        }
    }
}
