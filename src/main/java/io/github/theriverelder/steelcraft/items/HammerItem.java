package io.github.theriverelder.steelcraft.items;

import io.github.theriverelder.steelcraft.data.HammerRecipe;
import io.github.theriverelder.steelcraft.data.MaterialInfo;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static io.github.theriverelder.steelcraft.items.Items.*;
import static net.minecraft.item.Items.CHARCOAL;
import static net.minecraft.item.Items.COAL;

public class HammerItem extends Item {

    public static final List<HammerRecipe> RECIPES = new ArrayList<>();

    public static void setupRecipes() {
        RECIPES.add(new HammerRecipe(CHARCOAL, null, (m, a) -> new ItemStack(CARBON_DUST)));
        RECIPES.add(new HammerRecipe(COAL, null, (m, a) -> new ItemStack(CARBON_DUST)));
        RECIPES.add(new HammerRecipe(HEATED_IRON_INGOT, CARBON_DUST, HammerItem::makePart));
    }

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

        List<ItemEntity> itemEntities =  world.getEntitiesByClass(ItemEntity.class, box, (ItemEntity e) -> RECIPES.stream().anyMatch(r -> e.getStack().isOf(r.getMainMaterial())));
        if (itemEntities == null || itemEntities.size() == 0 || itemEntities.get(0) == null) return ActionResult.PASS;

        ItemEntity itemEntity = itemEntities.get(0);
        ItemStack mainMaterial = itemEntity.getStack();

        ItemStack result = craft(mainMaterial, miner.getOffHandStack());

        if (result != null) {
            result.onCraft(world, miner, result.getCount());
            itemEntity.setStack(result);
            miner.playSound(SoundEvents.BLOCK_ANVIL_USE, 1, 1);
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }

    @Nullable
    public ItemStack craft(ItemStack mainMaterial, @Nullable ItemStack addition) {
        for (HammerRecipe r : RECIPES) {
            Item a = r.getAddition();
            if (mainMaterial.isOf(r.getMainMaterial())
                    && (a == null || (addition != null && !addition.isEmpty() && addition.isOf(a)))
            ) {
                ItemStack result = r.getOutput().provide(mainMaterial, addition);
                mainMaterial.setCount(mainMaterial.getCount() - 1);

                if (addition != null && a != null) {
                    addition.setCount(addition.getCount() - 1);
                }
                return result;
            }
        }
        return null;
    }
}
