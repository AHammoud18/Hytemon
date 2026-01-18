/*    */ package com.hypixel.hytale.builtin.adventure.objectives.config.task;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.taskcondition.TaskConditionAsset;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class UseBlockObjectiveTaskAsset
/*    */   extends CountObjectiveTaskAsset {
/*    */   public static final BuilderCodec<UseBlockObjectiveTaskAsset> CODEC;
/*    */   protected BlockTagOrItemIdField blockTagOrItemIdField;
/*    */   
/*    */   static {
/* 18 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(UseBlockObjectiveTaskAsset.class, UseBlockObjectiveTaskAsset::new, CountObjectiveTaskAsset.CODEC).append(new KeyedCodec("BlockTagOrItemId", (Codec)BlockTagOrItemIdField.CODEC), (useBlockObjectiveTaskAsset, blockTypeOrSetTaskField) -> useBlockObjectiveTaskAsset.blockTagOrItemIdField = blockTypeOrSetTaskField, useBlockObjectiveTaskAsset -> useBlockObjectiveTaskAsset.blockTagOrItemIdField).addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public UseBlockObjectiveTaskAsset(String descriptionId, TaskConditionAsset[] taskConditions, Vector3i[] mapMarkers, int count, BlockTagOrItemIdField blockTagOrItemIdField) {
/* 23 */     super(descriptionId, taskConditions, mapMarkers, count);
/* 24 */     this.blockTagOrItemIdField = blockTagOrItemIdField;
/*    */   }
/*    */ 
/*    */   
/*    */   protected UseBlockObjectiveTaskAsset() {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ObjectiveTaskAsset.TaskScope getTaskScope() {
/* 33 */     return ObjectiveTaskAsset.TaskScope.PLAYER_AND_MARKER;
/*    */   }
/*    */   
/*    */   public BlockTagOrItemIdField getBlockTagOrItemIdField() {
/* 37 */     return this.blockTagOrItemIdField;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean matchesAsset0(ObjectiveTaskAsset task) {
/* 42 */     if (!super.matchesAsset0(task)) return false; 
/* 43 */     if (!(task instanceof UseBlockObjectiveTaskAsset)) return false;
/*    */     
/* 45 */     return ((UseBlockObjectiveTaskAsset)task).blockTagOrItemIdField.equals(this.blockTagOrItemIdField);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 51 */     return "UseBlockObjectiveTaskAsset{blockTagOrItemIdField=" + String.valueOf(this.blockTagOrItemIdField) + "} " + super
/*    */       
/* 53 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\task\UseBlockObjectiveTaskAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */