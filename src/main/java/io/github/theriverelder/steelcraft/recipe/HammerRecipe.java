package io.github.theriverelder.steelcraft.recipe;

import io.github.theriverelder.steelcraft.SteelCraft;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class HammerRecipe implements Recipe<HammerRecipeInventory> {

    private final Identifier id;
    private final Ingredient material;
    @Nullable
    private final Ingredient addon;
    private final ItemStack result;

    public HammerRecipe(Identifier id, Ingredient material, @Nullable Ingredient addon, ItemStack result) {
        this.id = id;
        this.material = material;
        this.addon = addon;
        this.result = result;
    }

    public Ingredient getMaterial() {
        return material;
    }

    public @Nullable Ingredient getAddon() {
        return addon;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public ItemStack getOutput() {
        return result;
    }

    @Override
    public boolean matches(HammerRecipeInventory inventory, World world) {
        ItemStack actualMaterial = inventory.getMaterial();
        ItemStack actualAddon = inventory.getAddon();

        if (!material.test(actualMaterial)) return false;

        return addon == null || !addon.test(actualAddon);
    }

    @Override
    public ItemStack craft(HammerRecipeInventory inventory) {
        return this.getOutput().copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SteelCraft.HAMMER_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }



    public static class Type implements RecipeType<HammerRecipe> {
        public Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "hammer";

        @Override
        public String toString() {
            return ID;
        }
    }
}
