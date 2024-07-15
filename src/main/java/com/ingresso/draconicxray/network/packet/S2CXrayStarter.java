package com.ingresso.draconicxray.network.packet;

import com.ingresso.draconicxray.Draconic;
import com.ingresso.draconicxray.handlers.ClientHandler;
import com.ingresso.draconicxray.xray.XrayController;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CXrayStarter {

    private final int ticks;

    public S2CXrayStarter(int ticks) {
        this.ticks = ticks;
    }

    public S2CXrayStarter(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    public static void encode(S2CXrayStarter message, FriendlyByteBuf buf) {
        buf.writeInt(message.ticks);
    }

    public static void handle(S2CXrayStarter message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientHandler.ticks = message.ticks;
            var player = Minecraft.getInstance().player;
            var level = player.clientLevel;
            level.playSound(player, player.getX(), player.getY(), player.getZ(),
                    Draconic.XRAY_ACTIIVATION.get(), SoundSource.PLAYERS, 1F, 1F);
            XrayController.INSTANCE.toggleXRay();
        });
        ctx.get().setPacketHandled(true);
    }

}
