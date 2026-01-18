/*    */ package it.unimi.dsi.fastutil.doubles;
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
/*    */ public final class DoubleIterables
/*    */ {
/*    */   public static long size(DoubleIterable iterable) {
/* 34 */     long c = 0L;
/*    */     
/* 36 */     for (DoubleIterator doubleIterator = iterable.iterator(); doubleIterator.hasNext(); ) { double dummy = ((Double)doubleIterator.next()).doubleValue(); c++; }
/* 37 */      return c;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleIterables.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */