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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Pow2
/*    */ {
/*    */   public static final int MAX_POW2 = 1073741824;
/*    */   
/*    */   public static int roundToPowerOfTwo(int value) {
/* 29 */     if (value > 1073741824) {
/* 30 */       throw new IllegalArgumentException("There is no larger power of 2 int for value:" + value + " since it exceeds 2^31.");
/*    */     }
/* 32 */     if (value < 0) {
/* 33 */       throw new IllegalArgumentException("Given value:" + value + ". Expecting value >= 0.");
/*    */     }
/* 35 */     int nextPow2 = 1 << 32 - Integer.numberOfLeadingZeros(value - 1);
/* 36 */     return nextPow2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isPowerOfTwo(int value) {
/* 44 */     return ((value & value - 1) == 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static long align(long value, int alignment) {
/* 56 */     if (!isPowerOfTwo(alignment)) {
/* 57 */       throw new IllegalArgumentException("alignment must be a power of 2:" + alignment);
/*    */     }
/* 59 */     return value + (alignment - 1) & (alignment - 1 ^ 0xFFFFFFFF);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctool\\util\Pow2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */