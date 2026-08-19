package ink.myumoon.epiphanyjournal;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_LOOT_JOURNAL_NOTIFICATIONS = BUILDER
            .comment("Show Epiphany notifications through Loot Journal")
            .define("enableLootJournalNotifications", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
