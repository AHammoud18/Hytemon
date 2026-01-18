/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Comparator;
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
/*     */ public final class BytePriorityQueues
/*     */ {
/*     */   public static class SynchronizedPriorityQueue
/*     */     implements BytePriorityQueue
/*     */   {
/*     */     protected final BytePriorityQueue q;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedPriorityQueue(BytePriorityQueue q, Object sync) {
/*  34 */       this.q = q;
/*  35 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedPriorityQueue(BytePriorityQueue q) {
/*  39 */       this.q = q;
/*  40 */       this.sync = this;
/*     */     }
/*     */ 
/*     */     
/*     */     public void enqueue(byte x) {
/*  45 */       synchronized (this.sync) {
/*  46 */         this.q.enqueue(x);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public byte dequeueByte() {
/*  52 */       synchronized (this.sync) {
/*  53 */         return this.q.dequeueByte();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public byte firstByte() {
/*  59 */       synchronized (this.sync) {
/*  60 */         return this.q.firstByte();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public byte lastByte() {
/*  66 */       synchronized (this.sync) {
/*  67 */         return this.q.lastByte();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/*  73 */       synchronized (this.sync) {
/*  74 */         return this.q.isEmpty();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  80 */       synchronized (this.sync) {
/*  81 */         return this.q.size();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  87 */       synchronized (this.sync) {
/*  88 */         this.q.clear();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void changed() {
/*  94 */       synchronized (this.sync) {
/*  95 */         this.q.changed();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteComparator comparator() {
/* 101 */       synchronized (this.sync) {
/* 102 */         return this.q.comparator();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void enqueue(Byte x) {
/* 109 */       synchronized (this.sync) {
/* 110 */         this.q.enqueue(x);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte dequeue() {
/* 117 */       synchronized (this.sync) {
/* 118 */         return this.q.dequeue();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte first() {
/* 125 */       synchronized (this.sync) {
/* 126 */         return this.q.first();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte last() {
/* 133 */       synchronized (this.sync) {
/* 134 */         return this.q.last();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 140 */       synchronized (this.sync) {
/* 141 */         return this.q.hashCode();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 147 */       if (o == this) return true; 
/* 148 */       synchronized (this.sync) {
/* 149 */         return this.q.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 154 */       synchronized (this.sync) {
/* 155 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BytePriorityQueue synchronize(BytePriorityQueue q) {
/* 168 */     return new SynchronizedPriorityQueue(q);
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
/*     */   public static BytePriorityQueue synchronize(BytePriorityQueue q, Object sync) {
/* 180 */     return new SynchronizedPriorityQueue(q, sync);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\BytePriorityQueues.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */