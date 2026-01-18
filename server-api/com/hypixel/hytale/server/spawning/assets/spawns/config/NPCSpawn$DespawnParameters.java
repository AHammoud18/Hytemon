/*     */ package com.hypixel.hytale.server.spawning.assets.spawns.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import java.util.Arrays;
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
/*     */ public class DespawnParameters
/*     */ {
/*     */   public static final BuilderCodec<DespawnParameters> CODEC;
/*     */   
/*     */   static {
/* 279 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DespawnParameters.class, DespawnParameters::new).documentation("A set of parameters that determine if NPCs should despawn.")).append(new KeyedCodec("DayTimeRange", (Codec)Codec.DOUBLE_ARRAY), (parameters, o) -> { parameters.dayTimeRange = o; parameters.dayTimeRange[0] = parameters.dayTimeRange[0] / 24.0D; parameters.dayTimeRange[1] = parameters.dayTimeRange[1] / 24.0D; }parameters -> new double[] { parameters.dayTimeRange[0] * 24.0D, parameters.dayTimeRange[1] * 24.0D }).documentation("An optional hour range within which the NPCs will despawn (between 0 and 24). For Spawn Beacons, this refers to the beacon itself.").addValidator(Validators.doubleArraySize(2)).add()).append(new KeyedCodec("MoonPhaseRange", (Codec)Codec.INT_ARRAY), (parameters, o) -> parameters.moonPhaseRange = o, parameters -> new int[] { parameters.moonPhaseRange[0], parameters.moonPhaseRange[1] }).documentation("An optional moon phase range during which the NPCs will despawn (must be greater than or equal to 0). For Spawn Beacons, this refers to the beacon itself.").addValidator(Validators.intArraySize(2)).add()).build();
/*     */   }
/*     */   
/* 282 */   protected double[] dayTimeRange = NPCSpawn.DEFAULT_DAY_TIME_RANGE;
/* 283 */   protected int[] moonPhaseRange = NPCSpawn.DEFAULT_MOON_PHASE_RANGE;
/*     */   
/*     */   public DespawnParameters(double[] dayTimeRange, int[] moonPhaseRange) {
/* 286 */     this.dayTimeRange = dayTimeRange;
/* 287 */     this.moonPhaseRange = moonPhaseRange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getDayTimeRange() {
/* 294 */     return this.dayTimeRange;
/*     */   }
/*     */   
/*     */   public int[] getMoonPhaseRange() {
/* 298 */     return this.moonPhaseRange;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 305 */     return "DespawnParameters{dayTimeRange=" + Arrays.toString(this.dayTimeRange) + ", moonPhaseRange=" + 
/* 306 */       Arrays.toString(this.moonPhaseRange) + "}";
/*     */   }
/*     */   
/*     */   protected DespawnParameters() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\assets\spawns\config\NPCSpawn$DespawnParameters.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */