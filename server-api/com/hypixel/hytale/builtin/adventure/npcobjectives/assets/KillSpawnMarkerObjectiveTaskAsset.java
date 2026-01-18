/*    */ package com.hypixel.hytale.builtin.adventure.npcobjectives.assets;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.ObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.taskcondition.TaskConditionAsset;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.codec.validation.validator.ArrayValidator;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.spawning.assets.spawnmarker.config.SpawnMarker;
/*    */ import java.util.Arrays;
/*    */ import java.util.function.Supplier;
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
/*    */ 
/*    */ public class KillSpawnMarkerObjectiveTaskAsset
/*    */   extends KillObjectiveTaskAsset
/*    */ {
/*    */   public static final BuilderCodec<KillSpawnMarkerObjectiveTaskAsset> CODEC;
/*    */   protected String[] spawnMarkerIds;
/*    */   
/*    */   static {
/* 36 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(KillSpawnMarkerObjectiveTaskAsset.class, KillSpawnMarkerObjectiveTaskAsset::new, KillObjectiveTaskAsset.CODEC).append(new KeyedCodec("Radius", (Codec)Codec.FLOAT), (killSpawnMarkerObjectiveTaskAsset, aFloat) -> killSpawnMarkerObjectiveTaskAsset.radius = aFloat.floatValue(), killSpawnMarkerObjectiveTaskAsset -> Float.valueOf(killSpawnMarkerObjectiveTaskAsset.radius)).addValidator(Validators.greaterThan(Float.valueOf(0.0F))).add()).append(new KeyedCodec("SpawnMarkerIds", (Codec)Codec.STRING_ARRAY), (killSpawnMarkerObjectiveTaskAsset, s) -> killSpawnMarkerObjectiveTaskAsset.spawnMarkerIds = s, killSpawnMarkerObjectiveTaskAsset -> killSpawnMarkerObjectiveTaskAsset.spawnMarkerIds).addValidator(Validators.nonEmptyArray()).addValidator((Validator)SpawnMarker.VALIDATOR_CACHE.getArrayValidator()).addValidator((Validator)new ArrayValidator((o, results) -> { SpawnMarker spawnMarker = (SpawnMarker)SpawnMarker.getAssetMap().getAsset(o); if (spawnMarker != null && !spawnMarker.isManualTrigger()) results.fail("SpawnMarker '" + o + "' can't be triggered manually!");  })).add()).build();
/*    */   }
/*    */   
/* 39 */   protected float radius = 1.0F;
/*    */   
/*    */   public KillSpawnMarkerObjectiveTaskAsset(String descriptionId, TaskConditionAsset[] taskConditions, Vector3i[] mapMarkers, int count, String npcGroupId, String[] spawnMarkerIds, float radius) {
/* 42 */     super(descriptionId, taskConditions, mapMarkers, count, npcGroupId);
/* 43 */     this.spawnMarkerIds = spawnMarkerIds;
/* 44 */     this.radius = radius;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String[] getSpawnMarkerIds() {
/* 52 */     return this.spawnMarkerIds;
/*    */   }
/*    */   
/*    */   public float getRadius() {
/* 56 */     return this.radius;
/*    */   }
/*    */   
/*    */   protected boolean matchesAsset0(ObjectiveTaskAsset task) {
/*    */     KillSpawnMarkerObjectiveTaskAsset killSpawnMarkerObjectiveTaskAsset;
/* 61 */     if (!super.matchesAsset0(task)) return false; 
/* 62 */     if (task instanceof KillSpawnMarkerObjectiveTaskAsset) { killSpawnMarkerObjectiveTaskAsset = (KillSpawnMarkerObjectiveTaskAsset)task; } else { return false; }
/*    */     
/* 64 */     if (!Arrays.equals((Object[])killSpawnMarkerObjectiveTaskAsset.spawnMarkerIds, (Object[])this.spawnMarkerIds)) return false; 
/* 65 */     return (killSpawnMarkerObjectiveTaskAsset.radius == this.radius);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 72 */     return "KillSpawnMarkerObjectiveTaskAsset{spawnMarkerIds=" + Arrays.toString((Object[])this.spawnMarkerIds) + ", radius=" + this.radius + "} " + super
/*    */       
/* 74 */       .toString();
/*    */   }
/*    */   
/*    */   protected KillSpawnMarkerObjectiveTaskAsset() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcobjectives\assets\KillSpawnMarkerObjectiveTaskAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */