package io.github.theriverelder.steelcraft.data;

import io.github.theriverelder.steelcraft.SteelCraft;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MiningAttributes {
    public static final EntityAttribute STEELCRAFT_MINING_LEVEL = Registry.register(Registry.ATTRIBUTE, new Identifier(SteelCraft.ID, "mining_level"), new ClampedEntityAttribute("attribute.name." + SteelCraft.ID + ".mining_level", 1.0, 0.0, 2048.0));
    public static final EntityAttribute STEELCRAFT_MINING_SPEED = Registry.register(Registry.ATTRIBUTE, new Identifier(SteelCraft.ID, "mining_speed"), new ClampedEntityAttribute("attribute.name." + SteelCraft.ID + ".mining_speed", 1.0, 0.0, 2048.0));
}
