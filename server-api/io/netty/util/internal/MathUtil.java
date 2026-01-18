/*    */ package io.netty.util.internal;
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
/*    */ public final class MathUtil
/*    */ {
/*    */   public static int findNextPositivePowerOfTwo(int value) {
/* 35 */     assert value > Integer.MIN_VALUE && value < 1073741824;
/* 36 */     return 1 << 32 - Integer.numberOfLeadingZeros(value - 1);
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
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int safeFindNextPositivePowerOfTwo(int value) {
/* 52 */     return (value <= 0) ? 1 : ((value >= 1073741824) ? 1073741824 : findNextPositivePowerOfTwo(value));
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
/*    */   public static boolean isOutOfBounds(int index, int length, int capacity) {
/* 64 */     return ((index | length | capacity | index + length) < 0 || index + length > capacity);
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
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static int compare(int x, int y) {
/* 80 */     return Integer.compare(x, y);
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
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static int compare(long x, long y) {
/* 97 */     return Long.compare(x, y);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\MathUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */