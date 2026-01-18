/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public final class ShortPriorityQueues
/*     */ {
/*     */   public static class SynchronizedPriorityQueue
/*     */     implements ShortPriorityQueue
/*     */   {
/*     */     protected final ShortPriorityQueue q;
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedPriorityQueue(ShortPriorityQueue q, Object sync) {
/*  34 */       this.q = q;
/*  35 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedPriorityQueue(ShortPriorityQueue q) {
/*  39 */       this.q = q;
/*  40 */       this.sync = this;
/*     */     }
/*     */ 
/*     */     
/*     */     public void enqueue(short x) {
/*  45 */       synchronized (this.sync) {
/*  46 */         this.q.enqueue(x);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public short dequeueShort() {
/*  52 */       synchronized (this.sync) {
/*  53 */         return this.q.dequeueShort();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public short firstShort() {
/*  59 */       synchronized (this.sync) {
/*  60 */         return this.q.firstShort();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public short lastShort() {
/*  66 */       synchronized (this.sync) {
/*  67 */         return this.q.lastShort();
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
/*     */     public ShortComparator comparator() {
/* 101 */       synchronized (this.sync) {
/* 102 */         return this.q.comparator();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public void enqueue(Short x) {
/* 109 */       synchronized (this.sync) {
/* 110 */         this.q.enqueue(x);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short dequeue() {
/* 117 */       synchronized (this.sync) {
/* 118 */         return this.q.dequeue();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short first() {
/* 125 */       synchronized (this.sync) {
/* 126 */         return this.q.first();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short last() {
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
/*     */   public static ShortPriorityQueue synchronize(ShortPriorityQueue q) {
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
/*     */   public static ShortPriorityQueue synchronize(ShortPriorityQueue q, Object sync) {
/* 180 */     return new SynchronizedPriorityQueue(q, sync);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortPriorityQueues.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */