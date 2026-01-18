/*    */ package com.hypixel.hytale.builtin.hytalegenerator.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */   public static final Consumer<Vector3d> EMPTY_CONSUMER = p -> {
/*    */     
/*    */     };
/*    */   public Vector3d minInclusive;
/*    */   public Vector3d maxExclusive;
/*    */   public Consumer<Vector3d> consumer;
/*    */   @Nullable
/*    */   public Vector3d anchor;
/*    */   public WorkerIndexer.Id workerId;
/*    */   
/*    */   public Context() {
/* 33 */     this.minInclusive = Vector3d.ZERO;
/* 34 */     this.maxExclusive = Vector3d.ZERO;
/* 35 */     this.consumer = EMPTY_CONSUMER;
/* 36 */     this.anchor = null;
/* 37 */     this.workerId = WorkerIndexer.Id.UNKNOWN;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Context(@Nonnull Vector3d minInclusive, @Nonnull Vector3d maxExclusive, @Nonnull Consumer<Vector3d> consumer, @Nullable Vector3d anchor, WorkerIndexer.Id workerId) {
/* 46 */     this.minInclusive = minInclusive;
/* 47 */     this.maxExclusive = maxExclusive;
/* 48 */     this.consumer = consumer;
/* 49 */     this.anchor = anchor;
/* 50 */     this.workerId = workerId;
/*    */   }
/*    */   
/*    */   public Context(@Nonnull Context other) {
/* 54 */     this.minInclusive = other.minInclusive;
/* 55 */     this.maxExclusive = other.maxExclusive;
/* 56 */     this.consumer = other.consumer;
/* 57 */     this.anchor = other.anchor;
/* 58 */     this.workerId = other.workerId;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\positionproviders\PositionProvider$Context.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */