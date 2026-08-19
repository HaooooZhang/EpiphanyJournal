package ink.myumoon.epiphanyjournal;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(EpiphanyJournal.MODID)
public final class EpiphanyJournal {
    public static final String MODID = "epiphany_journal";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EpiphanyJournal(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
