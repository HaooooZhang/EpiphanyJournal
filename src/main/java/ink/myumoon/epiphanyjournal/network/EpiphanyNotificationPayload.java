package ink.myumoon.epiphanyjournal.network;

import ink.myumoon.epiphanyjournal.EpiphanyJournal;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record EpiphanyNotificationPayload(
        Component message,
        NotificationType notificationType,
        ResourceLocation entryId,
        int amount,
        int currentValue)
        implements CustomPacketPayload {
    public static final Type<EpiphanyNotificationPayload> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(EpiphanyJournal.MODID, "epiphany_notification"));

    public static final StreamCodec<RegistryFriendlyByteBuf, EpiphanyNotificationPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ComponentSerialization.STREAM_CODEC,
                    EpiphanyNotificationPayload::message,
                    ByteBufCodecs.idMapper(index -> NotificationType.values()[index], NotificationType::ordinal),
                    EpiphanyNotificationPayload::notificationType,
                    ResourceLocation.STREAM_CODEC,
                    EpiphanyNotificationPayload::entryId,
                    ByteBufCodecs.VAR_INT,
                    EpiphanyNotificationPayload::amount,
                    ByteBufCodecs.VAR_INT,
                    EpiphanyNotificationPayload::currentValue,
                    EpiphanyNotificationPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
