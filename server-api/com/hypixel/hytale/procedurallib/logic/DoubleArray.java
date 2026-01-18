/*    */ package com.hypixel.hytale.procedurallib.logic;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class DoubleArray
/*    */ {
/*    */   public static class Double2
/*    */   {
/*    */     public final double x;
/*    */     public final double y;
/*    */     
/*    */     public Double2(double x, double y) {
/* 14 */       this.x = x;
/* 15 */       this.y = y;
/*    */     }
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public String toString() {
/* 21 */       return "DoubleArray.Double2{x=" + this.x + ", y=" + this.y + "}";
/*    */     }
/*    */   }
/*    */   
/*    */   public static class Double3
/*    */   {
/*    */     public final double x;
/*    */     public final double y;
/*    */     public final double z;
/*    */     
/*    */     public Double3(double x, double y, double z) {
/* 32 */       this.x = x;
/* 33 */       this.y = y;
/* 34 */       this.z = z;
/*    */     }
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public String toString() {
/* 40 */       return "DoubleArray.Double3{x=" + this.x + ", y=" + this.y + ", z=" + this.z + "}";
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\DoubleArray.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */