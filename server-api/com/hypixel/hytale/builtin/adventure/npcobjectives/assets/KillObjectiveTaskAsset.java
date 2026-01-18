/*    */ package com.hypixel.hytale.builtin.adventure.npcobjectives.assets;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.CountObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.ObjectiveTaskAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.taskcondition.TaskConditionAsset;
/*    */ import com.hypixel.hytale.builtin.tagset.config.NPCGroup;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KillObjectiveTaskAsset
/*    */   extends CountObjectiveTaskAsset
/*    */ {
/*    */   public static final BuilderCodec<KillObjectiveTaskAsset> CODEC;
/*    */   protected String npcGroupId;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(KillObjectiveTaskAsset.class, KillObjectiveTaskAsset::new, CountObjectiveTaskAsset.CODEC).append(new KeyedCodec("NPCGroupId", (Codec)Codec.STRING), (objective, entityType) -> objective.npcGroupId = entityType, objective -> objective.npcGroupId).addValidator(Validators.nonNull()).addValidator(NPCGroup.VALIDATOR_CACHE.getValidator()).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public KillObjectiveTaskAsset(String descriptionId, TaskConditionAsset[] taskConditions, Vector3i[] mapMarkers, int count, String npcGroupId) {
/* 31 */     super(descriptionId, taskConditions, mapMarkers, count);
/* 32 */     this.npcGroupId = npcGroupId;
/*    */   }
/*    */ 
/*    */   
/*    */   protected KillObjectiveTaskAsset() {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ObjectiveTaskAsset.TaskScope getTaskScope() {
/* 41 */     return ObjectiveTaskAsset.TaskScope.PLAYER_AND_MARKER;
/*    */   }
/*    */   
/*    */   public String getNpcGroupId() {
/* 45 */     return this.npcGroupId;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean matchesAsset0(ObjectiveTaskAsset task) {
/* 50 */     if (!super.matchesAsset0(task)) return false; 
/* 51 */     if (!(task instanceof KillObjectiveTaskAsset)) return false;
/*    */     
/* 53 */     return ((KillObjectiveTaskAsset)task).npcGroupId.equals(this.npcGroupId);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 59 */     return "KillObjectiveTaskAsset{npcGroupId='" + this.npcGroupId + "'} " + super
/*    */       
/* 61 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcobjectives\assets\KillObjectiveTaskAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */