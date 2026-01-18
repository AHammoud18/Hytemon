/*    */ package com.hypixel.hytale.builtin.hytalegenerator.environmentproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class EnvironmentProvider
/*    */ {
/*    */   public abstract int getValue(@Nonnull Context paramContext);
/*    */   
/*    */   @Nonnull
/*    */   public static EnvironmentProvider noEnvironmentProvider() {
/* 15 */     return new ConstantEnvironmentProvider(0);
/*    */   }
/*    */ 
/*    */   
/*    */   public static class Context
/*    */   {
/*    */     public Vector3i position;
/*    */     public WorkerIndexer.Id workerId;
/*    */     
/*    */     public Context(@Nonnull Vector3i position, WorkerIndexer.Id workerId) {
/* 25 */       this.position = position;
/* 26 */       this.workerId = workerId;
/*    */     }
/*    */     
/*    */     public Context(@Nonnull Context other) {
/* 30 */       this.position = other.position;
/* 31 */       this.workerId = other.workerId;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\environmentproviders\EnvironmentProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */