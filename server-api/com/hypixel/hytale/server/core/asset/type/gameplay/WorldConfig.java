/*     */ package com.hypixel.hytale.server.core.asset.type.gameplay;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class WorldConfig
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<WorldConfig> CODEC;
/*     */   public static final int DEFAULT_TOTAL_DAY_DURATION_SECONDS = 2880;
/*     */   public static final int DEFAULT_DAYTIME_DURATION_SECONDS = 1728;
/*     */   public static final int DEFAULT_NIGHTTIME_DURATION_SECONDS = 1728;
/*     */   
/*     */   static {
/*  72 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(WorldConfig.class, WorldConfig::new).append(new KeyedCodec("AllowBlockBreaking", (Codec)Codec.BOOLEAN), (worldConfig, o) -> worldConfig.allowBlockBreaking = o.booleanValue(), worldConfig -> Boolean.valueOf(worldConfig.allowBlockBreaking)).add()).append(new KeyedCodec("AllowBlockGathering", (Codec)Codec.BOOLEAN), (worldConfig, o) -> worldConfig.allowBlockGathering = o.booleanValue(), worldConfig -> Boolean.valueOf(worldConfig.allowBlockGathering)).add()).append(new KeyedCodec("AllowBlockPlacement", (Codec)Codec.BOOLEAN), (worldConfig, o) -> worldConfig.allowBlockPlacement = o.booleanValue(), worldConfig -> Boolean.valueOf(worldConfig.allowBlockPlacement)).add()).append(new KeyedCodec("BlockPlacementFragilityTimer", (Codec)Codec.DOUBLE), (worldConfig, d) -> worldConfig.blockPlacementFragilityTimer = d.floatValue(), worldConfig -> Double.valueOf(worldConfig.blockPlacementFragilityTimer)).documentation("The timer, in seconds, that blocks have after placement during which they are fragile and can be broken instantly").add()).append(new KeyedCodec("DaytimeDurationSeconds", (Codec)Codec.INTEGER), (worldConfig, i) -> worldConfig.daytimeDurationSeconds = i.intValue(), worldConfig -> Integer.valueOf(worldConfig.daytimeDurationSeconds)).documentation("The number of real-world seconds it takes for the day to pass (from sunrise to sunset)").add()).append(new KeyedCodec("NighttimeDurationSeconds", (Codec)Codec.INTEGER), (worldConfig, i) -> worldConfig.nighttimeDurationSeconds = i.intValue(), worldConfig -> Integer.valueOf(worldConfig.nighttimeDurationSeconds)).documentation("The number of real-world seconds it takes for the night to pass (from sunset to sunrise)").add()).append(new KeyedCodec("TotalMoonPhases", (Codec)Codec.INTEGER), (worldConfig, i) -> worldConfig.totalMoonPhases = i.intValue(), o -> Integer.valueOf(o.totalMoonPhases)).add()).append(new KeyedCodec("Sleep", (Codec)SleepConfig.CODEC), (worldConfig, sleepConfig) -> worldConfig.sleepConfig = sleepConfig, o -> o.sleepConfig).documentation("Configurations related to sleeping in this world (in beds)").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean allowBlockBreaking = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean allowBlockGathering = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean allowBlockPlacement = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   protected int daytimeDurationSeconds = 1728;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   protected int nighttimeDurationSeconds = 1728;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   private int totalMoonPhases = 5;
/*     */ 
/*     */ 
/*     */   
/*     */   protected float blockPlacementFragilityTimer;
/*     */ 
/*     */   
/* 124 */   private SleepConfig sleepConfig = SleepConfig.DEFAULT;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBlockBreakingAllowed() {
/* 130 */     return this.allowBlockBreaking;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBlockGatheringAllowed() {
/* 137 */     return this.allowBlockGathering;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBlockPlacementAllowed() {
/* 144 */     return this.allowBlockPlacement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDaytimeDurationSeconds() {
/* 151 */     return this.daytimeDurationSeconds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNighttimeDurationSeconds() {
/* 158 */     return this.nighttimeDurationSeconds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalMoonPhases() {
/* 165 */     return this.totalMoonPhases;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBlockPlacementFragilityTimer() {
/* 172 */     return this.blockPlacementFragilityTimer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SleepConfig getSleepConfig() {
/* 179 */     return this.sleepConfig;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\gameplay\WorldConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */