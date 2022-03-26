package io.github.theriverelder.steelcraft.data;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class HammerRecipe {

    private final Item mainMaterial;
    private final Item addition;
    private final ResultProvider output;


    public HammerRecipe(Item mainMaterial, Item addition, ResultProvider output) {
        this.mainMaterial = mainMaterial;
        this.addition = addition;
        this.output = output;
    }

    public Item getMainMaterial() {
        return mainMaterial;
    }

    public Item getAddition() {
        return addition;
    }

    public ResultProvider getOutput() {
        return output;
    }

    public static interface ResultProvider {
        ItemStack provide(ItemStack mainMaterial, ItemStack addition);
    }


}
