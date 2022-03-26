package io.github.theriverelder.steelcraft.blocks;

import io.github.theriverelder.steelcraft.data.HeatSources;
import io.github.theriverelder.steelcraft.data.MaterialInfo;
import io.github.theriverelder.steelcraft.items.MetalBaseItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ThermoprocessingMachineBlockEntity extends BlockEntity {

    public static final String KEY_PROGRESS = "progress";

    private final ThermoprocessingMachineInventory inventory = new ThermoprocessingMachineInventory();
    private int progress = 0;

    public ThermoprocessingMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ThermoprocessingMachineBlock.ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ThermoprocessingMachineBlockEntity be) {
        be.tick(world, pos, state);
    }

    public float getTemperature(World world, BlockPos pos, BlockState state) {
        float totalHeat = 0f;
        for (Direction face : Direction.values()) {
            BlockState s = world.getBlockState(pos.add(face.getVector()));
            float t = HeatSources.temperatureOf(s.getBlock());
            totalHeat += t;
        }
        return totalHeat / 6;
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        ItemStack processingStack = getProcessingStack();
        if (processingStack.getItem() instanceof MetalBaseItem item && progress < 200 && !state.get(ThermoprocessingMachineBlock.OPEN)) {
            progress += 1;

            MaterialInfo info = MaterialInfo.fromStack(processingStack);
            float temperature = info.getTemperature();
            float tempDiff = getTemperature(world, pos, state) - temperature;
            tempDiff = (float) (tempDiff - tempDiff * Math.pow(0.5f, 1 / 1200.0f));
            info.setTemperature(temperature + tempDiff);
            info.setToStack(processingStack);

            item.tick(processingStack);

            if (progress == 200) {
                ItemStack product = new ItemStack(Items.IRON_SWORD);
                MetalBaseItem.modifyByMaterial(product, MaterialInfo.fromStack(processingStack));
                setProcessingStack(product);
                markDirty();
                world.setBlockState(pos, state.with(ThermoprocessingMachineBlock.OPEN, true));
            }
        }
    }

    public ItemStack getProcessingStack() {
        return Optional.ofNullable(inventory.getStack(0)).orElse(ItemStack.EMPTY);
    }

    public void setProcessingStack(ItemStack processingStack) {
        this.inventory.setStack(0, Optional.ofNullable(processingStack).orElse(ItemStack.EMPTY));
    }

    public boolean setProcessingStackFromPlayerHand(PlayerEntity player) {
        ItemStack mainHandStack = player.getMainHandStack();
        if (mainHandStack.isEmpty()) return false;

        ItemStack processingStack = getProcessingStack();
        if (processingStack != null && !processingStack.isEmpty()) return false;
        this.setProcessingStack(mainHandStack.copy());
        mainHandStack.setCount(0);
        progress = 0;
        markDirty();
        return true;
    }

    public boolean takeProcessingStack(PlayerEntity player) {
        ItemStack processingStack = getProcessingStack();
        if (processingStack != null && !processingStack.isEmpty()) {
            player.giveItemStack(processingStack);
            this.setProcessingStack(ItemStack.EMPTY);
            progress = 0;
            markDirty();
            return true;
        } else return false;
    }


    @Override
    protected void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, inventory.getItems());
        nbt.putInt(KEY_PROGRESS, progress);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory.getItems());
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
