package com.ingresso.draconicxray.modules.entities;

import com.brandon3055.brandonscore.api.power.IOPStorage;
import com.brandon3055.draconicevolution.api.capability.DECapabilities;
import com.brandon3055.draconicevolution.api.modules.Module;
import com.brandon3055.draconicevolution.api.modules.lib.EntityOverridesItemUse;
import com.brandon3055.draconicevolution.api.modules.lib.ModuleContext;
import com.brandon3055.draconicevolution.api.modules.lib.ModuleEntity;
import com.brandon3055.draconicevolution.items.equipment.ModularPickaxe;
import com.ingresso.draconicxray.Config;
import com.ingresso.draconicxray.Draconic;
import com.ingresso.draconicxray.modules.data.XrayData;
import com.ingresso.draconicxray.network.NetworkHandler;
import com.ingresso.draconicxray.network.packet.S2CXrayStarter;
import com.ingresso.draconicxray.xray.XrayController;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class XrayEntity extends ModuleEntity<XrayData> implements EntityOverridesItemUse {

    public XrayEntity(Module<XrayData> module) {
        super(module);
    }

    @Override
    public void onPlayerInteractEvent(PlayerInteractEvent useEvent) {
        if (
                useEvent instanceof PlayerInteractEvent.RightClickItem event &&
                useEvent.getEntity() instanceof ServerPlayer player &&
                event.getItemStack().getItem() instanceof ModularPickaxe
        ) {
            ItemStack stack = useEvent.getItemStack();
            LazyOptional<IOPStorage> optional = stack.getCapability(DECapabilities.OP_STORAGE);
            optional.ifPresent(storage -> {
                int techLevel = module.getModuleTechLevel().index;
                int energy = Config.energyConsumption * techLevel;
                if (storage.getOPStored() > energy) {
                    int ticks = module.getData().ticks();
                    NetworkHandler.sendToPlayer(() -> player, new S2CXrayStarter(ticks));
                    player.getCooldowns().addCooldown(stack.getItem(), ticks);
                    player.playSound(Draconic.XRAY_ACTIIVATION.get(), 1F, 1F);
                    storage.modifyEnergyStored(-(long) energy);
                }
            });
        }
    }

}
