/*     */ package com.hypixel.hytale.math.util;
/*     */ 
/*     */ import java.util.function.DoubleUnaryOperator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IterationElement
/*     */ {
/*     */   private final int ox;
/*     */   private final int oy;
/*     */   private final int oz;
/*     */   private final DoubleUnaryOperator x;
/*     */   private final DoubleUnaryOperator y;
/*     */   private final DoubleUnaryOperator z;
/*     */   
/*     */   public IterationElement(int ox, int oy, int oz, DoubleUnaryOperator x, DoubleUnaryOperator y, DoubleUnaryOperator z) {
/* 105 */     this.ox = ox;
/* 106 */     this.oy = oy;
/* 107 */     this.oz = oz;
/* 108 */     this.x = x;
/* 109 */     this.y = y;
/* 110 */     this.z = z;
/*     */   }
/*     */   
/*     */   public int getOffsetX() {
/* 114 */     return this.ox;
/*     */   }
/*     */   
/*     */   public int getOffsetY() {
/* 118 */     return this.oy;
/*     */   }
/*     */   
/*     */   public int getOffsetZ() {
/* 122 */     return this.oz;
/*     */   }
/*     */   
/*     */   public DoubleUnaryOperator getX() {
/* 126 */     return this.x;
/*     */   }
/*     */   
/*     */   public DoubleUnaryOperator getY() {
/* 130 */     return this.y;
/*     */   }
/*     */   
/*     */   public DoubleUnaryOperator getZ() {
/* 134 */     return this.z;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\mat\\util\NearestBlockUtil$IterationElement.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */