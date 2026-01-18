/*    */ package com.hypixel.hytale.builtin.adventure.objectives.transaction;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldTransactionRecord
/*    */   extends TransactionRecord
/*    */ {
/*    */   public void revert() {}
/*    */   
/*    */   public void complete() {}
/*    */   
/*    */   public void unload() {}
/*    */   
/*    */   public boolean shouldBeSerialized() {
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 27 */     return "WorldTransactionRecord{} " + super
/* 28 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\transaction\WorldTransactionRecord.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */