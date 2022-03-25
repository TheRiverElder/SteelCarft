package io.github.theriverelder.steelcraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class ThermoprocessingMachineBlockEntityRenderer implements BlockEntityRenderer<ThermoprocessingMachineBlockEntity> {

//    private final ItemRenderer itemRenderer;

    public ThermoprocessingMachineBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
//        itemRenderer = MinecraftClient.getInstance().getItemRenderer();
    }

    @Override
    public void render(ThermoprocessingMachineBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack stack = entity.getProcessingStack();
        World world = entity.getWorld();

        if (stack == null || stack.isEmpty() || world == null) return;

        matrices.push();

        int renderId = (int) entity.getPos().asLong();

        matrices.translate(0.5, 1.35, 0.5);
        matrices.scale(0.5f, 0.5f, 0.5f);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((world.getTime() + tickDelta) * 4));

        MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED,
                WorldRenderer.getLightmapCoordinates(world, entity.getPos().up()), overlay, matrices, vertexConsumers, renderId);

        matrices.pop();
    }
}
