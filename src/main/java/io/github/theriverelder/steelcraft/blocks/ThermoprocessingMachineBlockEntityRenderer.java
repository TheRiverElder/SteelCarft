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
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class ThermoprocessingMachineBlockEntityRenderer implements BlockEntityRenderer<ThermoprocessingMachineBlockEntity> {

//    private final ItemRenderer itemRenderer;

    public ThermoprocessingMachineBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
//        itemRenderer = MinecraftClient.getInstance().getItemRenderer();
    }

    private int ticks = 0;

    @Override
    public void render(ThermoprocessingMachineBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack stack = entity.getProcessingStack();
        if (ticks == 0) {
//            System.out.println("item: " + stack);
        }
        if (stack == null || stack.isEmpty()) return;

        if (ticks == 0) {
//            System.out.println("render item");
        }
        ticks = (ticks + 1) % 50;

        matrices.push();

        int renderId = (int) entity.getPos().asLong();

        matrices.translate(0.5, 1.35, 0.5);
        matrices.scale(0.5f, 0.5f, 0.5f);

        World world = entity.getWorld();
        if (world != null) {
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED,
                    WorldRenderer.getLightmapCoordinates(world, entity.getPos().up()), overlay, matrices, vertexConsumers, renderId);
        }

        matrices.pop();
    }
}
