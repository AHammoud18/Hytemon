/*    */ package com.hypixel.hytale.builtin.hytalegenerator.tintproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Context
/*    */ {
/*    */   public Vector3i position;
/*    */   public WorkerIndexer.Id workerId;
/*    */   
/*    */   public Context(@Nonnull Vector3i position, WorkerIndexer.Id workerId) {
/* 44 */     this.position = position;
/* 45 */     this.workerId = workerId;
/*    */   }
/*    */   
/*    */   public Context(@Nonnull Context other) {
/* 49 */     this.position = other.position;
/* 50 */     this.workerId = other.workerId;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\tintproviders\TintProvider$Context.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */