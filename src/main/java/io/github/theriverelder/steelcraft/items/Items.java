package io.github.theriverelder.steelcraft.items;

import io.github.theriverelder.steelcraft.SteelCraft;
import io.github.theriverelder.steelcraft.blocks.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class Items {

    public static final Item HAMMER = new HammerItem(new Item.Settings().group(SteelCraft.ITEM_GROUP));
    public static final Item CARBON_DUST = new Item(new Item.Settings().group(SteelCraft.ITEM_GROUP));
    public static final MetalBaseItem HEATED_IRON_INGOT = new MetalBaseItem(new Item.Settings().group(SteelCraft.ITEM_GROUP));
    public static final MetalBaseItem HEATED_IRON_SWORD_PART = new MetalBaseItem(new Item.Settings().group(SteelCraft.ITEM_GROUP).maxCount(1));
    public static final MetalBaseItem IRON_SWORD_PART = new MetalBaseItem(new Item.Settings().group(SteelCraft.ITEM_GROUP).maxCount(1));

    public static final MetalBaseItem RAW_PYRITE = new MetalBaseItem(new Item.Settings().group(SteelCraft.ITEM_GROUP));
    public static final MetalBaseItem PYRITE_INGOT = new MetalBaseItem(new Item.Settings().group(SteelCraft.ITEM_GROUP));

    public static final Item THERMOPROCESSING_MACHINE = new BlockItem(Blocks.THERMOPROCESSING_MACHINE, new Item.Settings().group(SteelCraft.ITEM_GROUP));
    public static final Item PYRITE_ORE = new BlockItem(Blocks.PYRITE_ORE, new Item.Settings().group(SteelCraft.ITEM_GROUP));


}
