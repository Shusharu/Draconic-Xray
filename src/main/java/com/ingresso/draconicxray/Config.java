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
            .defineInRange("energyConsumption", 4096, 0, Integer.MAX_VALUE);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int energyConsumption;

    @SubscribeEvent
    static void onLoad(ModConfigEvent event) {
        energyConsumption = ENERGY_CONSUMPTION.get();
    }

}
