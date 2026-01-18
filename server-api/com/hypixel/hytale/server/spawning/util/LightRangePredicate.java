/*     */ package com.hypixel.hytale.server.spawning.util;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.LightType;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LightRangePredicate
/*     */ {
/*     */   private byte lightValueMin;
/*     */   private byte lightValueMax;
/*     */   private byte skyLightValueMin;
/*     */   private byte skyLightValueMax;
/*     */   private byte sunlightValueMin;
/*     */   private byte sunlightValueMax;
/*     */   private byte redLightValueMin;
/*     */   private byte redLightValueMax;
/*     */   private byte greenLightValueMin;
/*     */   private byte greenLightValueMax;
/*     */   private byte blueLightValueMin;
/*     */   private byte blueLightValueMax;
/*     */   private boolean testLightValue;
/*     */   private boolean testSkyLightValue;
/*     */   private boolean testSunlightValue;
/*     */   private boolean testRedLightValue;
/*     */   private boolean testGreenLightValue;
/*     */   private boolean testBlueLightValue;
/*     */   
/*     */   public static int lightToPrecentage(byte light) {
/*  44 */     return MathUtil.fastRound(light * 100.0F / 15.0F);
/*     */   }
/*     */   
/*     */   public void setLightRange(@Nonnull LightType type, double[] lightRange) {
/*  48 */     switch (type) {
/*     */       case Light:
/*  50 */         setLightRange(lightRange);
/*     */         break;
/*     */       case SkyLight:
/*  53 */         setSkyLightRange(lightRange);
/*     */         break;
/*     */       case Sunlight:
/*  56 */         setSunlightRange(lightRange);
/*     */         break;
/*     */       case RedLight:
/*  59 */         setRedLightRange(lightRange);
/*     */         break;
/*     */       case GreenLight:
/*  62 */         setGreenLightRange(lightRange);
/*     */         break;
/*     */       case BlueLight:
/*  65 */         setBlueLightRange(lightRange);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setLightRange(@Nullable double[] lightRange) {
/*  71 */     this.testLightValue = (lightRange != null);
/*  72 */     if (this.testLightValue) {
/*  73 */       this.lightValueMin = lightPercentageToAbsolute(lightRange[0]);
/*  74 */       this.lightValueMax = lightPercentageToAbsolute(lightRange[1]);
/*     */       
/*  76 */       this.testLightValue = isPartialRange(this.lightValueMin, this.lightValueMax);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSkyLightRange(@Nullable double[] lightRange) {
/*  81 */     this.testSkyLightValue = (lightRange != null);
/*  82 */     if (this.testSkyLightValue) {
/*  83 */       this.skyLightValueMin = lightPercentageToAbsolute(lightRange[0]);
/*  84 */       this.skyLightValueMax = lightPercentageToAbsolute(lightRange[1]);
/*  85 */       this.testSkyLightValue = isPartialRange(this.skyLightValueMin, this.skyLightValueMax);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSunlightRange(@Nullable double[] lightRange) {
/*  90 */     this.testSunlightValue = (lightRange != null);
/*  91 */     if (this.testSunlightValue) {
/*  92 */       this.sunlightValueMin = lightPercentageToAbsolute(lightRange[0]);
/*  93 */       this.sunlightValueMax = lightPercentageToAbsolute(lightRange[1]);
/*  94 */       this.testSunlightValue = isPartialRange(this.sunlightValueMin, this.sunlightValueMax);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setRedLightRange(@Nullable double[] lightRange) {
/*  99 */     this.testRedLightValue = (lightRange != null);
/* 100 */     if (this.testRedLightValue) {
/* 101 */       this.redLightValueMin = lightPercentageToAbsolute(lightRange[0]);
/* 102 */       this.redLightValueMax = lightPercentageToAbsolute(lightRange[1]);
/* 103 */       this.testRedLightValue = isPartialRange(this.redLightValueMin, this.redLightValueMax);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setGreenLightRange(@Nullable double[] lightRange) {
/* 108 */     this.testGreenLightValue = (lightRange != null);
/* 109 */     if (this.testGreenLightValue) {
/* 110 */       this.greenLightValueMin = lightPercentageToAbsolute(lightRange[0]);
/* 111 */       this.greenLightValueMax = lightPercentageToAbsolute(lightRange[1]);
/* 112 */       this.testGreenLightValue = isPartialRange(this.greenLightValueMin, this.greenLightValueMax);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setBlueLightRange(@Nullable double[] lightRange) {
/* 117 */     this.testBlueLightValue = (lightRange != null);
/* 118 */     if (this.testBlueLightValue) {
/* 119 */       this.blueLightValueMin = lightPercentageToAbsolute(lightRange[0]);
/* 120 */       this.blueLightValueMax = lightPercentageToAbsolute(lightRange[1]);
/* 121 */       this.testBlueLightValue = isPartialRange(this.blueLightValueMin, this.blueLightValueMax);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isTestLightValue() {
/* 126 */     return this.testLightValue;
/*     */   }
/*     */   
/*     */   public boolean isTestSkyLightValue() {
/* 130 */     return this.testSkyLightValue;
/*     */   }
/*     */   
/*     */   public boolean isTestSunlightValue() {
/* 134 */     return this.testSunlightValue;
/*     */   }
/*     */   
/*     */   public boolean isTestRedLightValue() {
/* 138 */     return this.testRedLightValue;
/*     */   }
/*     */   
/*     */   public boolean isTestGreenLightValue() {
/* 142 */     return this.testGreenLightValue;
/*     */   }
/*     */   
/*     */   public boolean isTestBlueLightValue() {
/* 146 */     return this.testBlueLightValue;
/*     */   }
/*     */   
/*     */   public boolean test(@Nonnull World world, @Nonnull Vector3d position, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 150 */     int x = MathUtil.floor(position.getX());
/* 151 */     int y = MathUtil.floor(position.getY());
/* 152 */     int z = MathUtil.floor(position.getZ());
/*     */     
/* 154 */     WorldTimeResource worldTimeResource = (WorldTimeResource)componentAccessor.getResource(WorldTimeResource.getResourceType());
/* 155 */     WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(x, z));
/* 156 */     return (chunk != null && test(chunk.getBlockChunk(), x, y, z, worldTimeResource.getSunlightFactor()));
/*     */   }
/*     */   
/*     */   public boolean test(@Nullable BlockChunk blockChunk, int x, int y, int z, double sunlightFactor) {
/* 160 */     if (blockChunk == null) return false;
/*     */     
/* 162 */     if (this.testLightValue) {
/* 163 */       byte maxLight = calculateLightValue(blockChunk, x, y, z, sunlightFactor);
/* 164 */       if (!testLight(maxLight)) return false;
/*     */     
/*     */     } 
/* 167 */     if (this.testSkyLightValue) {
/* 168 */       byte lightValue = blockChunk.getSkyLight(x, y, z);
/* 169 */       if (!testSkyLight(lightValue)) return false;
/*     */     
/*     */     } 
/* 172 */     if (this.testSunlightValue) {
/* 173 */       byte lightValue = (byte)(int)(blockChunk.getSkyLight(x, y, z) * sunlightFactor);
/* 174 */       if (!testSunlight(lightValue)) return false;
/*     */     
/*     */     } 
/* 177 */     if (this.testRedLightValue) {
/* 178 */       byte lightValue = blockChunk.getRedBlockLight(x, y, z);
/* 179 */       if (!testRedLight(lightValue)) return false;
/*     */     
/*     */     } 
/* 182 */     if (this.testGreenLightValue) {
/* 183 */       byte lightValue = blockChunk.getGreenBlockLight(x, y, z);
/* 184 */       if (!testGreenLight(lightValue)) return false;
/*     */     
/*     */     } 
/* 187 */     if (this.testBlueLightValue) {
/* 188 */       byte lightValue = blockChunk.getBlueBlockLight(x, y, z);
/* 189 */       if (!testBlueLight(lightValue)) return false;
/*     */     
/*     */     } 
/* 192 */     return true;
/*     */   }
/*     */   
/*     */   public boolean testLight(byte lightValue) {
/* 196 */     return test(lightValue, this.lightValueMin, this.lightValueMax);
/*     */   }
/*     */   
/*     */   public boolean testSkyLight(byte lightValue) {
/* 200 */     return test(lightValue, this.skyLightValueMin, this.skyLightValueMax);
/*     */   }
/*     */   
/*     */   public boolean testSunlight(byte lightValue) {
/* 204 */     return test(lightValue, this.sunlightValueMin, this.sunlightValueMax);
/*     */   }
/*     */   
/*     */   public boolean testRedLight(byte lightValue) {
/* 208 */     return test(lightValue, this.redLightValueMin, this.redLightValueMax);
/*     */   }
/*     */   
/*     */   public boolean testGreenLight(byte lightValue) {
/* 212 */     return test(lightValue, this.greenLightValueMin, this.greenLightValueMax);
/*     */   }
/*     */   
/*     */   public boolean testBlueLight(byte lightValue) {
/* 216 */     return test(lightValue, this.blueLightValueMin, this.blueLightValueMax);
/*     */   }
/*     */   
/*     */   public static byte calculateLightValue(@Nonnull BlockChunk blockChunk, int x, int y, int z, double sunlightFactor) {
/* 220 */     int lightValue = blockChunk.getBlockLightIntensity(x, y, z);
/* 221 */     byte skyLightValue = (byte)(int)(blockChunk.getSkyLight(x, y, z) * sunlightFactor);
/* 222 */     return (byte)Math.max(skyLightValue, lightValue);
/*     */   }
/*     */   
/*     */   private boolean test(byte lightValue, byte min, byte max) {
/* 226 */     return (lightValue >= min && lightValue <= max);
/*     */   }
/*     */   
/*     */   private byte lightPercentageToAbsolute(double light) {
/* 230 */     return (byte)(int)MathUtil.fastRound(light * 0.15D);
/*     */   }
/*     */   
/*     */   private boolean isPartialRange(byte min, byte max) {
/* 234 */     return (min > 0 || max < 15);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawnin\\util\LightRangePredicate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */