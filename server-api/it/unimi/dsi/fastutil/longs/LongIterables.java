/*    */ package it.unimi.dsi.fastutil.longs;
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
/*    */ public final class LongIterables
/*    */ {
/*    */   public static long size(LongIterable iterable) {
/* 34 */     long c = 0L;
/*    */     
/* 36 */     for (LongIterator longIterator = iterable.iterator(); longIterator.hasNext(); ) { long dummy = ((Long)longIterator.next()).longValue(); c++; }
/* 37 */      return c;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongIterables.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */