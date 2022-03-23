package io.github.theriverelder.steelcraft.client;

import io.github.theriverelder.steelcraft.blocks.ThermoprocessingMachineBlock;
import io.github.theriverelder.steelcraft.blocks.ThermoprocessingMachineBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class SteelCraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(ThermoprocessingMachineBlock.ENTITY, ThermoprocessingMachineBlockEntityRenderer::new);
    }
}
