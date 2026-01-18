/*    */ package com.hypixel.hytale.builtin.hytalegenerator.tintproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.Color;
/*    */ import com.hypixel.hytale.server.core.asset.util.ColorParseUtil;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class TintProvider
/*    */ {
/* 11 */   public static final int DEFAULT_TINT = ColorParseUtil.colorToARGBInt(new Color((byte)91, (byte)-98, (byte)40));
/*    */   public abstract Result getValue(@Nonnull Context paramContext);
/*    */   
/* 14 */   public static class Result { public static final Result WITHOUT_VALUE = new Result();
/*    */     
/*    */     public final int tint;
/*    */     public final boolean hasValue;
/*    */     
/*    */     public Result(int tint) {
/* 20 */       this.tint = tint;
/* 21 */       this.hasValue = true;
/*    */     }
/*    */     
/*    */     public Result() {
/* 25 */       this.tint = TintProvider.DEFAULT_TINT;
/* 26 */       this.hasValue = false;
/*    */     } }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static TintProvider noTintProvider() {
/* 34 */     return new ConstantTintProvider(DEFAULT_TINT);
/*    */   }
/*    */ 
/*    */   
/*    */   public static class Context
/*    */   {
/*    */     public Vector3i position;
/*    */     public WorkerIndexer.Id workerId;
/*    */     
/*    */     public Context(@Nonnull Vector3i position, WorkerIndexer.Id workerId) {
/* 44 */       this.position = position;
/* 45 */       this.workerId = workerId;
/*    */     }
/*    */     
/*    */     public Context(@Nonnull Context other) {
/* 49 */       this.position = other.position;
/* 50 */       this.workerId = other.workerId;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\tintproviders\TintProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */