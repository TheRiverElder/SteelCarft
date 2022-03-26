package io.github.theriverelder.steelcraft.items;

import io.github.theriverelder.steelcraft.data.MaterialInfo;
import io.github.theriverelder.steelcraft.data.MiningAttributes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MetalBaseItem extends Item {

    public static final String KEY_MATERIAL_INFO = "material_info";

    public MetalBaseItem(Settings settings) {
        super(settings);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        MaterialInfo materialInfo = MaterialInfo.fromToolMaterial(ToolMaterials.IRON);
        materialInfo.setToStack(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (world != null && world.isClient()) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player != null && player.isCreative()) {
                MaterialInfo materialInfo = MaterialInfo.fromStack(stack);
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

    public static void modifyByMaterial(ItemStack stack, MaterialInfo info) {

        stack.addAttributeModifier(
                EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier("Material-based weapon modifier", info.getAttackDamage(), EntityAttributeModifier.Operation.ADDITION),
                EquipmentSlot.MAINHAND
        );

        stack.addAttributeModifier(
                EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier("Material-based weapon modifier", info.getAttackSpeed(), EntityAttributeModifier.Operation.ADDITION),
                EquipmentSlot.MAINHAND
        );

        stack.addAttributeModifier(
                MiningAttributes.STEELCRAFT_MINING_LEVEL,
                new EntityAttributeModifier("Material-based weapon modifier", info.getMiningLevel(), EntityAttributeModifier.Operation.ADDITION),
                EquipmentSlot.MAINHAND
        );

        stack.addAttributeModifier(
                MiningAttributes.STEELCRAFT_MINING_SPEED,
                new EntityAttributeModifier("Material-based weapon modifier", info.getMiningSpeed(), EntityAttributeModifier.Operation.ADDITION),
                EquipmentSlot.MAINHAND
        );

    }

    public static void modifyByMaterial(ItemStack stack) {
        modifyByMaterial(stack, MaterialInfo.fromStack(stack));
    }
}
