/*    */ package com.hypixel.hytale.math.shape;
/*    */ 
/*    */ import com.hypixel.hytale.function.predicate.TriIntObjPredicate;
/*    */ import com.hypixel.hytale.function.predicate.TriIntPredicate;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OriginShape<S extends Shape>
/*    */   implements Shape
/*    */ {
/*    */   public final Vector3d origin;
/*    */   public S shape;
/*    */   
/*    */   public OriginShape() {
/* 19 */     this.origin = new Vector3d();
/*    */   }
/*    */   
/*    */   public OriginShape(Vector3d origin, S shape) {
/* 23 */     this.origin = origin;
/* 24 */     this.shape = shape;
/*    */   }
/*    */   
/*    */   public Vector3d getOrigin() {
/* 28 */     return this.origin;
/*    */   }
/*    */   
/*    */   public S getShape() {
/* 32 */     return this.shape;
/*    */   }
/*    */ 
/*    */   
/*    */   public Box getBox(double x, double y, double z) {
/* 37 */     return this.shape.getBox(x + this.origin.getX(), y + this.origin.getY(), z + this.origin.getZ());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean containsPosition(double x, double y, double z) {
/* 42 */     return this.shape.containsPosition(x - this.origin.getX(), y - this.origin.getY(), z - this.origin.getZ());
/*    */   }
/*    */ 
/*    */   
/*    */   public void expand(double radius) {
/* 47 */     this.shape.expand(radius);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean forEachBlock(double x, double y, double z, double epsilon, TriIntPredicate consumer) {
/* 52 */     return this.shape.forEachBlock(x + this.origin.getX(), y + this.origin.getY(), z + this.origin.getZ(), epsilon, consumer);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> boolean forEachBlock(double x, double y, double z, double epsilon, T t, TriIntObjPredicate<T> consumer) {
/* 57 */     return this.shape.forEachBlock(x + this.origin.getX(), y + this.origin.getY(), z + this.origin.getZ(), epsilon, t, consumer);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 63 */     return "OriginShape{origin=" + String.valueOf(this.origin) + ", shape=" + String.valueOf(this.shape) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\shape\OriginShape.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */