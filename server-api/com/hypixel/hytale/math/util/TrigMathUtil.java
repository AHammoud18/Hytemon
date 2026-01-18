/*     */ package com.hypixel.hytale.math.util;
/*     */ 
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class TrigMathUtil
/*     */ {
/*     */   public static final float PI = 3.1415927F;
/*     */   public static final float PI_HALF = 1.5707964F;
/*     */   public static final float PI_QUARTER = 0.7853982F;
/*     */   public static final float PI2 = 6.2831855F;
/*     */   public static final float PI4 = 12.566371F;
/*     */   public static final float radToDeg = 57.295776F;
/*     */   public static final float degToRad = 0.017453292F;
/*     */   
/*     */   private static final class Riven
/*     */   {
/*  67 */     private static final int SIN_BITS = 12;
/*  68 */     private static final int SIN_MASK = -1 << SIN_BITS ^ 0xFFFFFFFF;
/*  69 */     private static final int SIN_COUNT = SIN_MASK + 1;
/*     */     
/*  71 */     private static final float radFull = 6.2831855F;
/*     */     
/*  73 */     private static final float radToIndex = SIN_COUNT / radFull; private static final float degFull = 360.0F;
/*  74 */     private static final float degToIndex = SIN_COUNT / degFull;
/*     */     @Nonnull
/*  76 */     private static final float[] SIN = new float[SIN_COUNT]; @Nonnull
/*  77 */     private static final float[] COS = new float[SIN_COUNT]; static {
/*     */       int i;
/*  79 */       for (i = 0; i < SIN_COUNT; i++) {
/*  80 */         SIN[i] = (float)Math.sin(((i + 0.5F) / SIN_COUNT * radFull));
/*  81 */         COS[i] = (float)Math.cos(((i + 0.5F) / SIN_COUNT * radFull));
/*     */       } 
/*     */ 
/*     */       
/*  85 */       for (i = 0; i < 360; i += 90) {
/*  86 */         SIN[(int)(i * degToIndex) & SIN_MASK] = (float)Math.sin(i * Math.PI / 180.0D);
/*  87 */         COS[(int)(i * degToIndex) & SIN_MASK] = (float)Math.cos(i * Math.PI / 180.0D);
/*     */       } 
/*     */     }
/*     */     
/*     */     public static float sin(float rad) {
/*  92 */       return SIN[(int)(rad * radToIndex) & SIN_MASK];
/*     */     }
/*     */     
/*     */     public static float cos(float rad) {
/*  96 */       return COS[(int)(rad * radToIndex) & SIN_MASK];
/*     */     }
/*     */   }
/*     */   
/*     */   public static float sin(float radians) {
/* 101 */     return Riven.sin(radians);
/*     */   }
/*     */   
/*     */   public static float cos(float radians) {
/* 105 */     return Riven.cos(radians);
/*     */   }
/*     */   
/*     */   public static float sin(double radians) {
/* 109 */     return Riven.sin((float)radians);
/*     */   }
/*     */   
/*     */   public static float cos(double radians) {
/* 113 */     return Riven.cos((float)radians);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class Icecore
/*     */   {
/*     */     private static final int SIZE_AC = 100000;
/*     */ 
/*     */     
/*     */     private static final int SIZE_AR = 100001;
/*     */ 
/*     */     
/* 126 */     private static final float[] ATAN2 = new float[100001];
/*     */     
/*     */     static {
/* 129 */       for (int i = 0; i <= 100000; i++) {
/* 130 */         double d = i / 100000.0D;
/* 131 */         double x = 1.0D;
/* 132 */         double y = x * d;
/* 133 */         float v = (float)Math.atan2(y, x);
/* 134 */         ATAN2[i] = v;
/*     */       } 
/*     */     }
/*     */     
/*     */     public static float atan2(float y, float x) {
/* 139 */       if (y < 0.0F) {
/* 140 */         if (x < 0.0F) {
/*     */           
/* 142 */           if (y < x) {
/* 143 */             return -ATAN2[(int)(x / y * 100000.0F)] - 1.5707964F;
/*     */           }
/* 145 */           return ATAN2[(int)(y / x * 100000.0F)] - 3.1415927F;
/*     */         } 
/*     */         
/* 148 */         y = -y;
/* 149 */         if (y > x) {
/* 150 */           return ATAN2[(int)(x / y * 100000.0F)] - 1.5707964F;
/*     */         }
/* 152 */         return -ATAN2[(int)(y / x * 100000.0F)];
/*     */       } 
/*     */ 
/*     */       
/* 156 */       if (x < 0.0F) {
/* 157 */         x = -x;
/* 158 */         if (y > x) {
/* 159 */           return ATAN2[(int)(x / y * 100000.0F)] + 1.5707964F;
/*     */         }
/* 161 */         return -ATAN2[(int)(y / x * 100000.0F)] + 3.1415927F;
/*     */       } 
/*     */       
/* 164 */       if (y > x) {
/* 165 */         return -ATAN2[(int)(x / y * 100000.0F)] + 1.5707964F;
/*     */       }
/* 167 */       return ATAN2[(int)(y / x * 100000.0F)];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float atan2(float y, float x) {
/* 175 */     return Icecore.atan2(y, x);
/*     */   }
/*     */   
/*     */   public static float atan2(double y, double x) {
/* 179 */     return Icecore.atan2((float)y, (float)x);
/*     */   }
/*     */   
/*     */   public static float atan(double d) {
/* 183 */     return (float)Math.atan(d);
/*     */   }
/*     */   
/*     */   public static float asin(double d) {
/* 187 */     return (float)Math.asin(d);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\mat\\util\TrigMathUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */