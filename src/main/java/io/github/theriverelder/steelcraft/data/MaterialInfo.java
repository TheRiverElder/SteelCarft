package io.github.theriverelder.steelcraft.data;

import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;

public class MaterialInfo {

    public static final String KEY_BASE_COEFFICIENT = "base_coefficient";
    public static final String KEY_MASS = "mass";
    public static final String KEY_STRESS = "stress";
    public static final String KEY_GRAIN_SIZE = "grain_size";
    public static final String KEY_IMPURITY_RATIO = "impurity_ratio";
    public static final String KEY_PROCESS_COUNT = "process_count";

    public static final Map<ToolMaterial, Double> BASE_COEFFICIENTS = new HashMap<>();

    static {
        BASE_COEFFICIENTS.put(ToolMaterials.WOOD, 1.0d);
        BASE_COEFFICIENTS.put(ToolMaterials.STONE, 2.0d);
        BASE_COEFFICIENTS.put(ToolMaterials.IRON, 3.0d);
        BASE_COEFFICIENTS.put(ToolMaterials.DIAMOND, 4.0d);
        BASE_COEFFICIENTS.put(ToolMaterials.GOLD, 5.0d);
        BASE_COEFFICIENTS.put(ToolMaterials.NETHERITE, 6.0d);
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
        this.baseCoefficient = baseCoefficient;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getStress() {
        return stress;
    }

    public void setStress(float stress) {
        this.stress = stress;
    }

    public float getGrainSize() {
        return grainSize;
    }

    public void setGrainSize(float grainSize) {
        this.grainSize = grainSize;
    }

    public float getImpurityRatio() {
        return impurityRatio;
    }

    public void setImpurityRatio(float impurityRatio) {
        this.impurityRatio = impurityRatio;
    }

    public int getProcessCount() {
        return processCount;
    }

    public void setProcessCount(int processCount) {
        this.processCount = processCount;
    }

    public boolean isWasted() {
        return stress > 0.6;
    }

    //#endregion

    public float getToughness() {
        return baseCoefficient
                - stress * 2.0f
                - impurityRatio * 2.0f
                - grainSize * 2.0f;
    }

    public float getHardness() {
        return baseCoefficient
                + stress * 2.0f
                - impurityRatio * 2.0f
                + grainSize * 2.0f;
    }

    public float getAttackDamage() {
        return 1;
    }

    public float getAttackSpeed() {
        return 1;
    }

    public int getMineLevel() {
        return 1;
    }

    public float getMineSpeed() {
        return 1;
    }


    public void writeNbt(NbtCompound nbt) {
        nbt.putFloat(KEY_BASE_COEFFICIENT, baseCoefficient);
        nbt.putFloat(KEY_MASS, mass);
        nbt.putFloat(KEY_STRESS, stress);
        nbt.putFloat(KEY_GRAIN_SIZE, grainSize);
        nbt.putFloat(KEY_IMPURITY_RATIO, impurityRatio);
        nbt.putInt(KEY_PROCESS_COUNT, processCount);
    }

    public void readNbt(NbtCompound nbt) {
        baseCoefficient = nbt.getFloat(KEY_BASE_COEFFICIENT);
        mass = nbt.getFloat(KEY_MASS);
        stress = nbt.getFloat(KEY_STRESS);
        grainSize = nbt.getFloat(KEY_GRAIN_SIZE);
        impurityRatio = nbt.getFloat(KEY_IMPURITY_RATIO);
        processCount = nbt.getInt(KEY_PROCESS_COUNT);
    }

}
