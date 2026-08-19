package ink.myumoon.epiphanyjournal.client;

import ink.myumoon.epiphanyjournal.network.EpiphanyNotificationPayload;
import net.minecraft.client.Minecraft;

public final class EpiphanyNotificationClient {
    private EpiphanyNotificationClient() {
    }

    public static void handle(EpiphanyNotificationPayload payload) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            EpiphanyPickupEvent event = new EpiphanyPickupEvent(
                    minecraft.player,
                    payload.message(),
                    payload.notificationType(),
                    payload.entryId(),
                    payload.amount(),
                    payload.currentValue());
            event.show();
        }
    }
}
