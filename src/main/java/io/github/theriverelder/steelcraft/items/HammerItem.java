package io.github.theriverelder.steelcraft.items;

import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.github.theriverelder.steelcraft.items.Items.*;

public class HammerItem extends Item {
    public HammerItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
//        System.out.println("HammerItem useOnBlock");
        var stack = context.getStack();
        var pos = context.getBlockPos();
        var world = context.getWorld();
        var state = world.getBlockState(pos);
        var miner = context.getPlayer();
        var side = context.getSide();

        if (miner == null) return ActionResult.FAIL;

        if (!state.isOf(Blocks.ANVIL) || side != Direction.UP) return ActionResult.PASS;
//        System.out.println("is anvil up");
        Box box = new Box(pos.up());
        List<Item> validItems = new ArrayList<>();
        validItems.add(HEATED_IRON_INGOT);
        validItems.add(net.minecraft.item.Items.COAL);
        validItems.add(net.minecraft.item.Items.CHARCOAL);
        List<ItemEntity> itemEntities =  world.getEntitiesByClass(ItemEntity.class, box, (ItemEntity e) -> validItems.stream().anyMatch(i -> e.getStack().isOf(i)));
//        System.out.println(itemEntities);
        if (itemEntities == null || itemEntities.size() == 0 || itemEntities.get(0) == null) return ActionResult.PASS;

        ItemEntity itemEntity = itemEntities.get(0);
        ItemStack entityItemStack = itemEntity.getStack();
        if (entityItemStack.isOf(HEATED_IRON_INGOT)) {
            ItemStack offHandStack = miner.getOffHandStack();
            if (!offHandStack.isOf(CARBON_DUST) || offHandStack.getCount() <= 0) return ActionResult.FAIL;
            itemEntity.setStack(new ItemStack(HEATED_IRON_SWORD_PART, entityItemStack.getCount()));
            offHandStack.setCount(offHandStack.getCount() - 1);
        } else if (entityItemStack.isOf(net.minecraft.item.Items.COAL) || entityItemStack.isOf(net.minecraft.item.Items.CHARCOAL)) {
            itemEntity.setStack(new ItemStack(CARBON_DUST, entityItemStack.getCount()));
        }

        miner.playSound(SoundEvents.BLOCK_ANVIL_USE, 1, 1);

        return ActionResult.SUCCESS;
    }
}
