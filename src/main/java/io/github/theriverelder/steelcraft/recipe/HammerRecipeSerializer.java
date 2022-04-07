package io.github.theriverelder.steelcraft.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class HammerRecipeSerializer implements RecipeSerializer<HammerRecipe> {
    @Override
    public HammerRecipe read(Identifier id, JsonObject json) {
//        System.out.println("lollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollollol");
//        String group = JsonHelper.getString(json, "group", "");

        JsonElement materialJson = JsonHelper.hasArray(json, "material") ? JsonHelper.getArray(json, "material") : JsonHelper.getObject(json, "material");
        Ingredient material = Ingredient.fromJson(materialJson);

        JsonElement addonJson = JsonHelper.hasArray(json, "addon")
                ? JsonHelper.getArray(json, "addon")
                : JsonHelper.hasJsonObject(json, "addon")
                    ? JsonHelper.getObject(json, "addon")
                    : null;
        Ingredient addon = addonJson == null ? null : Ingredient.fromJson(addonJson);

        String resultIdStr = JsonHelper.getString(json, "result");
        Identifier resultId = new Identifier(resultIdStr);
        ItemStack result = new ItemStack(Registry.ITEM.getOrEmpty(resultId).orElseThrow(() -> new IllegalStateException("Item: " + resultIdStr + " does not exist")));

        return new HammerRecipe(id, material, addon, result);
    }

    @Override
    public HammerRecipe read(Identifier id, PacketByteBuf buf) {
//        String group = buf.readString();
        Ingredient material = Ingredient.fromPacket(buf);
        Ingredient addon = buf.readBoolean() ? Ingredient.fromPacket(buf) : null;
        ItemStack result = buf.readItemStack();
        return new HammerRecipe(id, material, addon, result);
    }

    @Override
    public void write(PacketByteBuf buf, HammerRecipe recipe) {
//        buf.writeString(recipe.getGroup());
        recipe.getMaterial().write(buf);
        Ingredient addon = recipe.getAddon();
        if (addon != null) {
            buf.writeBoolean(true);
            addon.write(buf);
        } else {
            buf.writeBoolean(false);
        }
        buf.writeItemStack(recipe.getOutput());
    }
}
