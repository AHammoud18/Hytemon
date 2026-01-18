/*     */ package it.unimi.dsi.fastutil;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import java.util.NoSuchElementException;
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
/*     */ public class PriorityQueues
/*     */ {
/*     */   public static class EmptyPriorityQueue
/*     */     implements PriorityQueue, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 0L;
/*     */     
/*     */     public void enqueue(Object o) {
/*  45 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     public Object dequeue() {
/*  48 */       throw new NoSuchElementException();
/*     */     }
/*     */     public boolean isEmpty() {
/*  51 */       return true;
/*     */     }
/*     */     public int size() {
/*  54 */       return 0;
/*     */     }
/*     */     
/*     */     public void clear() {}
/*     */     
/*     */     public Object first() {
/*  60 */       throw new NoSuchElementException();
/*     */     }
/*     */     public Object last() {
/*  63 */       throw new NoSuchElementException();
/*     */     }
/*     */     public void changed() {
/*  66 */       throw new NoSuchElementException();
/*     */     }
/*     */     public Comparator<?> comparator() {
/*  69 */       return null;
/*     */     }
/*     */     public Object clone() {
/*  72 */       return PriorityQueues.EMPTY_QUEUE;
/*     */     }
/*     */     public int hashCode() {
/*  75 */       return 0;
/*     */     }
/*     */     public boolean equals(Object o) {
/*  78 */       return (o instanceof PriorityQueue && ((PriorityQueue)o).isEmpty());
/*     */     } private Object readResolve() {
/*  80 */       return PriorityQueues.EMPTY_QUEUE;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  85 */   public static final EmptyPriorityQueue EMPTY_QUEUE = new EmptyPriorityQueue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> PriorityQueue<K> emptyQueue() {
/*  95 */     return EMPTY_QUEUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SynchronizedPriorityQueue<K>
/*     */     implements PriorityQueue<K>, Serializable
/*     */   {
/*     */     public static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final PriorityQueue<K> q;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedPriorityQueue(PriorityQueue<K> q, Object sync) {
/* 107 */       this.q = q;
/* 108 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedPriorityQueue(PriorityQueue<K> q) {
/* 112 */       this.q = q;
/* 113 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public void enqueue(K x) {
/* 117 */       synchronized (this.sync) { this.q.enqueue(x); }
/*     */     
/*     */     } public K dequeue() {
/* 120 */       synchronized (this.sync) { return this.q.dequeue(); }
/*     */     
/*     */     } public K first() {
/* 123 */       synchronized (this.sync) { return this.q.first(); }
/*     */     
/*     */     } public K last() {
/* 126 */       synchronized (this.sync) { return this.q.last(); }
/*     */     
/*     */     } public boolean isEmpty() {
/* 129 */       synchronized (this.sync) { return this.q.isEmpty(); }
/*     */     
/*     */     } public int size() {
/* 132 */       synchronized (this.sync) { return this.q.size(); }
/*     */     
/*     */     } public void clear() {
/* 135 */       synchronized (this.sync) { this.q.clear(); }
/*     */     
/*     */     } public void changed() {
/* 138 */       synchronized (this.sync) { this.q.changed(); }
/*     */     
/*     */     } public Comparator<? super K> comparator() {
/* 141 */       synchronized (this.sync) { return this.q.comparator(); }
/*     */     
/*     */     } public String toString() {
/* 144 */       synchronized (this.sync) { return this.q.toString(); }
/*     */     
/*     */     } public int hashCode() {
/* 147 */       synchronized (this.sync) { return this.q.hashCode(); }
/*     */     
/*     */     } public boolean equals(Object o) {
/* 150 */       if (o == this) return true;  synchronized (this.sync) { return this.q.equals(o); }
/*     */     
/*     */     } private void writeObject(ObjectOutputStream s) throws IOException {
/* 153 */       synchronized (this.sync) { s.defaultWriteObject(); }
/*     */     
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> PriorityQueue<K> synchronize(PriorityQueue<K> q) {
/* 164 */     return new SynchronizedPriorityQueue<>(q);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> PriorityQueue<K> synchronize(PriorityQueue<K> q, Object sync) {
/* 174 */     return new SynchronizedPriorityQueue<>(q, sync);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\PriorityQueues.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */