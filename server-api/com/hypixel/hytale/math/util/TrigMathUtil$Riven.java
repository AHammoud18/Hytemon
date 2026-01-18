/*    */ package com.hypixel.hytale.math.util;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class Riven
/*    */ {
/* 67 */   private static final int SIN_BITS = 12;
/* 68 */   private static final int SIN_MASK = -1 << SIN_BITS ^ 0xFFFFFFFF;
/* 69 */   private static final int SIN_COUNT = SIN_MASK + 1;
/*    */   
/* 71 */   private static final float radFull = 6.2831855F; private static final float radToIndex;
/* 72 */   private static final float degFull = 360.0F; private static final float degToIndex; static {
/* 73 */     radToIndex = SIN_COUNT / radFull;
/* 74 */     degToIndex = SIN_COUNT / degFull;
/*    */     
/* 76 */     SIN = new float[SIN_COUNT];
/* 77 */     COS = new float[SIN_COUNT];
/*    */     int i;
/* 79 */     for (i = 0; i < SIN_COUNT; i++) {
/* 80 */       SIN[i] = (float)Math.sin(((i + 0.5F) / SIN_COUNT * radFull));
/* 81 */       COS[i] = (float)Math.cos(((i + 0.5F) / SIN_COUNT * radFull));
/*    */     } 
/*    */ 
/*    */     
/* 85 */     for (i = 0; i < 360; i += 90) {
/* 86 */       SIN[(int)(i * degToIndex) & SIN_MASK] = (float)Math.sin(i * Math.PI / 180.0D);
/* 87 */       COS[(int)(i * degToIndex) & SIN_MASK] = (float)Math.cos(i * Math.PI / 180.0D);
/*    */     } 
/*    */   } @Nonnull
/*    */   private static final float[] SIN; @Nonnull
/*    */   private static final float[] COS; public static float sin(float rad) {
/* 92 */     return SIN[(int)(rad * radToIndex) & SIN_MASK];
/*    */   }
/*    */   
/*    */   public static float cos(float rad) {
/* 96 */     return COS[(int)(rad * radToIndex) & SIN_MASK];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\mat\\util\TrigMathUtil$Riven.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */