/*    */ package com.hypixel.hytale.builtin.hytalegenerator.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.SpaceSize;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace.VoxelSpace;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Pattern
/*    */ {
/*    */   public abstract boolean matches(@Nonnull Context paramContext);
/*    */   
/*    */   public abstract SpaceSize readSpace();
/*    */   
/*    */   @Nonnull
/*    */   public static Pattern noPattern() {
/* 20 */     final SpaceSize space = new SpaceSize(new Vector3i(0, 0, 0), new Vector3i(0, 0, 0));
/*    */     
/* 22 */     return new Pattern()
/*    */       {
/*    */         public boolean matches(@Nonnull Pattern.Context context) {
/* 25 */           return false;
/*    */         }
/*    */ 
/*    */         
/*    */         @Nonnull
/*    */         public SpaceSize readSpace() {
/* 31 */           return space;
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static Pattern yesPattern() {
/* 39 */     final SpaceSize space = new SpaceSize(new Vector3i(0, 0, 0), new Vector3i(0, 0, 0));
/*    */     
/* 41 */     return new Pattern()
/*    */       {
/*    */         public boolean matches(@Nonnull Pattern.Context context) {
/* 44 */           return true;
/*    */         }
/*    */ 
/*    */         
/*    */         @Nonnull
/*    */         public SpaceSize readSpace() {
/* 50 */           return space;
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public static class Context
/*    */   {
/*    */     public Vector3i position;
/*    */     
/*    */     public VoxelSpace<Material> materialSpace;
/*    */     public WorkerIndexer.Id workerId;
/*    */     
/*    */     public Context(@Nonnull Vector3i position, @Nonnull VoxelSpace<Material> materialSpace, WorkerIndexer.Id workerId) {
/* 64 */       this.position = position;
/* 65 */       this.materialSpace = materialSpace;
/* 66 */       this.workerId = workerId;
/*    */     }
/*    */     
/*    */     public Context(@Nonnull Context other) {
/* 70 */       this.position = other.position;
/* 71 */       this.materialSpace = other.materialSpace;
/* 72 */       this.workerId = other.workerId;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\patterns\Pattern.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */