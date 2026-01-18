/*    */ package com.hypixel.hytale.builtin.adventure.objectives.config.task;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.taskcondition.TaskConditionAsset;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class CraftObjectiveTaskAsset
/*    */   extends CountObjectiveTaskAsset
/*    */ {
/*    */   public static final BuilderCodec<CraftObjectiveTaskAsset> CODEC;
/*    */   protected String itemId;
/*    */   
/*    */   static {
/* 21 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(CraftObjectiveTaskAsset.class, CraftObjectiveTaskAsset::new, CountObjectiveTaskAsset.CODEC).append(new KeyedCodec("ItemId", (Codec)Codec.STRING), (objective, entityType) -> objective.itemId = entityType, objective -> objective.itemId).addValidator(Validators.nonNull()).addValidator(Item.VALIDATOR_CACHE.getValidator()).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public CraftObjectiveTaskAsset(String descriptionId, TaskConditionAsset[] taskConditions, Vector3i[] mapMarkers, int count, String itemId) {
/* 26 */     super(descriptionId, taskConditions, mapMarkers, count);
/* 27 */     this.itemId = itemId;
/*    */   }
/*    */ 
/*    */   
/*    */   protected CraftObjectiveTaskAsset() {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ObjectiveTaskAsset.TaskScope getTaskScope() {
/* 36 */     return ObjectiveTaskAsset.TaskScope.PLAYER_AND_MARKER;
/*    */   }
/*    */   
/*    */   public String getItemId() {
/* 40 */     return this.itemId;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean matchesAsset0(ObjectiveTaskAsset task) {
/* 45 */     if (!super.matchesAsset0(task)) return false; 
/* 46 */     if (!(task instanceof CraftObjectiveTaskAsset)) return false;
/*    */     
/* 48 */     return ((CraftObjectiveTaskAsset)task).itemId.equals(this.itemId);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 54 */     return "CraftObjectiveTaskAsset{itemId='" + this.itemId + "'} " + super
/*    */       
/* 56 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\task\CraftObjectiveTaskAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */