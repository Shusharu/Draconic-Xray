package com.ingresso.draconicxray.xray;

import com.brandon3055.draconicevolution.init.DETags;
import com.ingresso.draconicxray.OreTags;
import com.ingresso.draconicxray.xray.data.RenderBlockWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class OreBlockFinder {

	public static Set<RenderBlockWrapper> blockFinder() {

		final Level level = Minecraft.getInstance().level;
        final Player player = Minecraft.getInstance().player;

		final Set<RenderBlockWrapper> renderQueue = new HashSet<>();

		int radius = XrayController.INSTANCE.getRadius();

		int cX = player.getBlockX(), cY = player.getBlockY(), cZ = player.getBlockZ();

		BlockState currentState;
		BlockPos pos;
		for (int x = cX - radius; x < cX + radius; x++) {
			for (int z = cZ - radius; z < cZ + radius; z++) {
				for (int y = cY - radius; y < cY + radius; y++) {
					pos = new BlockPos(x, y, z);
					currentState = level.getBlockState(pos);
					if(XrayController.INSTANCE.blackList.contains(currentState.getBlock())) {
						continue;
					}
					int colorBlock = isOreBlock(currentState);
					if (colorBlock != 0) {
						renderQueue.add(new RenderBlockWrapper(pos, colorBlock));
					}
				}
			}
		}
		return renderQueue;
	}

	public static int isOreBlock(BlockState state) {
		if (state.is(BlockTags.COAL_ORES)) {
			return 0x454545;
		} else if (state.is(BlockTags.COPPER_ORES)) {
			return 0xff7b42;
		} else if (state.is(BlockTags.IRON_ORES)) {
			return 0xffdac9;
		} else if (state.is(BlockTags.GOLD_ORES)) {
			return 0xfcad23;
		} else if (state.is(BlockTags.REDSTONE_ORES)) {
			return 0xf7392f;
		} else if (state.is(BlockTags.LAPIS_ORES)) {
			return 0x2f54f7;
		} else if (state.is(BlockTags.DIAMOND_ORES)) {
			return 0x44edfc;
		} else if (state.is(BlockTags.EMERALD_ORES)) {
			return 0x93ff54;
		} else if (state.is(OreTags.QUARTZ_ORE)) {
			return 0xe0d5d3;
		} else if (state.is(OreTags.NETHERITE_SCRAP)) {
			return 0x6e352e;
		} else if (state.is(OreTags.ALUMINIUM_ORE)) {
			return 0xff8680;
		} else if (state.is(OreTags.SILVER_ORE)) {
			return 0xa3a3a3;
		} else if (state.is(OreTags.URANIUM_ORE)) {
			return 0x70c72a;
		} else if (state.is(OreTags.ZINC_ORE)) {
			return 0x74918c;
		} else if (state.is(OreTags.TIN_ORE)) {
			return 0xa1a1a1;
		} else if (state.is(OreTags.COBALT_ORE)) {
			return 0xbec4af;
		} else if (state.is(OreTags.SULFUR_ORE)) {
			return 0xf6ffa8;
		} else if (state.is(OreTags.NICKEL_ORE)) {
			return 0xaeb4c2;
		} else if (state.is(OreTags.LEAD_ORE)) {
			return 0xb3cc91;
		} else if (state.is(OreTags.RUBY_ORE)) {
			return 0xf75e75;
		} else if (state.is(OreTags.SAPPHIRE_ORE)) {
			return 0x8169e0;
		} else if (state.is(DETags.Blocks.ORES_DRACONIUM)) {
			return 0xc15eff;
		}
		return 0;
	}

}
