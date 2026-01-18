/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
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
/*     */ public final class EmptyPriorityQueue<T>
/*     */   implements PriorityQueue<T>
/*     */ {
/*  24 */   private static final PriorityQueue<Object> INSTANCE = new EmptyPriorityQueue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> EmptyPriorityQueue<V> instance() {
/*  34 */     return (EmptyPriorityQueue)INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeTyped(T node) {
/*  39 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsTyped(T node) {
/*  44 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void priorityChanged(T node) {}
/*     */ 
/*     */   
/*     */   public int size() {
/*  53 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  58 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<T> iterator() {
/*  68 */     return Collections.<T>emptyList().iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/*  73 */     return EmptyArrays.EMPTY_OBJECTS;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T1> T1[] toArray(T1[] a) {
/*  78 */     if (a.length > 0) {
/*  79 */       a[0] = null;
/*     */     }
/*  81 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(T t) {
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(Collection<?> c) {
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends T> c) {
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection<?> c) {
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearIgnoringIndexes() {}
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 124 */     return (o instanceof PriorityQueue && ((PriorityQueue)o).isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 129 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean offer(T t) {
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public T remove() {
/* 139 */     throw new NoSuchElementException();
/*     */   }
/*     */ 
/*     */   
/*     */   public T poll() {
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public T element() {
/* 149 */     throw new NoSuchElementException();
/*     */   }
/*     */ 
/*     */   
/*     */   public T peek() {
/* 154 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 159 */     return EmptyPriorityQueue.class.getSimpleName();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\EmptyPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */