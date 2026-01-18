/*    */ package io.sentry.cache.tape;
/*    */ 
/*    */ import java.io.Closeable;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.Nullable;
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
/*    */ @Internal
/*    */ public abstract class ObjectQueue<T>
/*    */   implements Iterable<T>, Closeable
/*    */ {
/*    */   public static <T> ObjectQueue<T> create(QueueFile qf, Converter<T> converter) {
/* 35 */     return new FileObjectQueue<>(qf, converter);
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T> ObjectQueue<T> createEmpty() {
/* 40 */     return new EmptyObjectQueue<>();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public abstract QueueFile file();
/*    */ 
/*    */   
/*    */   public abstract int size();
/*    */   
/*    */   public boolean isEmpty() {
/* 51 */     return (size() == 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void add(T paramT) throws IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public abstract T peek() throws IOException;
/*    */ 
/*    */ 
/*    */   
/*    */   public List<T> peek(int max) throws IOException {
/* 68 */     int end = Math.min(max, size());
/* 69 */     List<T> subList = new ArrayList<>(end);
/* 70 */     Iterator<T> iterator = iterator();
/* 71 */     for (int i = 0; i < end; i++) {
/* 72 */       subList.add(iterator.next());
/*    */     }
/* 74 */     return Collections.unmodifiableList(subList);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<T> asList() throws IOException {
/* 79 */     return peek(size());
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove() throws IOException {
/* 84 */     remove(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract void remove(int paramInt) throws IOException;
/*    */ 
/*    */   
/*    */   public void clear() throws IOException {
/* 92 */     remove(size());
/*    */   }
/*    */   
/*    */   public static interface Converter<T> {
/*    */     @Nullable
/*    */     T from(byte[] param1ArrayOfbyte) throws IOException;
/*    */     
/*    */     void toStream(T param1T, OutputStream param1OutputStream) throws IOException;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\cache\tape\ObjectQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */