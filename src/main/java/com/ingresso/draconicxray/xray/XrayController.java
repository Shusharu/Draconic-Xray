package com.ingresso.draconicxray.xray;

import com.ingresso.draconicxray.Config;
import com.ingresso.draconicxray.xray.data.RenderBlockWrapper;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class XrayController {

    public static XrayController INSTANCE = new XrayController();
    public final Set<RenderBlockWrapper> syncRenderList = Collections.synchronizedSet(new HashSet<>()); // this is accessed by threads
    public ArrayList<Block> blackList = new ArrayList<>() {{
        add(Blocks.AIR);
        add(Blocks.BEDROCK);
        add(Blocks.STONE);
        add(Blocks.GRASS);
        add(Blocks.DIRT);
    }};
    private boolean xrayActive = false;

    private XrayController() {}

    public boolean isXRayActive() {
        return xrayActive && Minecraft.getInstance().level != null && Minecraft.getInstance().player != null;
    }

    public void toggleXRay() {
        if (!xrayActive) {
            requestBlockFinder();
            xrayActive = true;
        } else {
            xrayActive = false;
            syncRenderList.clear();
        }
    }

    public int getRadius() {
        return LevelRenderer.CHUNK_SIZE * Config.scanRadius;
    }

    public void requestBlockFinder() {
        Util.backgroundExecutor().execute(() -> {
            Set<RenderBlockWrapper> c = OreBlockFinder.blockFinder();
            syncRenderList.addAll(c);
        });
    }
}
