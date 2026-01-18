/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes.positions.returntypes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class CellValueReturnType
/*    */   extends ReturnType
/*    */ {
/*    */   @Nonnull
/*    */   private final Density sampleField;
/*    */   private final double defaultValue;
/*    */   
/*    */   public CellValueReturnType(@Nonnull Density sampleField, double defaultValue, int threadCount) {
/* 17 */     this.sampleField = sampleField;
/* 18 */     this.defaultValue = defaultValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double get(double distance0, double distance1, @Nonnull Vector3d samplePosition, @Nullable Vector3d closestPoint0, Vector3d closestPoint1, @Nonnull Density.Context context) {
/* 28 */     if (closestPoint0 == null) return this.defaultValue;
/*    */     
/* 30 */     Density.Context childContext = new Density.Context(context);
/* 31 */     childContext.position = closestPoint0;
/*    */     
/* 33 */     return this.sampleField.process(childContext);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\positions\returntypes\CellValueReturnType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */