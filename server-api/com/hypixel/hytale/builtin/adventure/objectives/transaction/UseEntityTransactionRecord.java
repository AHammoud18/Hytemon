/*    */ package com.hypixel.hytale.builtin.adventure.objectives.transaction;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class UseEntityTransactionRecord
/*    */   extends TransactionRecord {
/*    */   protected UUID objectiveUUID;
/*    */   protected String taskId;
/*    */   
/*    */   public UseEntityTransactionRecord(UUID objectiveUUID, String taskId) {
/* 13 */     this.objectiveUUID = objectiveUUID;
/* 14 */     this.taskId = taskId;
/*    */   }
/*    */ 
/*    */   
/*    */   public void revert() {
/* 19 */     ObjectivePlugin.get().getObjectiveDataStore().removeEntityTask(this.objectiveUUID, this.taskId);
/*    */   }
/*    */ 
/*    */   
/*    */   public void complete() {
/* 24 */     ObjectivePlugin.get().getObjectiveDataStore().removeEntityTask(this.objectiveUUID, this.taskId);
/*    */   }
/*    */ 
/*    */   
/*    */   public void unload() {
/* 29 */     ObjectivePlugin.get().getObjectiveDataStore().removeEntityTask(this.objectiveUUID, this.taskId);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldBeSerialized() {
/* 34 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 40 */     return "UseEntityTransactionRecord{objectiveUUID=" + String.valueOf(this.objectiveUUID) + ", taskId='" + this.taskId + "'} " + super
/*    */ 
/*    */       
/* 43 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\transaction\UseEntityTransactionRecord.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */