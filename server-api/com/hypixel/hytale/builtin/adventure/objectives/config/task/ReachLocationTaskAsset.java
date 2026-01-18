/*    */ package com.hypixel.hytale.builtin.adventure.objectives.config.task;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.markers.reachlocation.ReachLocationMarkerAsset;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReachLocationTaskAsset
/*    */   extends ObjectiveTaskAsset
/*    */ {
/*    */   public static final BuilderCodec<ReachLocationTaskAsset> CODEC;
/*    */   protected String targetLocationId;
/*    */   
/*    */   static {
/* 23 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ReachLocationTaskAsset.class, ReachLocationTaskAsset::new, BASE_CODEC).append(new KeyedCodec("TargetLocation", (Codec)Codec.STRING), (reachLocationTaskAsset, vector3i) -> reachLocationTaskAsset.targetLocationId = vector3i, reachLocationTaskAsset -> reachLocationTaskAsset.targetLocationId).addValidator(Validators.nonNull()).addValidator(ReachLocationMarkerAsset.VALIDATOR_CACHE.getValidator()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ObjectiveTaskAsset.TaskScope getTaskScope() {
/* 30 */     return ObjectiveTaskAsset.TaskScope.PLAYER;
/*    */   }
/*    */   
/*    */   public String getTargetLocationId() {
/* 34 */     return this.targetLocationId;
/*    */   }
/*    */   
/*    */   protected boolean matchesAsset0(ObjectiveTaskAsset task) {
/*    */     ReachLocationTaskAsset asset;
/* 39 */     if (task instanceof ReachLocationTaskAsset) { asset = (ReachLocationTaskAsset)task; } else { return false; }
/*    */     
/* 41 */     return Objects.equals(asset.targetLocationId, this.targetLocationId);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 47 */     return "ReachLocationTaskAsset{targetLocationId=" + this.targetLocationId + "} " + super
/*    */       
/* 49 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\task\ReachLocationTaskAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */