/*    */ package com.hypixel.hytale.builtin.hytalegenerator.biome;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.PropField;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.environmentproviders.EnvironmentProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.MaterialProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.propdistributions.Assignments;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.tintproviders.TintProvider;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimpleBiomeType
/*    */   implements BiomeType
/*    */ {
/*    */   private final Density terrainDensity;
/*    */   private final MaterialProvider<Material> materialProvider;
/*    */   private final List<PropField> propFields;
/*    */   private final EnvironmentProvider environmentProvider;
/*    */   private final TintProvider tintProvider;
/*    */   private final String biomeName;
/*    */   
/*    */   public SimpleBiomeType(@Nonnull String biomeName, @Nonnull Density terrainDensity, @Nonnull MaterialProvider<Material> materialProvider, @Nonnull EnvironmentProvider environmentProvider, @Nonnull TintProvider tintProvider) {
/* 28 */     this.terrainDensity = terrainDensity;
/* 29 */     this.materialProvider = materialProvider;
/* 30 */     this.biomeName = biomeName;
/* 31 */     this.propFields = new ArrayList<>();
/* 32 */     this.environmentProvider = environmentProvider;
/* 33 */     this.tintProvider = tintProvider;
/*    */   }
/*    */   
/*    */   public void addPropFieldTo(@Nonnull PropField propField) {
/* 37 */     this.propFields.add(propField);
/*    */   }
/*    */ 
/*    */   
/*    */   public MaterialProvider<Material> getMaterialProvider() {
/* 42 */     return this.materialProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Density getTerrainDensity() {
/* 48 */     return this.terrainDensity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getBiomeName() {
/* 53 */     return this.biomeName;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<PropField> getPropFields() {
/* 58 */     return this.propFields;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnvironmentProvider getEnvironmentProvider() {
/* 63 */     return this.environmentProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   public TintProvider getTintProvider() {
/* 68 */     return this.tintProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<Assignments> getAllPropDistributions() {
/* 73 */     ArrayList<Assignments> list = new ArrayList<>();
/* 74 */     for (PropField f : this.propFields) {
/* 75 */       list.add(f.getPropDistribution());
/*    */     }
/* 77 */     return list;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\biome\SimpleBiomeType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */