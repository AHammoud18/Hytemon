/*     */ package io.sentry.cache.tape;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class FileObjectQueue<T>
/*     */   extends ObjectQueue<T>
/*     */ {
/*     */   private final QueueFile queueFile;
/*  31 */   private final DirectByteArrayOutputStream bytes = new DirectByteArrayOutputStream();
/*     */   
/*     */   final ObjectQueue.Converter<T> converter;
/*     */   
/*     */   FileObjectQueue(QueueFile queueFile, ObjectQueue.Converter<T> converter) {
/*  36 */     this.queueFile = queueFile;
/*  37 */     this.converter = converter;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public QueueFile file() {
/*  42 */     return this.queueFile;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  47 */     return this.queueFile.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  52 */     return this.queueFile.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(T entry) throws IOException {
/*  57 */     this.bytes.reset();
/*  58 */     this.converter.toStream(entry, this.bytes);
/*  59 */     this.queueFile.add(this.bytes.getArray(), 0, this.bytes.size());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T peek() throws IOException {
/*  64 */     byte[] bytes = this.queueFile.peek();
/*  65 */     if (bytes == null) return null; 
/*  66 */     return this.converter.from(bytes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove() throws IOException {
/*  71 */     this.queueFile.remove();
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(int n) throws IOException {
/*  76 */     this.queueFile.remove(n);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() throws IOException {
/*  81 */     this.queueFile.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  86 */     this.queueFile.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<T> iterator() {
/* 100 */     return new QueueFileIterator(this.queueFile.iterator());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 105 */     return "FileObjectQueue{queueFile=" + this.queueFile + '}';
/*     */   }
/*     */   
/*     */   private final class QueueFileIterator implements Iterator<T> {
/*     */     final Iterator<byte[]> iterator;
/*     */     
/*     */     QueueFileIterator(Iterator<byte[]> iterator) {
/* 112 */       this.iterator = iterator;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 117 */       return this.iterator.hasNext();
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public T next() {
/* 123 */       byte[] data = this.iterator.next();
/*     */       try {
/* 125 */         return FileObjectQueue.this.converter.from(data);
/* 126 */       } catch (IOException e) {
/* 127 */         throw (Error)QueueFile.getSneakyThrowable(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 133 */       this.iterator.remove();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class DirectByteArrayOutputStream
/*     */     extends ByteArrayOutputStream
/*     */   {
/*     */     byte[] getArray() {
/* 146 */       return this.buf;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\cache\tape\FileObjectQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */