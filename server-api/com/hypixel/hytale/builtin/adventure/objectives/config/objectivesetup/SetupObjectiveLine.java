/*    */ package com.hypixel.hytale.builtin.adventure.objectives.config.objectivesetup;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveLineAsset;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.LateValidator;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SetupObjectiveLine
/*    */   extends ObjectiveTypeSetup
/*    */ {
/*    */   public static final BuilderCodec<SetupObjectiveLine> CODEC;
/*    */   protected String objectiveLineId;
/*    */   
/*    */   static {
/* 32 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(SetupObjectiveLine.class, SetupObjectiveLine::new).append(new KeyedCodec("ObjectiveLineId", (Codec)Codec.STRING), (setupObjectiveLine, s) -> setupObjectiveLine.objectiveLineId = s, setupObjectiveLine -> setupObjectiveLine.objectiveLineId).addValidator(Validators.nonNull()).addValidatorLate(() -> ObjectiveLineAsset.VALIDATOR_CACHE.getValidator().late()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getObjectiveIdToStart() {
/* 39 */     ObjectiveLineAsset objectiveLineAsset = (ObjectiveLineAsset)ObjectiveLineAsset.getAssetMap().getAsset(this.objectiveLineId);
/* 40 */     return (objectiveLineAsset != null) ? objectiveLineAsset.getObjectiveIds()[0] : null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Objective setup(@Nonnull Set<UUID> playerUUIDs, @Nonnull UUID worldUUID, @Nullable UUID markerUUID, @Nonnull Store<EntityStore> store) {
/* 46 */     return ObjectivePlugin.get().startObjectiveLine(store, this.objectiveLineId, playerUUIDs, worldUUID, markerUUID);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 52 */     return "SetupObjectiveLine{objectiveLineId='" + this.objectiveLineId + "'} " + super
/*    */       
/* 54 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\objectivesetup\SetupObjectiveLine.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */