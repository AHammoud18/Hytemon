/*    */ package it.unimi.dsi.fastutil.shorts;
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
/*    */ public final class ShortIterables
/*    */ {
/*    */   public static long size(ShortIterable iterable) {
/* 34 */     long c = 0L;
/*    */     
/* 36 */     for (ShortIterator shortIterator = iterable.iterator(); shortIterator.hasNext(); ) { short dummy = ((Short)shortIterator.next()).shortValue(); c++; }
/* 37 */      return c;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortIterables.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */