/*     */ package com.hypixel.hytale.builtin.adventure.npcobjectives.assets;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.worldlocationproviders.WorldLocationProvider;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.config.BeaconNPCSpawn;
/*     */ import java.util.function.Supplier;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectiveSpawnBeacon
/*     */ {
/*     */   public static final BuilderCodec<ObjectiveSpawnBeacon> CODEC;
/*     */   protected String spawnBeaconId;
/*     */   protected Vector3d offset;
/*     */   protected WorldLocationProvider worldLocationProvider;
/*     */   
/*     */   static {
/*  75 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ObjectiveSpawnBeacon.class, ObjectiveSpawnBeacon::new).append(new KeyedCodec("SpawnBeaconId", (Codec)Codec.STRING), (objectiveSpawnBeacon, s) -> objectiveSpawnBeacon.spawnBeaconId = s, objectiveSpawnBeacon -> objectiveSpawnBeacon.spawnBeaconId).addValidator(Validators.nonNull()).addValidator(BeaconNPCSpawn.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("Offset", (Codec)Vector3d.CODEC), (objectiveSpawnBeacon, vector3d) -> objectiveSpawnBeacon.offset = vector3d, objectiveSpawnBeacon -> objectiveSpawnBeacon.offset).add()).append(new KeyedCodec("WorldLocationCondition", (Codec)WorldLocationProvider.CODEC), (objectiveSpawnBeacon, worldLocationCondition) -> objectiveSpawnBeacon.worldLocationProvider = worldLocationCondition, objectiveSpawnBeacon -> objectiveSpawnBeacon.worldLocationProvider).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSpawnBeaconId() {
/*  82 */     return this.spawnBeaconId;
/*     */   }
/*     */   
/*     */   public Vector3d getOffset() {
/*  86 */     return this.offset;
/*     */   }
/*     */   
/*     */   public WorldLocationProvider getWorldLocationProvider() {
/*  90 */     return this.worldLocationProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/*  95 */     if (this == o) return true; 
/*  96 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/*  98 */     ObjectiveSpawnBeacon that = (ObjectiveSpawnBeacon)o;
/*     */     
/* 100 */     if (!this.spawnBeaconId.equals(that.spawnBeaconId)) return false; 
/* 101 */     if ((this.offset != null) ? !this.offset.equals(that.offset) : (that.offset != null)) return false; 
/* 102 */     return (this.worldLocationProvider != null) ? this.worldLocationProvider.equals(that.worldLocationProvider) : ((that.worldLocationProvider == null));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 107 */     int result = this.spawnBeaconId.hashCode();
/* 108 */     result = 31 * result + ((this.offset != null) ? this.offset.hashCode() : 0);
/* 109 */     result = 31 * result + ((this.worldLocationProvider != null) ? this.worldLocationProvider.hashCode() : 0);
/* 110 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 116 */     return "ObjectiveSpawnBeacon{spawnBeaconId='" + this.spawnBeaconId + "', offset=" + String.valueOf(this.offset) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcobjectives\assets\KillSpawnBeaconObjectiveTaskAsset$ObjectiveSpawnBeacon.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */