package io.github.theriverelder.steelcraft;

import io.github.theriverelder.steelcraft.blocks.Blocks;
import io.github.theriverelder.steelcraft.blocks.ThermoprocessingMachineBlock;
import io.github.theriverelder.steelcraft.blocks.ThermoprocessingMachineBlockEntity;
import io.github.theriverelder.steelcraft.items.HammerItem;
import io.github.theriverelder.steelcraft.items.Items;
import io.github.theriverelder.steelcraft.worldgen.OreGen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.github.theriverelder.steelcraft.items.Items.*;

public class SteelCraft implements ModInitializer {

    public static final String ID = "steelcraft";

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(ID, "general"), () -> new ItemStack(Items.THERMOPROCESSING_MACHINE));

    public static final Logger LOGGER = LogManager.getLogger(ID);

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier(ID, "hammer"), HAMMER);
        Registry.register(Registry.ITEM, new Identifier(ID, "carbon_dust"), CARBON_DUST);
        Registry.register(Registry.ITEM, new Identifier(ID, "heated_iron_ingot"), HEATED_IRON_INGOT);
        Registry.register(Registry.ITEM, new Identifier(ID, "heated_iron_sword_part"), HEATED_IRON_SWORD_PART);
        Registry.register(Registry.ITEM, new Identifier(ID, "iron_sword_part"), IRON_SWORD_PART);
        Registry.register(Registry.ITEM, new Identifier(ID, "pyrite_ore"), PYRITE_ORE);
        Registry.register(Registry.ITEM, new Identifier(ID, "raw_pyrite"), RAW_PYRITE);
        Registry.register(Registry.ITEM, new Identifier(ID, "pyrite_ingot"), PYRITE_INGOT);
        Registry.register(Registry.ITEM, new Identifier(ID, "thermoprocessing_machine"), THERMOPROCESSING_MACHINE);

        Registry.register(Registry.BLOCK, new Identifier(ID, "thermoprocessing_machine"), Blocks.THERMOPROCESSING_MACHINE);
        Registry.register(Registry.BLOCK, new Identifier(ID, "pyrite_ore"), Blocks.PYRITE_ORE);

        ThermoprocessingMachineBlock.ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(ID, "thermoprocessing_machine_entity"), FabricBlockEntityTypeBuilder.create(ThermoprocessingMachineBlockEntity::new, Blocks.THERMOPROCESSING_MACHINE).build(null));

        OreGen.register();

        HammerItem.setupRecipes();
    }
}
