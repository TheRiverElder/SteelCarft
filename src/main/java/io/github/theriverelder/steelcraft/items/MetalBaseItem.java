package io.github.theriverelder.steelcraft.items;

import io.github.theriverelder.steelcraft.data.MaterialInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class MetalBaseItem extends Item {

    public static final String KEY_MATERIAL_INFO = "material_info";

    public MetalBaseItem(Settings settings) {
        super(settings);
    }

    public MaterialInfo getMaterialInfo(ItemStack stack) {
        NbtCompound nbt = Optional.ofNullable(stack.getSubNbt(KEY_MATERIAL_INFO)).orElseGet(NbtCompound::new);
        MaterialInfo materialInfo = new MaterialInfo();
        materialInfo.readNbt(nbt);
        return materialInfo;
    }

    public void setMaterialInfo(ItemStack stack, MaterialInfo materialInfo) {
        NbtCompound nbt = new NbtCompound();
        materialInfo.writeNbt(nbt);
        stack.setSubNbt(KEY_MATERIAL_INFO, nbt);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (world != null && world.isClient()) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player != null && player.isCreative()) {
                MaterialInfo materialInfo = getMaterialInfo(stack);
                tooltip.add(new TranslatableText("item.steelcraft.metal_base.tooltip_1", materialInfo.getBaseCoefficient()));
                tooltip.add(new TranslatableText("item.steelcraft.metal_base.tooltip_2", materialInfo.getMass()));
                tooltip.add(new TranslatableText("item.steelcraft.metal_base.tooltip_3", materialInfo.getStress()));
                tooltip.add(new TranslatableText("item.steelcraft.metal_base.tooltip_4", materialInfo.getGrainSize()));
                tooltip.add(new TranslatableText("item.steelcraft.metal_base.tooltip_5", materialInfo.getImpurityRatio()));
                tooltip.add(new TranslatableText("item.steelcraft.metal_base.tooltip_6", materialInfo.getProcessCount()));
                tooltip.add(new TranslatableText("item.steelcraft.metal_base.tooltip_7", materialInfo.getToughness()));
                tooltip.add(new TranslatableText("item.steelcraft.metal_base.tooltip_8", materialInfo.getHardness()));
            }
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
