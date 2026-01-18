/*    */ package com.hypixel.hytale.builtin.hytalegenerator.props.directionality;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.ScanResult;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class RotatedPositionsScanResult
/*    */   implements ScanResult {
/*    */   @Nonnull
/*    */   public final List<RotatedPosition> positions;
/*    */   
/*    */   public RotatedPositionsScanResult(@Nonnull List<RotatedPosition> positions) {
/* 13 */     this.positions = positions;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static RotatedPositionsScanResult cast(ScanResult scanResult) {
/* 18 */     if (!(scanResult instanceof RotatedPositionsScanResult)) {
/* 19 */       throw new IllegalArgumentException("The provided ScanResult isn't compatible with this type.");
/*    */     }
/* 21 */     return (RotatedPositionsScanResult)scanResult;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isNegative() {
/* 26 */     return (this.positions == null || this.positions.isEmpty());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\props\directionality\RotatedPositionsScanResult.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */