/*    */ package com.hypixel.hytale.common.util;
/*    */ 
/*    */ import java.security.SecureRandom;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class RandomUtil {
/* 13 */   public static final ThreadLocal<SecureRandom> SECURE_RANDOM = ThreadLocal.withInitial(SecureRandom::new);
/*    */   
/*    */   public static <T> T roll(int roll, T[] data, @Nonnull int[] chances) {
/* 16 */     roll++;
/* 17 */     int lower = 0;
/* 18 */     int upper = 0;
/*    */     
/* 20 */     for (int i = 0; i < chances.length; i++) {
/* 21 */       int thisOne = chances[i];
/* 22 */       upper += thisOne;
/* 23 */       if (roll > lower && roll <= upper) {
/* 24 */         return data[i];
/*    */       }
/*    */       
/* 27 */       lower += thisOne;
/*    */     } 
/*    */     
/* 30 */     throw new AssertionError("Reached end of roll(" + roll + ", " + Arrays.toString(data) + ", " + Arrays.toString(chances) + ")!");
/*    */   }
/*    */   
/*    */   public static int rollInt(int roll, int[] data, @Nonnull int[] chances) {
/* 34 */     roll++;
/* 35 */     int lower = 0;
/* 36 */     int upper = 0;
/*    */     
/* 38 */     for (int i = 0; i < chances.length; i++) {
/* 39 */       int thisOne = chances[i];
/* 40 */       upper += thisOne;
/* 41 */       if (roll > lower && roll <= upper) {
/* 42 */         return data[i];
/*    */       }
/*    */       
/* 45 */       lower += thisOne;
/*    */     } 
/*    */     
/* 48 */     throw new AssertionError("Reached end of roll(" + roll + ", " + Arrays.toString(data) + ", " + Arrays.toString(chances) + ")!");
/*    */   }
/*    */   
/*    */   public static SecureRandom getSecureRandom() {
/* 52 */     return SECURE_RANDOM.get();
/*    */   }
/*    */   
/*    */   public static <T> T selectRandom(@Nonnull T[] arr, @Nonnull Random random) {
/* 56 */     return arr[random.nextInt(arr.length)];
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static <T> T selectRandomOrNull(@Nonnull T[] arr, @Nonnull Random random) {
/* 61 */     int index = random.nextInt(arr.length + 1);
/* 62 */     if (index == arr.length) return null; 
/* 63 */     return arr[index];
/*    */   }
/*    */   
/*    */   public static <T> T selectRandom(@Nonnull List<? extends T> list) {
/* 67 */     return selectRandom(list, ThreadLocalRandom.current());
/*    */   }
/*    */   
/*    */   public static <T> T selectRandom(@Nonnull List<? extends T> list, @Nonnull Random random) {
/* 71 */     return list.get(random.nextInt(list.size()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\commo\\util\RandomUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */