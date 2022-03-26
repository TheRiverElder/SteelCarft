package io.github.theriverelder.steelcraft.data;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.HashMap;
import java.util.Map;

public class HeatSources {

    private static Map<Block, Float> TEMPERATURE_MAP = new HashMap<>();

    static {
        TEMPERATURE_MAP.put(Blocks.LAVA_CAULDRON, 1400f);
        TEMPERATURE_MAP.put(Blocks.LAVA, 1500f);
        TEMPERATURE_MAP.put(Blocks.FIRE, 1000f);
        TEMPERATURE_MAP.put(Blocks.SOUL_FIRE, 700f);
        TEMPERATURE_MAP.put(Blocks.WATER, 5f);
        TEMPERATURE_MAP.put(Blocks.WATER_CAULDRON, 15f);
        TEMPERATURE_MAP.put(Blocks.ICE, -10f);
        TEMPERATURE_MAP.put(Blocks.PACKED_ICE, -20f);
        TEMPERATURE_MAP.put(Blocks.BLUE_ICE, -30f);
        TEMPERATURE_MAP.put(Blocks.FROSTED_ICE, -10f);
    }

    public static float temperatureOf(Block block) {
        return TEMPERATURE_MAP.getOrDefault(block, 20.0f);
    }
}
