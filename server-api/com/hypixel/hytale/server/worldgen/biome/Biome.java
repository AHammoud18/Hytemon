/*     */ package com.hypixel.hytale.server.worldgen.biome;
/*     */ 
/*     */ import com.hypixel.hytale.procedurallib.condition.IHeightThresholdInterpreter;
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*     */ import com.hypixel.hytale.server.worldgen.container.CoverContainer;
/*     */ import com.hypixel.hytale.server.worldgen.container.EnvironmentContainer;
/*     */ import com.hypixel.hytale.server.worldgen.container.FadeContainer;
/*     */ import com.hypixel.hytale.server.worldgen.container.LayerContainer;
/*     */ import com.hypixel.hytale.server.worldgen.container.PrefabContainer;
/*     */ import com.hypixel.hytale.server.worldgen.container.TintContainer;
/*     */ import com.hypixel.hytale.server.worldgen.container.WaterContainer;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public abstract class Biome
/*     */ {
/*     */   protected final int id;
/*     */   protected final String name;
/*     */   protected final BiomeInterpolation interpolation;
/*     */   @Nonnull
/*     */   protected final IHeightThresholdInterpreter heightmapInterpreter;
/*     */   protected final CoverContainer coverContainer;
/*     */   protected final LayerContainer layerContainer;
/*     */   protected final PrefabContainer prefabContainer;
/*     */   protected final TintContainer tintContainer;
/*     */   protected final EnvironmentContainer environmentContainer;
/*     */   protected final WaterContainer waterContainer;
/*     */   protected final FadeContainer fadeContainer;
/*     */   protected final NoiseProperty heightmapNoise;
/*     */   protected final int mapColor;
/*     */   
/*     */   public Biome(int id, String name, BiomeInterpolation interpolation, @Nonnull IHeightThresholdInterpreter heightmapInterpreter, CoverContainer coverContainer, LayerContainer layerContainer, PrefabContainer prefabContainer, TintContainer tintContainer, EnvironmentContainer environmentContainer, WaterContainer waterContainer, FadeContainer fadeContainer, NoiseProperty heightmapNoise, int mapColor) {
/*  43 */     this.id = id;
/*  44 */     this.name = name;
/*  45 */     this.interpolation = interpolation;
/*  46 */     this.heightmapInterpreter = heightmapInterpreter;
/*  47 */     this.coverContainer = coverContainer;
/*  48 */     this.layerContainer = layerContainer;
/*  49 */     this.prefabContainer = prefabContainer;
/*  50 */     this.tintContainer = tintContainer;
/*  51 */     this.environmentContainer = environmentContainer;
/*  52 */     this.waterContainer = waterContainer;
/*  53 */     this.fadeContainer = fadeContainer;
/*  54 */     this.heightmapNoise = heightmapNoise;
/*  55 */     this.mapColor = mapColor;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  59 */     return this.name;
/*     */   }
/*     */   
/*     */   public BiomeInterpolation getInterpolation() {
/*  63 */     return this.interpolation;
/*     */   }
/*     */   
/*     */   public IHeightThresholdInterpreter getHeightmapInterpreter() {
/*  67 */     return this.heightmapInterpreter;
/*     */   }
/*     */   
/*     */   public CoverContainer getCoverContainer() {
/*  71 */     return this.coverContainer;
/*     */   }
/*     */   
/*     */   public LayerContainer getLayerContainer() {
/*  75 */     return this.layerContainer;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PrefabContainer getPrefabContainer() {
/*  80 */     return this.prefabContainer;
/*     */   }
/*     */   
/*     */   public TintContainer getTintContainer() {
/*  84 */     return this.tintContainer;
/*     */   }
/*     */   
/*     */   public EnvironmentContainer getEnvironmentContainer() {
/*  88 */     return this.environmentContainer;
/*     */   }
/*     */   
/*     */   public WaterContainer getWaterContainer() {
/*  92 */     return this.waterContainer;
/*     */   }
/*     */   
/*     */   public FadeContainer getFadeContainer() {
/*  96 */     return this.fadeContainer;
/*     */   }
/*     */   
/*     */   public NoiseProperty getHeightmapNoise() {
/* 100 */     return this.heightmapNoise;
/*     */   }
/*     */   
/*     */   public int getId() {
/* 104 */     return this.id;
/*     */   }
/*     */   
/*     */   public int getMapColor() {
/* 108 */     return this.mapColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     return this.id;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\biome\Biome.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */