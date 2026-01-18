/*    */ package com.hypixel.hytale.builtin.hytalegenerator.props;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.conveyor.stagedconveyor.ContextDependency;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace.VoxelSpace;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.views.EntityContainer;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Prop
/*    */ {
/*    */   public abstract ScanResult scan(@Nonnull Vector3i paramVector3i, @Nonnull VoxelSpace<Material> paramVoxelSpace, @Nonnull WorkerIndexer.Id paramId);
/*    */   
/*    */   public abstract void place(@Nonnull Context paramContext);
/*    */   
/*    */   public abstract ContextDependency getContextDependency();
/*    */   
/*    */   @Nonnull
/*    */   public abstract Bounds3i getWriteBounds();
/*    */   
/*    */   @Nonnull
/*    */   public static Prop noProp() {
/* 28 */     final ScanResult scanResult = new ScanResult()
/*    */       {
/*    */         public boolean isNegative() {
/* 31 */           return true;
/*    */         }
/*    */       };
/* 34 */     final ContextDependency contextDependency = new ContextDependency(new Vector3i(), new Vector3i());
/* 35 */     final Bounds3i writeBounds_voxelGrid = new Bounds3i();
/* 36 */     return new Prop()
/*    */       {
/*    */         
/*    */         @Nonnull
/*    */         public ScanResult scan(@Nonnull Vector3i position, @Nonnull VoxelSpace<Material> materialSpace, @Nonnull WorkerIndexer.Id id)
/*    */         {
/* 42 */           return scanResult;
/*    */         }
/*    */ 
/*    */         
/*    */         public void place(@Nonnull Prop.Context context) {}
/*    */ 
/*    */         
/*    */         @Nonnull
/*    */         public ContextDependency getContextDependency() {
/* 51 */           return contextDependency;
/*    */         }
/*    */ 
/*    */         
/*    */         @Nonnull
/*    */         public Bounds3i getWriteBounds() {
/* 57 */           return writeBounds_voxelGrid;
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public static class Context
/*    */   {
/*    */     public ScanResult scanResult;
/*    */     
/*    */     public VoxelSpace<Material> materialSpace;
/*    */     
/*    */     public EntityContainer entityBuffer;
/*    */     
/*    */     public WorkerIndexer.Id workerId;
/*    */     
/*    */     public double distanceFromBiomeEdge;
/*    */     
/*    */     public Context(@Nonnull ScanResult scanResult, @Nonnull VoxelSpace<Material> materialSpace, @Nonnull EntityContainer entityBuffer, WorkerIndexer.Id workerId, double distanceFromBiomeEdge) {
/* 76 */       this.scanResult = scanResult;
/* 77 */       this.materialSpace = materialSpace;
/* 78 */       this.entityBuffer = entityBuffer;
/* 79 */       this.workerId = workerId;
/* 80 */       this.distanceFromBiomeEdge = distanceFromBiomeEdge;
/*    */     }
/*    */     
/*    */     public Context(@Nonnull Context other) {
/* 84 */       this.scanResult = other.scanResult;
/* 85 */       this.materialSpace = other.materialSpace;
/* 86 */       this.entityBuffer = other.entityBuffer;
/* 87 */       this.workerId = other.workerId;
/* 88 */       this.distanceFromBiomeEdge = other.distanceFromBiomeEdge;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\props\Prop.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */