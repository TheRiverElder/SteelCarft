package io.github.theriverelder.steelcraft.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static io.github.theriverelder.steelcraft.items.Items.*;

public class ThermoprocessingMachineBlockEntity extends BlockEntity {

    public static final String KEY_PROCESSING_STACK = "processing_stack";
    public static final String KEY_PROGRESS = "progress";

//    private ItemStack processingStack = ItemStack.EMPTY;
    private final ThermoprocessingMachineInventory inventory = new ThermoprocessingMachineInventory();
    private int progress = 0;

    public ThermoprocessingMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ThermoprocessingMachineBlock.ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ThermoprocessingMachineBlockEntity be) {
        if (be.getProcessingStack().isOf(HEATED_IRON_SWORD_PART) && be.progress < 200 && !state.get(ThermoprocessingMachineBlock.OPEN)) {
            be.progress += 1;
            if (be.progress == 200) {
                ItemStack product = new ItemStack(Items.IRON_SWORD);
                product.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier("generic.attack_damage", 20.0d, EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
                be.setProcessingStack(product);
                be.markDirty();
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
