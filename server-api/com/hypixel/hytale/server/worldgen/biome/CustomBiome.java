/*    */ package com.hypixel.hytale.server.worldgen.biome;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.condition.IHeightThresholdInterpreter;
/*    */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*    */ import com.hypixel.hytale.server.worldgen.container.CoverContainer;
/*    */ import com.hypixel.hytale.server.worldgen.container.EnvironmentContainer;
/*    */ import com.hypixel.hytale.server.worldgen.container.FadeContainer;
/*    */ import com.hypixel.hytale.server.worldgen.container.LayerContainer;
/*    */ import com.hypixel.hytale.server.worldgen.container.PrefabContainer;
/*    */ import com.hypixel.hytale.server.worldgen.container.TintContainer;
/*    */ import com.hypixel.hytale.server.worldgen.container.WaterContainer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomBiome
/*    */   extends Biome
/*    */ {
/*    */   protected final CustomBiomeGenerator customBiomeGenerator;
/*    */   
/*    */   public CustomBiome(int id, String name, BiomeInterpolation interpolation, CustomBiomeGenerator customBiomeGenerator, @Nonnull IHeightThresholdInterpreter heightmapInterpreter, CoverContainer coverContainer, LayerContainer layerContainer, PrefabContainer prefabContainer, TintContainer tintContainer, EnvironmentContainer environmentContainer, WaterContainer waterContainer, FadeContainer fadeContainer, NoiseProperty heightmapNoise, int mapColor) {
/* 30 */     super(id, name, interpolation, heightmapInterpreter, coverContainer, layerContainer, prefabContainer, tintContainer, environmentContainer, waterContainer, fadeContainer, heightmapNoise, mapColor);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 42 */     this.customBiomeGenerator = customBiomeGenerator;
/*    */   }
/*    */   
/*    */   public CustomBiomeGenerator getCustomBiomeGenerator() {
/* 46 */     return this.customBiomeGenerator;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 52 */     return "CustomBiome{id=" + this.id + ", name='" + this.name + "', interpolation=" + String.valueOf(this.interpolation) + ", heightmapInterpreter=" + String.valueOf(this.heightmapInterpreter) + ", coverContainer=" + String.valueOf(this.coverContainer) + ", layerContainer=" + String.valueOf(this.layerContainer) + ", prefabContainer=" + String.valueOf(this.prefabContainer) + ", tintContainer=" + String.valueOf(this.tintContainer) + ", environmentContainer=" + String.valueOf(this.environmentContainer) + ", waterContainer=" + String.valueOf(this.waterContainer) + ", fadeContainer=" + String.valueOf(this.fadeContainer) + ", heightmapNoise=" + String.valueOf(this.heightmapNoise) + ", mapColor=" + this.mapColor + ", customBiomeGenerator=" + String.valueOf(this.customBiomeGenerator) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\biome\CustomBiome.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */