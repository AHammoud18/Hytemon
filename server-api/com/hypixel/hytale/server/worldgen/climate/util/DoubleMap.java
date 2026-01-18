/*    */ package com.hypixel.hytale.server.worldgen.climate.util;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class DoubleMap
/*    */ {
/*    */   public final int width;
/*    */   
/*    */   public DoubleMap(int width, int height) {
/* 10 */     this.width = width;
/* 11 */     this.height = height;
/* 12 */     this.values = new double[width * height];
/* 13 */     clear();
/*    */   }
/*    */   public final int height; protected final double[] values;
/*    */   public int index(int x, int y) {
/* 17 */     return y * this.width + x;
/*    */   }
/*    */   
/*    */   public boolean validate(int index) {
/* 21 */     return (index > -1 && index < this.values.length);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 25 */     Arrays.fill(this.values, -1.0D);
/*    */   }
/*    */   
/*    */   public double at(int x, int y) {
/* 29 */     return at(index(x, y));
/*    */   }
/*    */   
/*    */   public double at(int index) {
/* 33 */     return this.values[index];
/*    */   }
/*    */   
/*    */   public void set(int x, int y, double value) {
/* 37 */     set(index(x, y), value);
/*    */   }
/*    */   
/*    */   public void set(int index, double value) {
/* 41 */     this.values[index] = value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\climat\\util\DoubleMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */