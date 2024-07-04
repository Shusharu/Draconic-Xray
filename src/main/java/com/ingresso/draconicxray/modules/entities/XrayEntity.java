package com.ingresso.draconicxray.modules.entities;

import com.brandon3055.brandonscore.api.power.IOPStorage;
import com.brandon3055.draconicevolution.api.capability.DECapabilities;
import com.brandon3055.draconicevolution.api.modules.Module;
import com.brandon3055.draconicevolution.api.modules.lib.EntityOverridesItemUse;
import com.brandon3055.draconicevolution.api.modules.lib.ModuleContext;
import com.brandon3055.draconicevolution.api.modules.lib.ModuleEntity;
import com.brandon3055.draconicevolution.api.modules.lib.StackModuleContext;
import com.brandon3055.draconicevolution.items.equipment.ModularPickaxe;
import com.ingresso.draconicxray.Config;
import com.ingresso.draconicxray.Draconic;
import com.ingresso.draconicxray.modules.data.XrayData;
import com.ingresso.draconicxray.xray.XrayController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class XrayEntity extends ModuleEntity<XrayData> implements EntityOverridesItemUse {

    private int ticks;

    public XrayEntity(Module<XrayData> module) {
        super(module);
    }

    // The method fired by both sides: client and server;
    // Because of player drops item, xray forever active.
    // TODO: As a solution, install a networking system that starting
    //  and stopping xray-mode from server to client side
    @Override
    public void tick(ModuleContext context) {
        if (context instanceof StackModuleContext ctx && ctx.getEntity() instanceof LocalPlayer) {
            if (XrayController.INSTANCE.isXRayActive()) {
                ticks--;
                if (ticks <= 0) {
                    XrayController.INSTANCE.toggleXRay();
                }
            }
        }
    }

    @Override
    public void onPlayerInteractEvent(PlayerInteractEvent useEvent) {
        if (
                useEvent instanceof PlayerInteractEvent.RightClickItem event &&
                useEvent.getSide().isClient() &&
                event.getItemStack().getItem() instanceof ModularPickaxe
        ) {
            if (!XrayController.INSTANCE.isXRayActive()) {
                ItemStack stack = useEvent.getItemStack();
                LazyOptional<IOPStorage> optional = stack.getCapability(DECapabilities.OP_STORAGE);
                optional.ifPresent(storage -> {
                    int techLevel = module.getModuleTechLevel().index;
                    int energy = Config.energyConsumption * techLevel;
                    if (storage.getOPStored() > energy) {
                        ticks = module.getData().ticks();
                        XrayController.INSTANCE.toggleXRay();
                        storage.modifyEnergyStored(-(long) energy);
                        var player = Minecraft.getInstance().player;
                        var level = player.clientLevel;
                        level.playSound(player, player.getX(), player.getY(), player.getZ(),
                                Draconic.XRAY_ACTIIVATION.get(), SoundSource.PLAYERS, 1F, 1F);
                    }
                });
            }
        }
    }

}
