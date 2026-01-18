/*    */ package it.unimi.dsi.fastutil.objects;
/*    */ 
/*    */ import java.util.Iterator;
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
/*    */ 
/*    */ public interface ObjectIterator<K>
/*    */   extends Iterator<K>
/*    */ {
/*    */   default int skip(int n) {
/* 39 */     if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 40 */     int i = n;
/* 41 */     for (; i-- != 0 && hasNext(); next());
/* 42 */     return n - i - 1;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */