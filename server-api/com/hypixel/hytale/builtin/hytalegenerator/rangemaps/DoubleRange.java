/*    */ package com.hypixel.hytale.builtin.hytalegenerator.rangemaps;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DoubleRange {
/*    */   private double min;
/*    */   private double max;
/*    */   private boolean inclusiveMin;
/*    */   private boolean inclusiveMax;
/*    */   
/*    */   public DoubleRange(double min, boolean inclusiveMin, double max, boolean inclusiveMax) {
/* 12 */     if (min > max)
/* 13 */       throw new IllegalArgumentException("min greater than max"); 
/* 14 */     this.min = min;
/* 15 */     this.max = max;
/* 16 */     this.inclusiveMin = inclusiveMin;
/* 17 */     this.inclusiveMax = inclusiveMax;
/*    */   }
/*    */   
/*    */   public double getMin() {
/* 21 */     return this.min;
/*    */   }
/*    */   
/*    */   public boolean isInclusiveMin() {
/* 25 */     return this.inclusiveMin;
/*    */   }
/*    */   
/*    */   public double getMax() {
/* 29 */     return this.max;
/*    */   }
/*    */   
/*    */   public boolean isInclusiveMax() {
/* 33 */     return this.inclusiveMax;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean includes(double v) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: getfield inclusiveMin : Z
/*    */     //   4: ifeq -> 19
/*    */     //   7: dload_1
/*    */     //   8: aload_0
/*    */     //   9: getfield min : D
/*    */     //   12: dcmpl
/*    */     //   13: iflt -> 60
/*    */     //   16: goto -> 28
/*    */     //   19: dload_1
/*    */     //   20: aload_0
/*    */     //   21: getfield min : D
/*    */     //   24: dcmpl
/*    */     //   25: ifle -> 60
/*    */     //   28: aload_0
/*    */     //   29: getfield inclusiveMax : Z
/*    */     //   32: ifeq -> 47
/*    */     //   35: dload_1
/*    */     //   36: aload_0
/*    */     //   37: getfield max : D
/*    */     //   40: dcmpg
/*    */     //   41: ifgt -> 60
/*    */     //   44: goto -> 56
/*    */     //   47: dload_1
/*    */     //   48: aload_0
/*    */     //   49: getfield max : D
/*    */     //   52: dcmpg
/*    */     //   53: ifge -> 60
/*    */     //   56: iconst_1
/*    */     //   57: goto -> 61
/*    */     //   60: iconst_0
/*    */     //   61: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #37	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	62	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/rangemaps/DoubleRange;
/*    */     //   0	62	1	v	D
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static DoubleRange inclusive(double min, double max) {
/* 43 */     return new DoubleRange(min, true, max, true);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static DoubleRange exclusive(double min, double max) {
/* 48 */     return new DoubleRange(min, false, max, false);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\rangemaps\DoubleRange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */