package ink.myumoon.epiphanyjournal.notification;

import ink.myumoon.epiphany.content.EpiphanyData;
import ink.myumoon.epiphany.content.ModuleData;
import ink.myumoon.epiphany.event.EpiphanyUnlockedEvent;
import ink.myumoon.epiphany.event.InsightPointsChangedEvent;
import ink.myumoon.epiphany.event.ModuleUnlockedEvent;
import ink.myumoon.epiphany.registry.EpiphanyRegistries;
import ink.myumoon.epiphanyjournal.EpiphanyJournal;
import ink.myumoon.epiphanyjournal.Config;
import ink.myumoon.epiphanyjournal.network.EpiphanyNetworkSender;
import ink.myumoon.epiphanyjournal.network.NotificationType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = EpiphanyJournal.MODID)
public final class EpiphanyNotificationListener {
    private EpiphanyNotificationListener() {
    }

    @SubscribeEvent
    static void onInsightPointsChanged(InsightPointsChangedEvent event) {
        if (!Config.ENABLE_LOOT_JOURNAL_NOTIFICATIONS.get()
                || !ink.myumoon.epiphany.Config.NOTIFY_INSIGHT_POINTS.get() || !event.isGain()) {
            return;
        }
        Component message = Component.translatable("epiphany_journal.insight_points")
                .withStyle(ChatFormatting.WHITE);
        EpiphanyNetworkSender.send(event.getPlayer(), message, NotificationType.INSIGHT_POINTS,
                ResourceLocation.withDefaultNamespace("air"), event.getDelta(), event.getNewValue());
    }

    @SubscribeEvent
    static void onModuleUnlocked(ModuleUnlockedEvent event) {
        if (!Config.ENABLE_LOOT_JOURNAL_NOTIFICATIONS.get()
                || event.isSilent() || !ink.myumoon.epiphany.Config.NOTIFY_MODULE_UNLOCK.get()) {
            return;
        }
        ServerPlayer player = event.getPlayer();
        ResourceLocation id = event.getModuleId();
        Registry<ModuleData> registry = player.server.registryAccess()
                .registryOrThrow(EpiphanyRegistries.MODULE_REGISTRY_KEY);
        ModuleData module = registry.get(id);
        Component name = module == null ? Component.literal(id.toString()).withStyle(ChatFormatting.GRAY)
                : module.effectiveName(id);
        Component message = Component.translatable("epiphany_journal.module_unlocked", name)
                .withStyle(ChatFormatting.WHITE);
        EpiphanyNetworkSender.send(player, message, NotificationType.MODULE_UNLOCKED, id, 1, 0);
    }

    @SubscribeEvent
    static void onEpiphanyUnlocked(EpiphanyUnlockedEvent event) {
        if (!Config.ENABLE_LOOT_JOURNAL_NOTIFICATIONS.get()
                || event.isSilent() || !ink.myumoon.epiphany.Config.NOTIFY_EPIPHANY_UNLOCK.get()) {
            return;
        }
        ServerPlayer player = event.getPlayer();
        ResourceLocation id = event.getEpiphanyId();
        Registry<EpiphanyData> registry = player.server.registryAccess()
                .registryOrThrow(EpiphanyRegistries.EPIPHANY_REGISTRY_KEY);
        EpiphanyData epiphany = registry.get(id);
        Component name = epiphany == null ? Component.literal(id.toString()).withStyle(ChatFormatting.GRAY)
                : epiphany.effectiveName(id);
        Component message = Component.translatable("epiphany_journal.epiphany_unlocked", name)
                .withStyle(ChatFormatting.WHITE);
        EpiphanyNetworkSender.send(player, message, NotificationType.EPIPHANY_UNLOCKED, id, 1, 0);
    }
}
