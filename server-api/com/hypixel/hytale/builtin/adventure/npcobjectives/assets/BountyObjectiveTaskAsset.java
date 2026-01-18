/*    */ package com.hypixel.hytale.builtin.adventure.npcobjectives.assets;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.ObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.taskcondition.TaskConditionAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.worldlocationproviders.WorldLocationProvider;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BountyObjectiveTaskAsset
/*    */   extends ObjectiveTaskAsset
/*    */ {
/*    */   public static final BuilderCodec<BountyObjectiveTaskAsset> CODEC;
/*    */   protected String npcId;
/*    */   protected WorldLocationProvider worldLocationProvider;
/*    */   
/*    */   static {
/* 27 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BountyObjectiveTaskAsset.class, BountyObjectiveTaskAsset::new, ObjectiveTaskAsset.BASE_CODEC).append(new KeyedCodec("NpcId", (Codec)Codec.STRING), (bountyObjectiveTaskAsset, s) -> bountyObjectiveTaskAsset.npcId = s, bountyObjectiveTaskAsset -> bountyObjectiveTaskAsset.npcId).add()).append(new KeyedCodec("WorldLocationCondition", (Codec)WorldLocationProvider.CODEC), (bountyObjectiveTaskAsset, worldLocationCondition) -> bountyObjectiveTaskAsset.worldLocationProvider = worldLocationCondition, bountyObjectiveTaskAsset -> bountyObjectiveTaskAsset.worldLocationProvider).addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public BountyObjectiveTaskAsset(String descriptionId, TaskConditionAsset[] taskConditions, Vector3i[] mapMarkers, String npcId, WorldLocationProvider worldLocationProvider) {
/* 33 */     super(descriptionId, taskConditions, mapMarkers);
/* 34 */     this.npcId = npcId;
/* 35 */     this.worldLocationProvider = worldLocationProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BountyObjectiveTaskAsset() {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ObjectiveTaskAsset.TaskScope getTaskScope() {
/* 44 */     return ObjectiveTaskAsset.TaskScope.PLAYER;
/*    */   }
/*    */   
/*    */   public String getNpcId() {
/* 48 */     return this.npcId;
/*    */   }
/*    */   
/*    */   public WorldLocationProvider getWorldLocationProvider() {
/* 52 */     return this.worldLocationProvider;
/*    */   }
/*    */   
/*    */   protected boolean matchesAsset0(ObjectiveTaskAsset task) {
/*    */     BountyObjectiveTaskAsset asset;
/* 57 */     if (task instanceof BountyObjectiveTaskAsset) { asset = (BountyObjectiveTaskAsset)task; } else { return false; }
/*    */     
/* 59 */     if (!Objects.equals(asset.npcId, this.npcId)) return false; 
/* 60 */     return Objects.equals(asset.worldLocationProvider, this.worldLocationProvider);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 66 */     return "BountyObjectiveTaskAsset{npcId='" + this.npcId + "', worldLocationCondition=" + String.valueOf(this.worldLocationProvider) + "} " + super
/*    */ 
/*    */       
/* 69 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcobjectives\assets\BountyObjectiveTaskAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */