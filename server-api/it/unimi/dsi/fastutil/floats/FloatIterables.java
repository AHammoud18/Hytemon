/*    */ package it.unimi.dsi.fastutil.floats;
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
/*    */ public final class FloatIterables
/*    */ {
/*    */   public static long size(FloatIterable iterable) {
/* 34 */     long c = 0L;
/*    */     
/* 36 */     for (FloatIterator floatIterator = iterable.iterator(); floatIterator.hasNext(); ) { float dummy = ((Float)floatIterator.next()).floatValue(); c++; }
/* 37 */      return c;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatIterables.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */