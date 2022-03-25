package io.github.theriverelder.steelcraft.items;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;

public class HeatedPartItem extends Item {
    public HeatedPartItem(Settings settings) {
        super(settings);
    }

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        super.onItemEntityDestroyed(entity);
    }
}
