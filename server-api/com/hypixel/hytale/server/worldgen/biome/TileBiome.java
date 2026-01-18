/*    */ package com.hypixel.hytale.server.worldgen.biome;
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
/*    */ public class TileBiome extends Biome {
/* 14 */   public static final TileBiome[] EMPTY_ARRAY = new TileBiome[0];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final double weight;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final double sizeModifier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TileBiome(int id, String name, BiomeInterpolation interpolation, @Nonnull IHeightThresholdInterpreter heightmapInterpreter, CoverContainer coverContainer, LayerContainer layerContainer, PrefabContainer prefabContainer, TintContainer tintContainer, EnvironmentContainer environmentContainer, WaterContainer waterContainer, FadeContainer fadeContainer, NoiseProperty heightmapNoise, double weight, double sizeModifier, int mapColor) {
/* 33 */     super(id, name, interpolation, heightmapInterpreter, coverContainer, layerContainer, prefabContainer, tintContainer, environmentContainer, waterContainer, fadeContainer, heightmapNoise, mapColor);
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
/* 45 */     this.weight = weight;
/* 46 */     this.sizeModifier = sizeModifier;
/*    */   }
/*    */   
/*    */   public double getWeight() {
/* 50 */     return this.weight;
/*    */   }
/*    */   
/*    */   public double getSizeModifier() {
/* 54 */     return this.sizeModifier;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 60 */     return "TileBiome{id=" + this.id + ", name='" + this.name + "', interpolation=" + String.valueOf(this.interpolation) + ", heightmapInterpreter=" + String.valueOf(this.heightmapInterpreter) + ", coverContainer=" + String.valueOf(this.coverContainer) + ", layerContainer=" + String.valueOf(this.layerContainer) + ", prefabContainer=" + String.valueOf(this.prefabContainer) + ", tintContainer=" + String.valueOf(this.tintContainer) + ", environmentContainer=" + String.valueOf(this.environmentContainer) + ", waterContainer=" + String.valueOf(this.waterContainer) + ", fadeContainer=" + String.valueOf(this.fadeContainer) + ", heightmapNoise=" + String.valueOf(this.heightmapNoise) + ", mapColor=" + this.mapColor + ", weight=" + this.weight + ", sizeModifier=" + this.sizeModifier + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\biome\TileBiome.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */