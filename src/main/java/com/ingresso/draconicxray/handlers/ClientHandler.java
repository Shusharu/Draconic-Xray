package com.ingresso.draconicxray.handlers;

import com.ingresso.draconicxray.xray.XrayController;
import com.ingresso.draconicxray.xray.Render;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;

public class ClientHandler {

    public static int ticks;

    public static void setup() {
        MinecraftForge.EVENT_BUS.addListener(ClientHandler::onLevelRender);
        MinecraftForge.EVENT_BUS.addListener(ClientHandler::onTickClient);
    }

    public static void onTickClient(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        if (XrayController.INSTANCE.isXRayActive()) {
            ticks--;
            if (ticks <= 0) {
                XrayController.INSTANCE.toggleXRay();
            }
        }
    }

    public static void onLevelRender(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_CUTOUT_BLOCKS) {
            return;
        }
        if (XrayController.INSTANCE.isXRayActive() && Minecraft.getInstance().player != null) {
            Render.renderBlocks(event);
        }
    }

}
