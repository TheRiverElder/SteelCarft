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

import java.util.List;

import static io.github.theriverelder.steelcraft.items.Items.HEATED_IRON_INGOT;
import static io.github.theriverelder.steelcraft.items.Items.SHAPED_HEATED_IRON_INGOT;

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

        if (!state.isOf(Blocks.ANVIL) || side != Direction.UP) return ActionResult.PASS;
//        System.out.println("is anvil up");
        Box box = new Box(pos.up());
        List<ItemEntity> itemEntities =  world.getEntitiesByClass(ItemEntity.class, box, (ItemEntity e) -> e.getStack().isOf(HEATED_IRON_INGOT));
//        System.out.println(itemEntities);
        if (itemEntities == null || itemEntities.size() == 0 || itemEntities.get(0) == null) return ActionResult.PASS;

        ItemEntity itemEntity = itemEntities.get(0);
        itemEntity.setStack(new ItemStack(SHAPED_HEATED_IRON_INGOT));

        if (miner != null) {
            miner.playSound(SoundEvents.BLOCK_ANVIL_USE, 1, 1);
        }

        return ActionResult.SUCCESS;
    }
}
