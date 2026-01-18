/*    */ package it.unimi.dsi.fastutil.bytes;
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
/*    */ public final class ByteIterables
/*    */ {
/*    */   public static long size(ByteIterable iterable) {
/* 34 */     long c = 0L;
/*    */     
/* 36 */     for (ByteIterator byteIterator = iterable.iterator(); byteIterator.hasNext(); ) { byte dummy = ((Byte)byteIterator.next()).byteValue(); c++; }
/* 37 */      return c;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteIterables.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */