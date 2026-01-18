/*     */ package com.hypixel.hytale.math.util;
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
/*     */ final class Icecore
/*     */ {
/*     */   private static final int SIZE_AC = 100000;
/*     */   private static final int SIZE_AR = 100001;
/* 126 */   private static final float[] ATAN2 = new float[100001];
/*     */   
/*     */   static {
/* 129 */     for (int i = 0; i <= 100000; i++) {
/* 130 */       double d = i / 100000.0D;
/* 131 */       double x = 1.0D;
/* 132 */       double y = x * d;
/* 133 */       float v = (float)Math.atan2(y, x);
/* 134 */       ATAN2[i] = v;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static float atan2(float y, float x) {
/* 139 */     if (y < 0.0F) {
/* 140 */       if (x < 0.0F) {
/*     */         
/* 142 */         if (y < x) {
/* 143 */           return -ATAN2[(int)(x / y * 100000.0F)] - 1.5707964F;
/*     */         }
/* 145 */         return ATAN2[(int)(y / x * 100000.0F)] - 3.1415927F;
/*     */       } 
/*     */       
/* 148 */       y = -y;
/* 149 */       if (y > x) {
/* 150 */         return ATAN2[(int)(x / y * 100000.0F)] - 1.5707964F;
/*     */       }
/* 152 */       return -ATAN2[(int)(y / x * 100000.0F)];
/*     */     } 
/*     */ 
/*     */     
/* 156 */     if (x < 0.0F) {
/* 157 */       x = -x;
/* 158 */       if (y > x) {
/* 159 */         return ATAN2[(int)(x / y * 100000.0F)] + 1.5707964F;
/*     */       }
/* 161 */       return -ATAN2[(int)(y / x * 100000.0F)] + 3.1415927F;
/*     */     } 
/*     */     
/* 164 */     if (y > x) {
/* 165 */       return -ATAN2[(int)(x / y * 100000.0F)] + 1.5707964F;
/*     */     }
/* 167 */     return ATAN2[(int)(y / x * 100000.0F)];
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\mat\\util\TrigMathUtil$Icecore.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */