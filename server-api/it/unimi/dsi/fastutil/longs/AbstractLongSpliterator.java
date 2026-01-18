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
/*    */ 
/*    */ 
/*    */ public abstract class AbstractLongSpliterator
/*    */   implements LongSpliterator
/*    */ {
/*    */   public final boolean tryAdvance(LongConsumer action) {
/* 37 */     return tryAdvance(action);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void forEachRemaining(LongConsumer action) {
/* 48 */     forEachRemaining(action);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\AbstractLongSpliterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */