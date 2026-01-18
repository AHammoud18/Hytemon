/*    */ package com.hypixel.hytale.builtin.hytalegenerator.props;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class PositionListScanResult
/*    */   implements ScanResult
/*    */ {
/*    */   private List<Vector3i> positions;
/*    */   
/*    */   public PositionListScanResult(@Nullable List<Vector3i> positions) {
/* 14 */     if (positions == null)
/* 15 */       return;  this.positions = positions;
/*    */   }
/*    */   @Nullable
/*    */   public List<Vector3i> getPositions() {
/* 19 */     return this.positions;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static PositionListScanResult cast(ScanResult scanResult) {
/* 24 */     if (!(scanResult instanceof PositionListScanResult)) {
/* 25 */       throw new IllegalArgumentException("The provided ScanResult isn't compatible with this prop.");
/*    */     }
/* 27 */     return (PositionListScanResult)scanResult;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isNegative() {
/* 32 */     return (this.positions == null || this.positions.isEmpty());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\props\PositionListScanResult.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */