/*    */ package com.hypixel.hytale.builtin.hytalegenerator.props;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class PositionScanResult
/*    */   implements ScanResult {
/*    */   private Vector3i position;
/*    */   
/*    */   public PositionScanResult(@Nullable Vector3i position) {
/* 12 */     if (position == null)
/* 13 */       return;  this.position = position.clone();
/*    */   }
/*    */   @Nullable
/*    */   public Vector3i getPosition() {
/* 17 */     if (this.position == null) return null; 
/* 18 */     return this.position.clone();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static PositionScanResult cast(ScanResult scanResult) {
/* 23 */     if (!(scanResult instanceof PositionScanResult)) {
/* 24 */       throw new IllegalArgumentException("The provided ScanResult isn't compatible with this prop.");
/*    */     }
/* 26 */     return (PositionScanResult)scanResult;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isNegative() {
/* 31 */     return (this.position == null);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\props\PositionScanResult.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */