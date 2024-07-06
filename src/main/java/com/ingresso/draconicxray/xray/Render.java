package com.ingresso.draconicxray.xray;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;

public class Render {

    private static VertexBuffer vertexBuffer;

	public static void renderBlocks(RenderLevelStageEvent event) {
        if (vertexBuffer == null) {
            vertexBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        }
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();

        float opacity = 1F;

        buffer.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

        XrayController.INSTANCE.syncRenderList.forEach(blockProps -> {
            if (blockProps == null) {
                return;
            }

            final float size = 1.0f;
            final double x = blockProps.pos().getX(), y = blockProps.pos().getY(), z = blockProps.pos().getZ();

            final float red = (blockProps.color() >> 16 & 0xff) / 255f;
            final float green = (blockProps.color() >> 8 & 0xff) / 255f;
            final float blue = (blockProps.color() & 0xff) / 255f;

            LevelRenderer.renderLineBox(buffer, x, y, z, x + size, y + size, z + size,
                    red, green, blue, opacity);
//                renderCuboid(buffer, (float) x, (float) y, (float) z, size, red, green, blue, opacity);
        });

        vertexBuffer.bind();
        vertexBuffer.upload(buffer.end());
        VertexBuffer.unbind();

        Vec3 view = Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition();

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.disableDepthTest();
        RenderSystem.disableCull();

        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        PoseStack matrix = event.getPoseStack();
        matrix.pushPose();
        matrix.translate(-view.x, -view.y, -view.z);

        vertexBuffer.bind();
        vertexBuffer.drawWithShader(matrix.last().pose(),
                new Matrix4f(event.getProjectionMatrix()), RenderSystem.getShader());
        VertexBuffer.unbind();
        matrix.popPose();

        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
	}

//    public static void renderCuboid(BufferVertexConsumer buffer, float x, float y, float z,
//                                    float size, float red, float green, float blue, float opacity) {
//        buffer.vertex(x, y, z + size).color(red, green, blue, opacity).endVertex();
//        buffer.vertex(x + size, y, z + size).color(red, green, blue, opacity).endVertex();
//        buffer.vertex(x + size, y, z).color(red, green, blue, opacity).endVertex();
//        buffer.vertex(x, y, z).color(red, green, blue, opacity).endVertex();
//
//        buffer.vertex(x, y + size, z + size).color(red, green, blue, opacity).endVertex();
//        buffer.vertex(x + size, y + size, z + size).color(red, green, blue, opacity).endVertex();
//        buffer.vertex(x + size, y + size, z).color(red, green, blue, opacity).endVertex();
//        buffer.vertex(x, y + size, z).color(red, green, blue, opacity).endVertex();
//
//        buffer.vertex(x + size, y, z).color(red, green, blue, opacity).endVertex();
//        buffer.vertex(x, y, z).color(red, green, blue, opacity).endVertex();
//        buffer.vertex(x, y + size, z).color(red, green, blue, opacity).endVertex();
//        buffer.vertex(x + size, y + size, z).color(red, green, blue, opacity).endVertex();
//
//        buffer.vertex(x + size, y + size, z + size).color(red, green, blue, opacity).endVertex();
//        buffer.vertex(x, y + size, z + size).color(red, green, blue, opacity).endVertex();
//        buffer.vertex(x, y, z + size).color(red, green, blue, opacity).endVertex();
//        buffer.vertex(x + size, y, z + size).color(red, green, blue, opacity).endVertex();
//
//        buffer.vertex(x, y + size, z + size).color(red, green, blue, opacity).endVertex();
//        buffer.vertex(x, y + size, z).color(red, green, blue, opacity).endVertex();
//        buffer.vertex(x, y, z).color(red, green, blue, opacity).endVertex();
//        buffer.vertex(x, y, z + size).color(red, green, blue, opacity).endVertex();
//
//        buffer.vertex(x + size, y, z + size).color(red, green, blue, opacity).endVertex();
//        buffer.vertex(x + size, y, z).color(red, green, blue, opacity).endVertex();
//        buffer.vertex(x + size, y + size, z).color(red, green, blue, opacity).endVertex();
//        buffer.vertex(x + size, y + size, z + size).color(red, green, blue, opacity).endVertex();
//    }
}
