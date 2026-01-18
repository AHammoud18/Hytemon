/*    */ package com.hypixel.hytale.server.worldgen.util;
/*    */ 
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArrayUtli
/*    */ {
/*    */   public static void shuffleArray(@Nonnull int[] ar, @Nonnull Random rnd) {
/* 14 */     for (int i = ar.length - 1; i > 0; i--) {
/* 15 */       int index = rnd.nextInt(i + 1);
/*    */       
/* 17 */       int a = ar[index];
/* 18 */       ar[index] = ar[i];
/* 19 */       ar[i] = a;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static <T> void shuffleArray(@Nonnull T[] ar, @Nonnull Random rnd) {
/* 24 */     for (int i = ar.length - 1; i > 0; i--) {
/* 25 */       int index = rnd.nextInt(i + 1);
/*    */       
/* 27 */       T a = ar[index];
/* 28 */       ar[index] = ar[i];
/* 29 */       ar[i] = a;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\ArrayUtli.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */