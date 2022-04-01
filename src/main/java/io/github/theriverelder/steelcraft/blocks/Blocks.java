package io.github.theriverelder.steelcraft.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;

public class Blocks {

    public static final Block THERMOPROCESSING_MACHINE = new ThermoprocessingMachineBlock(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
    public static final Block PYRITE_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE).hardness(4.0f));

}
