/*    */ package io.sentry.cache.tape;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Iterator;
/*    */ import java.util.NoSuchElementException;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ final class EmptyObjectQueue<T> extends ObjectQueue<T> {
/*    */   @Nullable
/*    */   public QueueFile file() {
/* 12 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 17 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(T entry) throws IOException {}
/*    */   
/*    */   @Nullable
/*    */   public T peek() throws IOException {
/* 25 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove(int n) throws IOException {}
/*    */ 
/*    */   
/*    */   public void close() throws IOException {}
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public Iterator<T> iterator() {
/* 37 */     return new EmptyIterator<>();
/*    */   }
/*    */   
/*    */   private static final class EmptyIterator<T> implements Iterator<T> {
/*    */     private EmptyIterator() {}
/*    */     
/*    */     public boolean hasNext() {
/* 44 */       return false;
/*    */     }
/*    */ 
/*    */     
/*    */     public T next() {
/* 49 */       throw new NoSuchElementException("No elements in EmptyIterator!");
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\cache\tape\EmptyObjectQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */