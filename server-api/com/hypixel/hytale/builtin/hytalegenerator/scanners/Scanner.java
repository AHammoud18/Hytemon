/*    */ package com.hypixel.hytale.builtin.hytalegenerator.scanners;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.SpaceSize;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace.VoxelSpace;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public abstract class Scanner
/*    */ {
/*    */   public abstract List<Vector3i> scan(@Nonnull Context paramContext);
/*    */   
/*    */   public abstract SpaceSize scanSpace();
/*    */   
/*    */   @Nonnull
/*    */   public SpaceSize readSpaceWith(@Nonnull Pattern pattern) {
/* 22 */     return SpaceSize.stack(pattern.readSpace(), scanSpace());
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Scanner noScanner() {
/* 27 */     final SpaceSize space = new SpaceSize(new Vector3i(0, 0, 0), new Vector3i(0, 0, 0));
/* 28 */     return new Scanner()
/*    */       {
/*    */         @Nonnull
/*    */         public List<Vector3i> scan(@Nonnull Scanner.Context context) {
/* 32 */           return Collections.emptyList();
/*    */         }
/*    */ 
/*    */         
/*    */         @Nonnull
/*    */         public SpaceSize scanSpace() {
/* 38 */           return space;
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public static class Context
/*    */   {
/*    */     public Vector3i position;
/*    */     
/*    */     public Pattern pattern;
/*    */     
/*    */     public VoxelSpace<Material> materialSpace;
/*    */     
/*    */     public WorkerIndexer.Id workerId;
/*    */     
/*    */     public Context(@Nonnull Vector3i position, @Nonnull Pattern pattern, @Nonnull VoxelSpace<Material> materialSpace, @Nonnull WorkerIndexer.Id workerId) {
/* 55 */       this.position = position;
/* 56 */       this.pattern = pattern;
/* 57 */       this.materialSpace = materialSpace;
/* 58 */       this.workerId = workerId;
/*    */     }
/*    */     
/*    */     public Context(@Nonnull Context other) {
/* 62 */       this.position = other.position;
/* 63 */       this.pattern = other.pattern;
/* 64 */       this.materialSpace = other.materialSpace;
/* 65 */       this.workerId = other.workerId;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\scanners\Scanner.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */