/*     */ package it.unimi.dsi.fastutil.longs;
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
/*     */ public final class LongPriorityQueues
/*     */ {
/*     */   public static class SynchronizedPriorityQueue
/*     */     implements LongPriorityQueue
/*     */   {
/*     */     protected final LongPriorityQueue q;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedPriorityQueue(LongPriorityQueue q, Object sync) {
/*  34 */       this.q = q;
/*  35 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedPriorityQueue(LongPriorityQueue q) {
/*  39 */       this.q = q;
/*  40 */       this.sync = this;
/*     */     }
/*     */ 
/*     */     
/*     */     public void enqueue(long x) {
/*  45 */       synchronized (this.sync) {
/*  46 */         this.q.enqueue(x);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long dequeueLong() {
/*  52 */       synchronized (this.sync) {
/*  53 */         return this.q.dequeueLong();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long firstLong() {
/*  59 */       synchronized (this.sync) {
/*  60 */         return this.q.firstLong();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastLong() {
/*  66 */       synchronized (this.sync) {
/*  67 */         return this.q.lastLong();
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
/*     */     public LongComparator comparator() {
/* 101 */       synchronized (this.sync) {
/* 102 */         return this.q.comparator();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void enqueue(Long x) {
/* 109 */       synchronized (this.sync) {
/* 110 */         this.q.enqueue(x);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long dequeue() {
/* 117 */       synchronized (this.sync) {
/* 118 */         return this.q.dequeue();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long first() {
/* 125 */       synchronized (this.sync) {
/* 126 */         return this.q.first();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long last() {
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
/*     */   public static LongPriorityQueue synchronize(LongPriorityQueue q) {
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
/*     */   public static LongPriorityQueue synchronize(LongPriorityQueue q, Object sync) {
/* 180 */     return new SynchronizedPriorityQueue(q, sync);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongPriorityQueues.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */