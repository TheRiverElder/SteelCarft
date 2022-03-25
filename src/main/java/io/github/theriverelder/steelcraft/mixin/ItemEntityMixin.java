package io.github.theriverelder.steelcraft.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.github.theriverelder.steelcraft.items.Items.HEATED_IRON_SWORD_PART;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Inject(method = "tick()V", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        ItemEntity ie = (ItemEntity) (Object) this;
        if (!ie.isInsideWaterOrBubbleColumn()) return;
        ItemStack stack = ie.getStack();
        if (!stack.isOf(HEATED_IRON_SWORD_PART)) return;
        ItemStack product = new ItemStack(Items.IRON_SWORD);
        product.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier("generic.attack_damage", 30.0d, EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
        ie.setStack(product);
        ie.getWorld().playSound(ie.getX(), ie.getY(), ie.getZ(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.VOICE, 1.0f, 1.0f, true);
    }
}
