/*     */ package it.unimi.dsi.fastutil;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IndirectPriorityQueues
/*     */ {
/*     */   public static class EmptyIndirectPriorityQueue
/*     */     implements IndirectPriorityQueue
/*     */   {
/*     */     public void enqueue(int i) {
/*  43 */       throw new UnsupportedOperationException();
/*     */     } public int dequeue() {
/*  45 */       throw new NoSuchElementException();
/*     */     } public boolean isEmpty() {
/*  47 */       return true;
/*     */     } public int size() {
/*  49 */       return 0;
/*     */     } public boolean contains(int index) {
/*  51 */       return false;
/*     */     }
/*     */     public void clear() {}
/*     */     public int first() {
/*  55 */       throw new NoSuchElementException();
/*     */     } public int last() {
/*  57 */       throw new NoSuchElementException();
/*     */     } public void changed() {
/*  59 */       throw new NoSuchElementException();
/*     */     }
/*     */     public void allChanged() {}
/*     */     public Comparator<?> comparator() {
/*  63 */       return null;
/*     */     } public void changed(int i) {
/*  65 */       throw new IllegalArgumentException("Index " + i + " is not in the queue");
/*     */     } public boolean remove(int i) {
/*  67 */       return false;
/*     */     } public int front(int[] a) {
/*  69 */       return 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public static final EmptyIndirectPriorityQueue EMPTY_QUEUE = new EmptyIndirectPriorityQueue();
/*     */ 
/*     */   
/*     */   public static class SynchronizedIndirectPriorityQueue<K>
/*     */     implements IndirectPriorityQueue<K>
/*     */   {
/*     */     public static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final IndirectPriorityQueue<K> q;
/*     */     
/*     */     protected final Object sync;
/*     */     
/*     */     protected SynchronizedIndirectPriorityQueue(IndirectPriorityQueue<K> q, Object sync) {
/*  89 */       this.q = q;
/*  90 */       this.sync = sync;
/*     */     }
/*     */     
/*     */     protected SynchronizedIndirectPriorityQueue(IndirectPriorityQueue<K> q) {
/*  94 */       this.q = q;
/*  95 */       this.sync = this;
/*     */     }
/*     */     
/*     */     public void enqueue(int x) {
/*  99 */       synchronized (this.sync) { this.q.enqueue(x); }
/*     */     
/* 101 */     } public int dequeue() { synchronized (this.sync) { return this.q.dequeue(); }
/*     */        }
/* 103 */     public boolean contains(int index) { synchronized (this.sync) { return this.q.contains(index); }
/*     */        }
/* 105 */     public int first() { synchronized (this.sync) { return this.q.first(); }
/*     */        }
/* 107 */     public int last() { synchronized (this.sync) { return this.q.last(); }
/*     */        }
/* 109 */     public boolean isEmpty() { synchronized (this.sync) { return this.q.isEmpty(); }
/*     */        }
/* 111 */     public int size() { synchronized (this.sync) { return this.q.size(); }
/*     */        }
/* 113 */     public void clear() { synchronized (this.sync) { this.q.clear(); }
/*     */        }
/* 115 */     public void changed() { synchronized (this.sync) { this.q.changed(); }
/*     */        }
/* 117 */     public void allChanged() { synchronized (this.sync) { this.q.allChanged(); }
/*     */        }
/* 119 */     public void changed(int i) { synchronized (this.sync) { this.q.changed(i); }
/*     */        }
/* 121 */     public boolean remove(int i) { synchronized (this.sync) { return this.q.remove(i); }
/*     */        }
/* 123 */     public Comparator<? super K> comparator() { synchronized (this.sync) { return this.q.comparator(); }
/*     */        } public int front(int[] a) {
/* 125 */       return this.q.front(a);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> IndirectPriorityQueue<K> synchronize(IndirectPriorityQueue<K> q) {
/* 134 */     return new SynchronizedIndirectPriorityQueue<>(q);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> IndirectPriorityQueue<K> synchronize(IndirectPriorityQueue<K> q, Object sync) {
/* 143 */     return new SynchronizedIndirectPriorityQueue<>(q, sync);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\IndirectPriorityQueues.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */