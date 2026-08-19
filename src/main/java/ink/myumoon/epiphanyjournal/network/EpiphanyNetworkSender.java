package ink.myumoon.epiphanyjournal.network;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

public final class EpiphanyNetworkSender {
    private EpiphanyNetworkSender() {
    }

    public static void send(ServerPlayer player, Component message, NotificationType type,
                            ResourceLocation entryId, int amount, int currentValue) {
        PacketDistributor.sendToPlayer(player,
                new EpiphanyNotificationPayload(message, type, entryId, amount, currentValue));
    }
}
