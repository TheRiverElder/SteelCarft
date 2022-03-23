package io.github.theriverelder.steelcraft.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class Items {

    public static final Item HAMMER = new HammerItem(new Item.Settings().group(ItemGroup.TOOLS));
    public static final Item HEATED_IRON_INGOT = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item SHAPED_HEATED_IRON_INGOT = new ShapedHeatedIronIngotItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));


}
