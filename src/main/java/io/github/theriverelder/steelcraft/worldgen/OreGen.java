package io.github.theriverelder.steelcraft.worldgen;

import io.github.theriverelder.steelcraft.SteelCraft;
import io.github.theriverelder.steelcraft.blocks.Blocks;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.*;

import java.util.Arrays;

public class OreGen {
    private static final ConfiguredFeature<?, ?> OVERWORLD_PYRITE_ORE_CONFIGURED_FEATURE = new ConfiguredFeature
            (Feature.ORE, new OreFeatureConfig(
                    OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
                    Blocks.PYRITE_ORE.getDefaultState(),
                    9)); // vein size

    public static PlacedFeature OVERWORLD_PYRITE_ORE_PLACED_FEATURE = new PlacedFeature(
            () -> OVERWORLD_PYRITE_ORE_CONFIGURED_FEATURE,
            Arrays.asList(
                    CountPlacementModifier.of(20), // number of veins per chunk
                    SquarePlacementModifier.of(), // spreading horizontally
                    HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(64))
            )); // height

    public static void register() {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(SteelCraft.ID, "overworld_pyrite_ore"), OVERWORLD_PYRITE_ORE_CONFIGURED_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(SteelCraft.ID, "overworld_pyrite_ore"), OVERWORLD_PYRITE_ORE_PLACED_FEATURE);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(SteelCraft.ID, "overworld_pyrite_ore")));
    }
}
