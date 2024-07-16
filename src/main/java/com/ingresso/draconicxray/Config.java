package com.ingresso.draconicxray;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = Draconic.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.IntValue ENERGY_CONSUMPTION = BUILDER
            .comment("An energy that xray-mode will consume during the job")
            .defineInRange("energyConsumption", 24_576, 0, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue SCAN_RADIUS = BUILDER
            .comment("A radius scanning ore block's when xray-module works.\n" +
                    "The value installs by count of chunks: 1 = 16 blocks around player")
            .comment("Also not recommended using big values")
            .defineInRange("scanRadius", 2, 0, Integer.MAX_VALUE);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int energyConsumption;
    public static int scanRadius;

    @SubscribeEvent
    static void onLoad(ModConfigEvent event) {
        energyConsumption = ENERGY_CONSUMPTION.get();
        scanRadius = SCAN_RADIUS.get();
    }

}
