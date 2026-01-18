/*    */ package com.hypixel.hytale.builtin.hytalegenerator.props.filler;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.ScanResult;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class FillerPropScanResult
/*    */   implements ScanResult
/*    */ {
/*    */   private List<Vector3i> positions;
/*    */   
/*    */   public FillerPropScanResult(@Nullable List<Vector3i> positions) {
/* 15 */     if (positions == null)
/* 16 */       return;  this.positions = positions;
/*    */   }
/*    */   @Nullable
/*    */   public List<Vector3i> getFluidBlocks() {
/* 20 */     return this.positions;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static FillerPropScanResult cast(ScanResult scanResult) {
/* 25 */     if (!(scanResult instanceof FillerPropScanResult)) {
/* 26 */       throw new IllegalArgumentException("The provided ScanResult isn't compatible with this prop.");
/*    */     }
/* 28 */     return (FillerPropScanResult)scanResult;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isNegative() {
/* 33 */     return (this.positions == null || this.positions.isEmpty());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\props\filler\FillerPropScanResult.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */