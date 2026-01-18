/*    */ package com.hypixel.hytale.builtin.adventure.objectives.transaction;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TransactionUtil
/*    */ {
/*    */   public static boolean anyFailed(@Nullable TransactionRecord[] transactionRecords) {
/* 11 */     if (transactionRecords == null) return false; 
/* 12 */     for (TransactionRecord transactionRecord : transactionRecords) {
/* 13 */       if (transactionRecord.status == TransactionStatus.FAIL) return true; 
/*    */     } 
/* 15 */     return false;
/*    */   }
/*    */   
/*    */   public static void revertAll(@Nullable TransactionRecord[] transactionRecords) {
/* 19 */     if (transactionRecords == null)
/* 20 */       return;  for (TransactionRecord transactionRecord : transactionRecords) {
/* 21 */       transactionRecord.revert();
/*    */     }
/*    */   }
/*    */   
/*    */   public static void completeAll(@Nullable TransactionRecord[] transactionRecords) {
/* 26 */     if (transactionRecords == null)
/* 27 */       return;  for (TransactionRecord transactionRecord : transactionRecords) {
/* 28 */       transactionRecord.complete();
/*    */     }
/*    */   }
/*    */   
/*    */   public static void unloadAll(@Nullable TransactionRecord[] transactionRecords) {
/* 33 */     if (transactionRecords == null)
/* 34 */       return;  for (TransactionRecord transactionRecord : transactionRecords)
/* 35 */       transactionRecord.unload(); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\transaction\TransactionUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */