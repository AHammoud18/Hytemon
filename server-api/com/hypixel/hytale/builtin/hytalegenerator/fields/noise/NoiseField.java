/*    */ package com.hypixel.hytale.builtin.hytalegenerator.fields.noise;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class NoiseField
/*    */ {
/* 11 */   protected double scaleX = 1.0D, scaleY = 1.0D, scaleZ = 1.0D, scaleW = 1.0D;
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract double valueAt(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract double valueAt(double paramDouble1, double paramDouble2, double paramDouble3);
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract double valueAt(double paramDouble1, double paramDouble2);
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract double valueAt(double paramDouble);
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public NoiseField setScale(double scaleX, double scaleY, double scaleZ, double scaleW) {
/* 32 */     this.scaleX = scaleX;
/* 33 */     this.scaleY = scaleY;
/* 34 */     this.scaleZ = scaleZ;
/* 35 */     this.scaleW = scaleW;
/* 36 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public NoiseField setScale(double scale) {
/* 45 */     this.scaleX = scale;
/* 46 */     this.scaleY = scale;
/* 47 */     this.scaleZ = scale;
/* 48 */     this.scaleW = scale;
/* 49 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\fields\noise\NoiseField.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */