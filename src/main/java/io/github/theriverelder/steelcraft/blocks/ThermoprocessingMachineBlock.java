package io.github.theriverelder.steelcraft.blocks;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static io.github.theriverelder.steelcraft.items.Items.HAMMER;
import static io.github.theriverelder.steelcraft.items.Items.HEATED_IRON_SWORD_PART;

public class ThermoprocessingMachineBlock extends BlockWithEntity implements BlockEntityProvider {

    public static BlockEntityType<ThermoprocessingMachineBlockEntity> ENTITY;

    public static final BooleanProperty OPEN = BooleanProperty.of("open");

    public ThermoprocessingMachineBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(OPEN, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(OPEN);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack mainHandStack = player.getMainHandStack();
        if (mainHandStack == null || mainHandStack.isEmpty()) {
            world.setBlockState(pos, state.with(OPEN, !state.get(OPEN)));
            BlockEntity be = world.getBlockEntity(pos);
            if (!(be instanceof ThermoprocessingMachineBlockEntity entity)) return ActionResult.PASS;
            entity.markDirty();
            return ActionResult.SUCCESS;
        } else if (state.get(OPEN)) {
            BlockEntity be = world.getBlockEntity(pos);
            if (!(be instanceof ThermoprocessingMachineBlockEntity entity)) return ActionResult.PASS;

            if (mainHandStack.isOf(HEATED_IRON_SWORD_PART))
                return entity.setProcessingStackFromPlayerHand(player) ? ActionResult.SUCCESS : ActionResult.FAIL;
            else if (mainHandStack.isOf(HAMMER))
                return entity.takeProcessingStack(player) ? ActionResult.SUCCESS : ActionResult.FAIL;
            else return ActionResult.FAIL;
        } else return ActionResult.FAIL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ThermoprocessingMachineBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        // 由于继承了BlockWithEntity，这个默认为INVISIBLE，所以我们需要更改它！
        return BlockRenderType.MODEL;
    }
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ENTITY, ThermoprocessingMachineBlockEntity::tick);
    }
}
