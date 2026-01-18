/*    */ package it.unimi.dsi.fastutil.booleans;
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
/*    */ public final class BooleanIterables
/*    */ {
/*    */   public static long size(BooleanIterable iterable) {
/* 34 */     long c = 0L;
/*    */     
/* 36 */     for (BooleanIterator booleanIterator = iterable.iterator(); booleanIterator.hasNext(); ) { boolean dummy = ((Boolean)booleanIterator.next()).booleanValue(); c++; }
/* 37 */      return c;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanIterables.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */