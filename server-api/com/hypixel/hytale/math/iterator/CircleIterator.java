/*    */ package com.hypixel.hytale.math.iterator;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.Iterator;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class CircleIterator
/*    */   implements Iterator<Vector3d>
/*    */ {
/*    */   private final Vector3d origin;
/*    */   private final int pointTotal;
/*    */   private final double radius;
/*    */   private final float angleOffset;
/*    */   private int pointIndex;
/*    */   
/*    */   public CircleIterator(Vector3d origin, double radius, int pointTotal) {
/* 19 */     this(origin, radius, pointTotal, 0.0F);
/*    */   }
/*    */   
/*    */   public CircleIterator(Vector3d origin, double radius, int pointTotal, float angleOffset) {
/* 23 */     this.origin = origin;
/* 24 */     this.pointTotal = pointTotal;
/* 25 */     this.angleOffset = angleOffset;
/* 26 */     this.pointIndex = 0;
/* 27 */     this.radius = radius;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 32 */     return (this.pointIndex < this.pointTotal);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d next() {
/* 38 */     this.pointIndex++;
/* 39 */     float angle = this.pointIndex / this.pointTotal * 6.2831855F + this.angleOffset;
/* 40 */     return new Vector3d(TrigMathUtil.cos(angle) * this.radius + this.origin.getX(), this.origin.getY(), TrigMathUtil.sin(angle) * this.radius + this.origin.getZ());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\iterator\CircleIterator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */