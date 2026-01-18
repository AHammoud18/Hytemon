/*    */ package io.netty.util.internal;
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
/*    */ public final class ReadOnlyIterator<T>
/*    */   implements Iterator<T>
/*    */ {
/*    */   private final Iterator<? extends T> iterator;
/*    */   
/*    */   public ReadOnlyIterator(Iterator<? extends T> iterator) {
/* 25 */     this.iterator = ObjectUtil.<Iterator<? extends T>>checkNotNull(iterator, "iterator");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 30 */     return this.iterator.hasNext();
/*    */   }
/*    */ 
/*    */   
/*    */   public T next() {
/* 35 */     return this.iterator.next();
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove() {
/* 40 */     throw new UnsupportedOperationException("read-only");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\ReadOnlyIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */