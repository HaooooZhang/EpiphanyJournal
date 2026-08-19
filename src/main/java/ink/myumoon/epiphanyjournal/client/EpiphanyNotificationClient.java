package ink.myumoon.epiphanyjournal.client;

import ink.myumoon.epiphanyjournal.network.EpiphanyNotificationPayload;
import net.minecraft.client.player.AbstractClientPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class EpiphanyNotificationClient {
    private EpiphanyNotificationClient() {
    }

    public static void handle(final EpiphanyNotificationPayload payload, final IPayloadContext context) {
        if (context.player() instanceof AbstractClientPlayer player) {
            EpiphanyPickupEvent event = new EpiphanyPickupEvent(
                    player,
                    payload.message(),
                    payload.notificationType(),
                    payload.entryId(),
                    payload.amount(),
                    payload.currentValue());
            event.show();
        }
    }
}
