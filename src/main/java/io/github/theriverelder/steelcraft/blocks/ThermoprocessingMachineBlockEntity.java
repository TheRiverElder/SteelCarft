package io.github.theriverelder.steelcraft.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static io.github.theriverelder.steelcraft.items.Items.HEATED_IRON_INGOT;
import static io.github.theriverelder.steelcraft.items.Items.SHAPED_HEATED_IRON_INGOT;

public class ThermoprocessingMachineBlockEntity extends BlockEntity {

    public static final String KEY_PROCESSING_STACK = "processing_stack";
    public static final String KEY_PROGRESS = "progress";

    private ItemStack processingStack = ItemStack.EMPTY;
    private int progress = 0;

    public ThermoprocessingMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ThermoprocessingMachineBlock.ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ThermoprocessingMachineBlockEntity be) {
        if (be.processingStack.isOf(SHAPED_HEATED_IRON_INGOT) && be.progress < 200 && !state.get(ThermoprocessingMachineBlock.OPEN)) {
            be.progress += 1;
            if (be.progress == 200) {
                be.processingStack = new ItemStack(Items.IRON_SWORD);
                be.markDirty();
                world.setBlockState(pos, state.with(ThermoprocessingMachineBlock.OPEN, true));
            }
        }
    }

    public ItemStack getProcessingStack() {
        return processingStack;
    }

    public boolean setProcessingStackFromPlayerHand(PlayerEntity player) {
        System.out.println("setProcessingStackFromPlayerHand");
        ItemStack mainHandStack = player.getMainHandStack();
        if (mainHandStack.isEmpty()) return false;

        if (processingStack != null && !processingStack.isEmpty()) return false;
        this.processingStack = mainHandStack.copy();
        mainHandStack.setCount(0);
        progress = 0;
        markDirty();
        return true;
    }

    public boolean takeProcessingStack(PlayerEntity player) {
        System.out.println("takeProcessingStack");
        if (processingStack != null && !processingStack.isEmpty()) {
            player.giveItemStack(processingStack);
            this.processingStack = ItemStack.EMPTY;
            progress = 0;
            markDirty();
            return true;
        } else return false;
    }


    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.put(KEY_PROCESSING_STACK, processingStack.getOrCreateNbt());
        nbt.putInt(KEY_PROGRESS, progress);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        processingStack = ItemStack.fromNbt(nbt.getCompound(KEY_PROCESSING_STACK));
        progress = nbt.getInt(KEY_PROGRESS);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
