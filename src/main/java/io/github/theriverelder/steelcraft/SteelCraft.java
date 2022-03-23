package io.github.theriverelder.steelcraft;

import io.github.theriverelder.steelcraft.blocks.ThermoprocessingMachineBlock;
import io.github.theriverelder.steelcraft.blocks.ThermoprocessingMachineBlockEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.github.theriverelder.steelcraft.blocks.Blocks.THERMOPROCESSING_MACHINE;
import static io.github.theriverelder.steelcraft.items.Items.*;

public class SteelCraft implements ModInitializer {

    public static final String ID = "steelcraft";

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier(ID, "hammer"), HAMMER);
        Registry.register(Registry.ITEM, new Identifier(ID, "heated_iron_ingot"), HEATED_IRON_INGOT);
        Registry.register(Registry.ITEM, new Identifier(ID, "shaped_heated_iron_ingot"), SHAPED_HEATED_IRON_INGOT);
        Registry.register(Registry.ITEM, new Identifier(ID, "thermoprocessing_machine"), new BlockItem(THERMOPROCESSING_MACHINE, new Item.Settings().group(ItemGroup.DECORATIONS)));

        Registry.register(Registry.BLOCK, new Identifier(ID, "thermoprocessing_machine"), THERMOPROCESSING_MACHINE);

        ThermoprocessingMachineBlock.ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(ID, "thermoprocessing_machine_entity"), FabricBlockEntityTypeBuilder.create(ThermoprocessingMachineBlockEntity::new, THERMOPROCESSING_MACHINE).build(null));
    }
}
