package io.github.theriverelder.steelcraft.items;

import io.github.theriverelder.steelcraft.SteelCraft;
import io.github.theriverelder.steelcraft.data.MaterialInfo;
import io.github.theriverelder.steelcraft.recipe.HammerRecipe;
import io.github.theriverelder.steelcraft.recipe.HammerRecipeInventory;
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
import java.util.Optional;

import static io.github.theriverelder.steelcraft.items.Items.HEATED_IRON_SWORD_PART;

public class HammerItem extends Item {

    private static ItemStack makePart(ItemStack mainMaterial, ItemStack addition) {
        MaterialInfo info = MaterialInfo.fromStack(mainMaterial);

        info.setProcessCount(info.getProcessCount() + 1);
        info.setStress(info.getStress() + 0.1f);
        info.setGrainSize(Math.max(0.0f, info.getGrainSize() - 0.1f));
        if (addition != null && !addition.isEmpty()) {
            info.setImpurityRatio(info.getImpurityRatio() + 0.03f);
        }

        ItemStack result = new ItemStack(HEATED_IRON_SWORD_PART);
        info.setToStack(result);
        return result;
    }



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
        Box box = new Box(pos.up());

        List<ItemEntity> itemEntities =  world.getEntitiesByClass(ItemEntity.class, box, (ItemEntity e) -> true);
        if (itemEntities == null || itemEntities.size() == 0 || itemEntities.get(0) == null) return ActionResult.PASS;

        ItemEntity itemEntity = itemEntities.get(0);
        ItemStack mainMaterial = itemEntity.getStack();
        ItemStack addon = miner.getOffHandStack();

        HammerRecipeInventory inventory = new HammerRecipeInventory(mainMaterial, addon);
        Optional<HammerRecipe> recipeOpt = world.getRecipeManager().getFirstMatch(SteelCraft.HAMMER_RECIPE_TYPE, inventory, world);
        if (recipeOpt.isEmpty()) return ActionResult.FAIL;
        HammerRecipe recipe = recipeOpt.get();

        recipe.consume(inventory, world);
        ItemStack result = recipe.getOutput();

        result.onCraft(world, miner, result.getCount());
        if (mainMaterial.isEmpty()) {
            itemEntity.setStack(result);
        } else if (!result.isEmpty()){
            ItemEntity newItemEntity = new ItemEntity(world, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), result);
            world.spawnEntity(newItemEntity);
        }
        miner.playSound(SoundEvents.BLOCK_ANVIL_USE, 1, 1);
        return ActionResult.SUCCESS;

    }
}
