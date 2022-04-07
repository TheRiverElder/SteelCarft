package io.github.theriverelder.steelcraft.recipe;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.item.Items.AIR;

public class HammerRecipeInventory implements Inventory {

    private @NotNull ItemStack material;
    private @NotNull ItemStack addon;

    public HammerRecipeInventory(@NotNull ItemStack material, @NotNull ItemStack addon) {
        this.material = material;
        this.addon = addon;
    }

    public void setMaterial(@NotNull ItemStack material) {
        this.material = material;
    }

    public @NotNull ItemStack getMaterial() {
        return material;
    }

    public void setAddon(@NotNull ItemStack addon) {
        this.addon = addon;
    }

    public @NotNull ItemStack getAddon() {
        return addon;
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return material.isOf(AIR) || material.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return switch (slot) {
            case 0 -> material;
            case 1 -> addon;
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack prev = ItemStack.EMPTY;
        switch (slot) {
            case 0 -> {
                prev = material;
                material = ItemStack.EMPTY;
            }
            case 1 -> {
                prev = addon;
                addon = ItemStack.EMPTY;
            }
        }
        return prev;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        switch (slot) {
            case 0 -> material = stack;
            case 1 -> addon = stack;
        }
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Override
    public void clear() {
        material = ItemStack.EMPTY;
        addon = ItemStack.EMPTY;
    }
}
