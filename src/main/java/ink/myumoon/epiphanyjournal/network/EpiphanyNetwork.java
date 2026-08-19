package ink.myumoon.epiphanyjournal.network;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public final class EpiphanyNetwork {
    private EpiphanyNetwork() {
    }

    public static void register(IEventBus modEventBus, IPayloadHandler<EpiphanyNotificationPayload> handler) {
        modEventBus.addListener(RegisterPayloadHandlersEvent.class,
                event -> registerPayloads(event, handler));
    }

    private static void registerPayloads(RegisterPayloadHandlersEvent event,
                                         IPayloadHandler<EpiphanyNotificationPayload> handler) {
        event.registrar("1").playToClient(
                EpiphanyNotificationPayload.TYPE,
                EpiphanyNotificationPayload.STREAM_CODEC,
                handler);
    }
}
