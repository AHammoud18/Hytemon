/*    */ package io.netty.util.internal.shaded.org.jctools.util;
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
/*    */ public final class RangeUtil
/*    */ {
/*    */   public static long checkPositive(long n, String name) {
/* 21 */     if (n <= 0L)
/*    */     {
/* 23 */       throw new IllegalArgumentException(name + ": " + n + " (expected: > 0)");
/*    */     }
/*    */     
/* 26 */     return n;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int checkPositiveOrZero(int n, String name) {
/* 31 */     if (n < 0)
/*    */     {
/* 33 */       throw new IllegalArgumentException(name + ": " + n + " (expected: >= 0)");
/*    */     }
/*    */     
/* 36 */     return n;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int checkLessThan(int n, int expected, String name) {
/* 41 */     if (n >= expected)
/*    */     {
/* 43 */       throw new IllegalArgumentException(name + ": " + n + " (expected: < " + expected + ')');
/*    */     }
/*    */     
/* 46 */     return n;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int checkLessThanOrEqual(int n, long expected, String name) {
/* 51 */     if (n > expected)
/*    */     {
/* 53 */       throw new IllegalArgumentException(name + ": " + n + " (expected: <= " + expected + ')');
/*    */     }
/*    */     
/* 56 */     return n;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int checkGreaterThanOrEqual(int n, int expected, String name) {
/* 61 */     if (n < expected)
/*    */     {
/* 63 */       throw new IllegalArgumentException(name + ": " + n + " (expected: >= " + expected + ')');
/*    */     }
/*    */     
/* 66 */     return n;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctool\\util\RangeUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */