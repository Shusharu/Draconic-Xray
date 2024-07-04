package com.ingresso.draconicxray;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class OreTags {

    public static final TagKey<Block> NETHERITE_SCRAP = forgeTag("ores/netherite_scrap");
    public static final TagKey<Block> QUARTZ_ORE = forgeTag("ores/quartz");
    public static final TagKey<Block> SILVER_ORE = forgeTag("ores/silver");
    public static final TagKey<Block> ZINC_ORE = forgeTag("ores/zinc");
    public static final TagKey<Block> ALUMINIUM_ORE = forgeTag("ores/aluminum");
    public static final TagKey<Block> URANIUM_ORE = forgeTag("ores/uranium");
    public static final TagKey<Block> SULFUR_ORE = forgeTag("ores/netherite_scrap");
    public static final TagKey<Block> NICKEL_ORE = forgeTag("ores/nickel");
    public static final TagKey<Block> TIN_ORE = forgeTag("ores/tin");
    public static final TagKey<Block> COBALT_ORE = forgeTag("ores/cobalt");
    public static final TagKey<Block> LEAD_ORE = forgeTag("ores/lead");
    public static final TagKey<Block> RUBY_ORE = forgeTag("ores/ruby");
    public static final TagKey<Block> SAPPHIRE_ORE = forgeTag("ores/sapphire");

    public static TagKey<Block> forgeTag(String name) {
        return BlockTags.create(new ResourceLocation("forge", name));
    }

}
