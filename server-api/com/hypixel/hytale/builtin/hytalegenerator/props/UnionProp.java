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
/*    */ public class UnionProp
/*    */   extends Prop {
/*    */   @Nonnull
/*    */   private final List<Prop> props;
/*    */   @Nonnull
/*    */   private final ContextDependency contextDependency;
/*    */   @Nonnull
/*    */   private final Bounds3i writeBounds_voxelGrid;
/*    */   
/*    */   public UnionProp(@Nonnull List<Prop> propChain) {
/* 23 */     this.props = new ArrayList<>(propChain);
/*    */     
/* 25 */     Vector3i writeRange = new Vector3i();
/* 26 */     Vector3i readRange = new Vector3i();
/* 27 */     for (Prop prop : propChain) {
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
/* 41 */     ChainedScanResult scanResult = new ChainedScanResult();
/* 42 */     scanResult.scanResults = new ArrayList<>(this.props.size());
/* 43 */     for (Prop prop : this.props) {
/* 44 */       scanResult.scanResults.add(prop.scan(position, materialSpace, id));
/*    */     }
/* 46 */     return scanResult;
/*    */   }
/*    */ 
/*    */   
/*    */   public void place(@Nonnull Prop.Context context) {
/* 51 */     List<ScanResult> scanResults = (ChainedScanResult.cast(context.scanResult)).scanResults;
/*    */     
/* 53 */     for (int i = 0; i < this.props.size(); i++) {
/* 54 */       Prop prop = this.props.get(i);
/* 55 */       Prop.Context childContext = new Prop.Context(context);
/* 56 */       childContext.scanResult = scanResults.get(i);
/*    */       
/* 58 */       prop.place(childContext);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ContextDependency getContextDependency() {
/* 65 */     return this.contextDependency.clone();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Bounds3i getWriteBounds() {
/* 71 */     return this.writeBounds_voxelGrid;
/*    */   }
/*    */   
/*    */   private static class ChainedScanResult implements ScanResult {
/*    */     List<ScanResult> scanResults;
/*    */     
/*    */     @Nonnull
/*    */     public static ChainedScanResult cast(ScanResult scanResult) {
/* 79 */       if (!(scanResult instanceof ChainedScanResult)) {
/* 80 */         throw new IllegalArgumentException("The provided ScanResult isn't compatible with this prop.");
/*    */       }
/* 82 */       return (ChainedScanResult)scanResult;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isNegative() {
/* 87 */       return (this.scanResults == null || this.scanResults.isEmpty());
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\props\UnionProp.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */