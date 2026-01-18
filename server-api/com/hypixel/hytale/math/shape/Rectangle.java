/*    */ package com.hypixel.hytale.math.shape;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector2d;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Rectangle
/*    */ {
/*    */   private Vector2d min;
/*    */   private Vector2d max;
/*    */   
/*    */   public Rectangle() {
/* 17 */     this(new Vector2d(), new Vector2d());
/*    */   }
/*    */   
/*    */   public Rectangle(double minX, double minY, double maxX, double maxY) {
/* 21 */     this(new Vector2d(minX, minY), new Vector2d(maxX, maxY));
/*    */   }
/*    */   
/*    */   public Rectangle(Vector2d min, Vector2d max) {
/* 25 */     this.min = min;
/* 26 */     this.max = max;
/*    */   }
/*    */   
/*    */   public Rectangle(@Nonnull Rectangle other) {
/* 30 */     this(other.getMinX(), other.getMinY(), other.getMaxX(), other.getMaxY());
/*    */   }
/*    */   
/*    */   public Vector2d getMin() {
/* 34 */     return this.min;
/*    */   }
/*    */   
/*    */   public Vector2d getMax() {
/* 38 */     return this.max;
/*    */   }
/*    */   
/*    */   public double getMinX() {
/* 42 */     return this.min.x;
/*    */   }
/*    */   
/*    */   public double getMinY() {
/* 46 */     return this.min.y;
/*    */   }
/*    */   
/*    */   public double getMaxX() {
/* 50 */     return this.max.x;
/*    */   }
/*    */   
/*    */   public double getMaxY() {
/* 54 */     return this.max.y;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Rectangle assign(double minX, double minY, double maxX, double maxY) {
/* 59 */     this.min.x = minX;
/* 60 */     this.min.y = minY;
/* 61 */     this.max.x = maxX;
/* 62 */     this.max.y = maxY;
/* 63 */     return this;
/*    */   }
/*    */   
/*    */   public boolean hasArea() {
/* 67 */     return (this.min.x < this.max.x && this.min.y < this.max.y);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 72 */     if (this == o) return true; 
/* 73 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 75 */     Rectangle that = (Rectangle)o;
/*    */     
/* 77 */     if ((this.min != null) ? !this.min.equals(that.min) : (that.min != null)) return false; 
/* 78 */     return (this.max != null) ? this.max.equals(that.max) : ((that.max == null));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 83 */     int result = (this.min != null) ? this.min.hashCode() : 0;
/* 84 */     result = 31 * result + ((this.max != null) ? this.max.hashCode() : 0);
/* 85 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 91 */     return "Rectangle2d{min=" + String.valueOf(this.min) + ", max=" + String.valueOf(this.max) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\shape\Rectangle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */