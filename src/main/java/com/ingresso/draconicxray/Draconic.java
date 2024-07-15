package com.ingresso.draconicxray;

import com.brandon3055.brandonscore.api.TechLevel;
import com.brandon3055.draconicevolution.api.DraconicAPI;
import com.brandon3055.draconicevolution.api.modules.Module;
import com.brandon3055.draconicevolution.api.modules.ModuleCategory;
import com.brandon3055.draconicevolution.api.modules.ModuleType;
import com.brandon3055.draconicevolution.api.modules.items.ModuleItem;
import com.brandon3055.draconicevolution.api.modules.lib.ModuleImpl;
import com.brandon3055.draconicevolution.api.modules.types.ModuleTypeImpl;
import com.brandon3055.draconicevolution.init.DEModules;
import com.ingresso.draconicxray.handlers.ClientHandler;
import com.ingresso.draconicxray.modules.data.XrayData;
import com.ingresso.draconicxray.modules.entities.XrayEntity;
import com.ingresso.draconicxray.network.NetworkHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

@Mod(Draconic.MODID)
public class Draconic {

    public static final String MODID = "draconicxray";
    public static final DeferredRegister<Module<?>> MODULES = DeferredRegister
            .create(DEModules.MODULE_KEY, Draconic.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister
            .create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister
            .create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister
            .create(ForgeRegistries.SOUND_EVENTS, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, MODID);

    public static Function<Module<XrayData>, XrayData> xrayData(short ticks) {
        return e -> new XrayData(ticks);
    }
    public static final ModuleType<XrayData> XRAY = new ModuleTypeImpl<>("xray", 3, 3,
            XrayEntity::new, ModuleCategory.MINING_TOOL).setMaxInstallable(1);
    public static final RegistryObject<Module<?>> WYVERN_XRAY = MODULES.register("wyvern_xray",
            () -> new ModuleImpl<>(
                    XRAY,
                    TechLevel.WYVERN,
                    xrayData((short) 100)
            )
    );
    public static final RegistryObject<Module<?>> DRACONIC_XRAY = MODULES.register("draconic_xray",
            () -> new ModuleImpl<>(
                    XRAY,
                    TechLevel.DRACONIC,
                    xrayData((short) 200)
            )
    );
    public static final RegistryObject<Module<?>> CHAOTIC_XRAY = MODULES.register("chaotic_xray",
            () -> new ModuleImpl<>(
                    XRAY,
                    TechLevel.CHAOTIC,
                    xrayData((short) 300)
            )
    );
    public static final RegistryObject<ModuleItem<?>> ITEM_WYVERN_XRAY = ITEMS.register(
            "item_wyvern_xray", () -> new ModuleItem<>(WYVERN_XRAY));
    public static final RegistryObject<ModuleItem<?>> ITEM_DRACONIC_XRAY = ITEMS.register(
            "item_draconic_xray", () -> new ModuleItem<>(DRACONIC_XRAY));
    public static final RegistryObject<ModuleItem<?>> ITEM_CHAOTIC_XRAY = ITEMS.register(
            "item_chaotic_xray", () -> new ModuleItem<>(CHAOTIC_XRAY));
    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register(
            "draconicxray_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.draconicxray.modules"))
                    .icon(() -> ITEM_WYVERN_XRAY.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ITEM_WYVERN_XRAY.get());
                        output.accept(ITEM_DRACONIC_XRAY.get());
                        output.accept(ITEM_CHAOTIC_XRAY.get());
                    })
                    .build()
    );
    public static final RegistryObject<SoundEvent> XRAY_ACTIIVATION = SOUNDS.register(
            "xray_activation",
            () -> SoundEvent.createFixedRangeEvent(
                    new ResourceLocation(MODID, "xray_activation"),
                    16F
            )
    );

    public Draconic() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        DraconicAPI.addModuleProvider(Draconic.MODID);  // Without that ModuleEntity rendering wrong in ModularItemGui
        MODULES.register(modEventBus);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        SOUNDS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        NetworkHandler.registerMessage();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientHandler::setup);
    }

//    private void commonSetup(final FMLCommonSetupEvent event) {}

}
