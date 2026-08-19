package ink.myumoon.epiphanyjournal.client;

import dev.obscuria.lootjournal.client.events.PickupEvent;
import dev.obscuria.lootjournal.client.renderer.PickupComponent;
import dev.obscuria.lootjournal.client.renderer.PickupRenderer;
import dev.obscuria.lootjournal.client.themes.styles.PickupStyle;
import ink.myumoon.epiphany.client.EpiphanyIcons;
import ink.myumoon.epiphany.content.EpiphanyData;
import ink.myumoon.epiphany.content.ModuleData;
import ink.myumoon.epiphany.registry.EpiphanyRegistries;
import ink.myumoon.epiphanyjournal.network.NotificationType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class EpiphanyPickupEvent implements PickupEvent {
    private final AbstractClientPlayer player;
    private final Component message;
    private final NotificationType type;
    private final ResourceLocation entryId;
    private final int amount;
    private final int currentValue;

    public EpiphanyPickupEvent(AbstractClientPlayer player, Component message, NotificationType type,
                               ResourceLocation entryId, int amount, int currentValue) {
        this.player = player;
        this.message = message;
        this.type = type;
        this.entryId = entryId;
        this.amount = amount;
        this.currentValue = currentValue;
    }

    public void show() {
        PickupComponent.handleEvent(this);
    }

    @Override
    public void bind(PickupStyle style) {
    }

    @Override
    public void renderIcon(GuiGraphics graphics, PickupRenderer renderer) {
        EpiphanyIcons.ResolvedIcon icon = resolveIcon();
        if (icon instanceof EpiphanyIcons.ItemIcon(ItemStack stack)) {
            graphics.renderFakeItem(stack, -8, -8);
        } else if (icon instanceof EpiphanyIcons.TextureIcon(ResourceLocation texture)) {
            graphics.blit(texture, -8, -8, 0, 0, 16, 16, 16, 16);
        }
    }

    private EpiphanyIcons.ResolvedIcon resolveIcon() {
        if (type == NotificationType.MODULE_UNLOCKED) {
            ModuleData module = player.level().registryAccess()
                    .registryOrThrow(EpiphanyRegistries.MODULE_REGISTRY_KEY).get(entryId);
            return module == null ? new EpiphanyIcons.ItemIcon(EpiphanyIcons.defaultModule())
                    : EpiphanyIcons.resolve(module, entryId);
        }
        if (type == NotificationType.EPIPHANY_UNLOCKED) {
            EpiphanyData epiphany = player.level().registryAccess()
                    .registryOrThrow(EpiphanyRegistries.EPIPHANY_REGISTRY_KEY).get(entryId);
            return epiphany == null ? new EpiphanyIcons.ItemIcon(EpiphanyIcons.defaultEpiphany())
                    : EpiphanyIcons.resolve(epiphany, entryId);
        }
        return new EpiphanyIcons.ItemIcon(Items.COMPASS.getDefaultInstance());
    }

    @Override
    public boolean maybeMerge(PickupEvent event) {
        return false;
    }

    @Override
    public boolean supportsTotalCount() {
        return type == NotificationType.INSIGHT_POINTS;
    }

    @Override
    public Component displayName() {
        return message;
    }

    @Override
    public AbstractClientPlayer player() {
        return player;
    }

    @Override
    public int count() {
        return amount;
    }

    @Override
    public int total() {
        return currentValue;
    }
}
