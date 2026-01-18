/*    */ package io.netty.util.internal.shaded.org.jctools.queues;
/*    */ 
/*    */ import io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class LinkedArrayQueueUtil
/*    */ {
/*    */   public static int length(Object[] buf) {
/* 16 */     return buf.length;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static long modifiedCalcCircularRefElementOffset(long index, long mask) {
/* 26 */     return UnsafeRefArrayAccess.REF_ARRAY_BASE + ((index & mask) << UnsafeRefArrayAccess.REF_ELEMENT_SHIFT - 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public static long nextArrayOffset(Object[] curr) {
/* 31 */     return UnsafeRefArrayAccess.REF_ARRAY_BASE + ((length(curr) - 1) << UnsafeRefArrayAccess.REF_ELEMENT_SHIFT);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctools\queues\LinkedArrayQueueUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */