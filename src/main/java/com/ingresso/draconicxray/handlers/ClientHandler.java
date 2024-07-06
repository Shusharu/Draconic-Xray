package com.ingresso.draconicxray.handlers;

import com.ingresso.draconicxray.xray.XrayController;
import com.ingresso.draconicxray.xray.Render;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;

public class ClientHandler {

    public static void setup() {
        MinecraftForge.EVENT_BUS.addListener(ClientHandler::onLevelRender);
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
