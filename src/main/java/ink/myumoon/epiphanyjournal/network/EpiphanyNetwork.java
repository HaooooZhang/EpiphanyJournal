package ink.myumoon.epiphanyjournal.network;

import ink.myumoon.epiphanyjournal.EpiphanyJournal;
import ink.myumoon.epiphanyjournal.client.EpiphanyNotificationClient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = EpiphanyJournal.MODID)
public final class EpiphanyNetwork {
    private static final String NETWORK_VERSION = "1";

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(NETWORK_VERSION);
        registrar.playToClient(
                EpiphanyNotificationPayload.TYPE,
                EpiphanyNotificationPayload.STREAM_CODEC,
                EpiphanyNotificationClient::handle);
    }
}
