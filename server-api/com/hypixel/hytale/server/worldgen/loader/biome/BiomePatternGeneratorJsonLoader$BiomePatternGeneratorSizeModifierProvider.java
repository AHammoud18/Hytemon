/*     */ package com.hypixel.hytale.server.worldgen.loader.biome;
/*     */ 
/*     */ import com.hypixel.hytale.server.worldgen.biome.BiomePatternGenerator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BiomePatternGeneratorSizeModifierProvider
/*     */   implements BiomePatternGeneratorJsonLoader.ISizeModifierProvider
/*     */ {
/*     */   private BiomePatternGenerator generator;
/*     */   
/*     */   public BiomePatternGeneratorSizeModifierProvider() {}
/*     */   
/*     */   public BiomePatternGeneratorSizeModifierProvider(BiomePatternGenerator generator) {
/* 109 */     this.generator = generator;
/*     */   }
/*     */   
/*     */   public BiomePatternGenerator getGenerator() {
/* 113 */     return this.generator;
/*     */   }
/*     */   
/*     */   public void setGenerator(BiomePatternGenerator generator) {
/* 117 */     this.generator = generator;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int seed, int x, int y) {
/* 122 */     return this.generator.getBiomeDirect(seed, x, y).getSizeModifier();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\loader\biome\BiomePatternGeneratorJsonLoader$BiomePatternGeneratorSizeModifierProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */