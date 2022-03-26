package io.github.theriverelder.steelcraft.data;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.github.theriverelder.steelcraft.items.MetalBaseItem.KEY_MATERIAL_INFO;

public class MaterialInfo {

    public static final String KEY_BASE_COEFFICIENT = "base_coefficient";
    public static final String KEY_MASS = "mass";
    public static final String KEY_TEMPERATURE = "temperature";
    public static final String KEY_STRESS = "stress";
    public static final String KEY_GRAIN_SIZE = "grain_size";
    public static final String KEY_IMPURITY_RATIO = "impurity_ratio";
    public static final String KEY_PROCESS_COUNT = "process_count";

    public static final Map<ToolMaterial, MaterialInfo> DEFAULT_INFO_MAP = new HashMap<>();

    static {
        DEFAULT_INFO_MAP.put(ToolMaterials.WOOD, new MaterialInfo(1.0f, 1, 20, 0.05f, 25f, 1.0f, 0));
        DEFAULT_INFO_MAP.put(ToolMaterials.STONE, new MaterialInfo(2.0f, 1, 20, 0.8f, 15f, 0.8f, 0));
        DEFAULT_INFO_MAP.put(ToolMaterials.IRON, new MaterialInfo(3.0f, 1, 20, 0.1f, 5f, 0.05f, 0));
        DEFAULT_INFO_MAP.put(ToolMaterials.DIAMOND, new MaterialInfo(4.0f, 1, 20, 0.5f, 1f, 0.8f, 0));
        DEFAULT_INFO_MAP.put(ToolMaterials.GOLD, new MaterialInfo(5.0f, 1, 20, 0.05f, 127f, 0.05f, 0));
        DEFAULT_INFO_MAP.put(ToolMaterials.NETHERITE, new MaterialInfo(6.0f, 1, 20, 0.1f, 0.1f, 0.05f, 0));
    }

    public static MaterialInfo fromToolMaterial(ToolMaterial toolMaterial) {
        return DEFAULT_INFO_MAP.getOrDefault(toolMaterial, new MaterialInfo());
    }

    public static MaterialInfo fromStack(ItemStack stack) {
        NbtCompound nbt = Optional.ofNullable(stack.getSubNbt(KEY_MATERIAL_INFO)).orElseGet(NbtCompound::new);
        MaterialInfo materialInfo = new MaterialInfo();
        materialInfo.readNbt(nbt);
        return materialInfo;
    }

    public MaterialInfo() {
        this(0 ,0, 20, 0, 0, 0, 0);
    }

    public MaterialInfo(float baseCoefficient, float mass, float temperature, float stress, float grainSize, float impurityRatio, int processCount) {
        this.baseCoefficient = baseCoefficient;
        this.mass = mass;
        this.temperature = temperature;
        this.stress = stress;
        this.grainSize = grainSize;
        this.impurityRatio = impurityRatio;
        this.processCount = processCount;
    }

    /**
     * 主要材质系数
     */
    private float baseCoefficient;

    /**
     * 质量
     */
    private float mass;

    /**
     * 温度
     */
    private float temperature;

    /**
     * 材料的应力
     * 应力越大，越结实，但是也更加脆弱
     */
    private float stress;

    /**
     * 晶粒大小
     */
    private float grainSize;

    /**
     * 杂质比例
     */
    private float impurityRatio;

    /**
     * 加工次数
     */
    private int processCount;

    //#region getters and setters


    public float getBaseCoefficient() {
        return baseCoefficient;
    }

    public void setBaseCoefficient(float baseCoefficient) {
        this.baseCoefficient = Math.max(0, baseCoefficient);
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = Math.max(0, mass);
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getStress() {
        return stress;
    }

    public void setStress(float stress) {
        this.stress = Math.max(0, stress);
    }

    public float getGrainSize() {
        return grainSize;
    }

    public void setGrainSize(float grainSize) {
        this.grainSize = Math.max(0, grainSize);
    }

    public float getImpurityRatio() {
        return impurityRatio;
    }

    public void setImpurityRatio(float impurityRatio) {
        this.impurityRatio = Math.min(Math.max(0, impurityRatio), 1);
    }

    public int getProcessCount() {
        return processCount;
    }

    public void setProcessCount(int processCount) {
        this.processCount = Math.max(0, processCount);
    }

    public boolean isWasted() {
        return stress > 0.6;
    }

    //#endregion

    public float getToughness() {
        return baseCoefficient
                - stress * 0.2f
                - impurityRatio * 0.2f
                - grainSize * 0.2f;
    }

    public float getHardness() {
        return baseCoefficient
                + stress * 2.0f
                - impurityRatio * 2.0f
                + grainSize * 2.0f;
    }

    public float getAttackDamage() {
        return getToughness() * 3.0f;
    }

    public float getAttackSpeed() {
        return 0.3f * (0.3f * getToughness() + 0.7f * getHardness()) * (0.5f / (mass + 1) + 0.5f);
    }

    public int getMiningLevel() {
        return (int) getToughness();
    }

    public float getMiningSpeed() {
        return 0.3f * getToughness() + 0.7f * getHardness();
    }

    public ItemStack setToStack(ItemStack stack) {
        NbtCompound nbt = new NbtCompound();
        writeNbt(nbt);
        stack.setSubNbt(KEY_MATERIAL_INFO, nbt);
        return stack;
    }


    public void writeNbt(NbtCompound nbt) {
        nbt.putFloat(KEY_BASE_COEFFICIENT, baseCoefficient);
        nbt.putFloat(KEY_MASS, mass);
        nbt.putFloat(KEY_TEMPERATURE, temperature);
        nbt.putFloat(KEY_STRESS, stress);
        nbt.putFloat(KEY_GRAIN_SIZE, grainSize);
        nbt.putFloat(KEY_IMPURITY_RATIO, impurityRatio);
        nbt.putInt(KEY_PROCESS_COUNT, processCount);
    }

    public void readNbt(NbtCompound nbt) {
        baseCoefficient = nbt.getFloat(KEY_BASE_COEFFICIENT);
        mass = nbt.getFloat(KEY_MASS);
        temperature = nbt.getFloat(KEY_TEMPERATURE);
        stress = nbt.getFloat(KEY_STRESS);
        grainSize = nbt.getFloat(KEY_GRAIN_SIZE);
        impurityRatio = nbt.getFloat(KEY_IMPURITY_RATIO);
        processCount = nbt.getInt(KEY_PROCESS_COUNT);
    }

    public MaterialInfo copy() {
        MaterialInfo c = new MaterialInfo();

        c.baseCoefficient = baseCoefficient;
        c.mass = mass;
        c.temperature = temperature;
        c.stress = stress;
        c.grainSize = grainSize;
        c.impurityRatio = impurityRatio;
        c.processCount = processCount;

        return c;
    }

}
