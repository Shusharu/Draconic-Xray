package com.ingresso.draconicxray.network;

import com.ingresso.draconicxray.Draconic;
import com.ingresso.draconicxray.network.packet.S2CXrayStarter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class NetworkHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Draconic.MODID, "network"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerMessage() {
        INSTANCE.messageBuilder(S2CXrayStarter.class, 0, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(S2CXrayStarter::encode)
                .decoder(S2CXrayStarter::new)
                .consumerNetworkThread(S2CXrayStarter::handle)
                .add();
    }

    public static <MSG> void sendToPlayer(Supplier<ServerPlayer> player, MSG message) {
        INSTANCE.send(PacketDistributor.PLAYER.with(player), message);
    }
}
