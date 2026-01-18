/*    */ package com.hypixel.hytale.builtin.hytalegenerator.props;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.conveyor.stagedconveyor.ContextDependency;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace.VoxelSpace;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class QueueProp
/*    */   extends Prop {
/*    */   @Nonnull
/*    */   private final List<Prop> props;
/*    */   @Nonnull
/*    */   private final ContextDependency contextDependency;
/*    */   @Nonnull
/*    */   private final Bounds3i writeBounds_voxelGrid;
/*    */   
/*    */   public QueueProp(@Nonnull List<Prop> propsQueue) {
/* 23 */     this.props = new ArrayList<>(propsQueue);
/*    */     
/* 25 */     Vector3i writeRange = new Vector3i();
/* 26 */     Vector3i readRange = new Vector3i();
/* 27 */     for (Prop prop : propsQueue) {
/* 28 */       writeRange = Vector3i.max(writeRange, prop.getContextDependency().getWriteRange());
/* 29 */       readRange = Vector3i.max(readRange, prop.getContextDependency().getReadRange());
/*    */     } 
/*    */     
/* 32 */     this.contextDependency = new ContextDependency(readRange, writeRange);
/* 33 */     this.writeBounds_voxelGrid = this.contextDependency.getTotalPropBounds_voxelGrid();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ScanResult scan(@Nonnull Vector3i position, @Nonnull VoxelSpace<Material> materialSpace, @Nonnull WorkerIndexer.Id id) {
/* 41 */     QueueScanResult queueScanResult = new QueueScanResult();
/* 42 */     for (Prop prop : this.props) {
/* 43 */       ScanResult propScanResult = prop.scan(position, materialSpace, id);
/* 44 */       if (propScanResult.isNegative())
/*    */         continue; 
/* 46 */       queueScanResult.propScanResult = propScanResult;
/* 47 */       queueScanResult.prop = prop;
/* 48 */       return queueScanResult;
/*    */     } 
/* 50 */     return queueScanResult;
/*    */   }
/*    */ 
/*    */   
/*    */   public void place(@Nonnull Prop.Context context) {
/* 55 */     QueueScanResult conditionalScanResult = QueueScanResult.cast(context.scanResult);
/* 56 */     if (conditionalScanResult.isNegative())
/* 57 */       return;  conditionalScanResult.prop.place(context);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ContextDependency getContextDependency() {
/* 63 */     return this.contextDependency.clone();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Bounds3i getWriteBounds() {
/* 69 */     return this.writeBounds_voxelGrid;
/*    */   }
/*    */   
/*    */   private static class QueueScanResult implements ScanResult {
/*    */     ScanResult propScanResult;
/*    */     Prop prop;
/*    */     
/*    */     @Nonnull
/*    */     public static QueueScanResult cast(ScanResult scanResult) {
/* 78 */       if (!(scanResult instanceof QueueScanResult)) {
/* 79 */         throw new IllegalArgumentException("The provided ScanResult isn't compatible with this prop.");
/*    */       }
/* 81 */       return (QueueScanResult)scanResult;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isNegative() {
/* 86 */       return (this.propScanResult == null || this.propScanResult.isNegative());
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\props\QueueProp.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */