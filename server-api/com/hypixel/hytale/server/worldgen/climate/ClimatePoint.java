/*    */ package com.hypixel.hytale.server.worldgen.climate;
/*    */ 
/*    */ public class ClimatePoint {
/*  4 */   public static final ClimatePoint[] EMPTY_ARRAY = new ClimatePoint[0];
/*    */   
/*    */   public double temperature;
/*    */   public double intensity;
/*    */   public double modifier;
/*    */   
/*    */   public ClimatePoint(double temperature, double intensity) {
/* 11 */     this(temperature, intensity, 1.0D);
/*    */   }
/*    */   
/*    */   public ClimatePoint(double temperature, double intensity, double modifier) {
/* 15 */     this.temperature = temperature;
/* 16 */     this.intensity = intensity;
/* 17 */     this.modifier = modifier;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 22 */     return String.format("t=%.3f, i=%.3f, mod=%.3f", new Object[] { Double.valueOf(this.temperature), Double.valueOf(this.intensity), Double.valueOf(this.modifier) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\climate\ClimatePoint.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */