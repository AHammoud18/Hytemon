/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharHeapPriorityQueue
/*     */   implements CharPriorityQueue, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  33 */   protected transient char[] heap = CharArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */ 
/*     */   
/*     */   protected CharComparator c;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharHeapPriorityQueue(int capacity, CharComparator c) {
/*  47 */     if (capacity > 0) this.heap = new char[capacity]; 
/*  48 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharHeapPriorityQueue(int capacity) {
/*  57 */     this(capacity, (CharComparator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharHeapPriorityQueue(CharComparator c) {
/*  66 */     this(0, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharHeapPriorityQueue() {
/*  73 */     this(0, (CharComparator)null);
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
/*     */ 
/*     */   
/*     */   public CharHeapPriorityQueue(char[] a, int size, CharComparator c) {
/*  89 */     this(c);
/*  90 */     this.heap = a;
/*  91 */     this.size = size;
/*  92 */     CharHeaps.makeHeap(a, size, c);
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
/*     */   
/*     */   public CharHeapPriorityQueue(char[] a, CharComparator c) {
/* 107 */     this(a, a.length, c);
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
/*     */   
/*     */   public CharHeapPriorityQueue(char[] a, int size) {
/* 122 */     this(a, size, null);
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
/*     */   public CharHeapPriorityQueue(char[] a) {
/* 136 */     this(a, a.length);
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
/*     */   public CharHeapPriorityQueue(CharCollection collection, CharComparator c) {
/* 149 */     this(collection.toCharArray(), c);
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
/*     */   public CharHeapPriorityQueue(CharCollection collection) {
/* 161 */     this(collection, (CharComparator)null);
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
/*     */   public CharHeapPriorityQueue(Collection<? extends Character> collection, CharComparator c) {
/* 174 */     this(collection.size(), c);
/* 175 */     Iterator<? extends Character> iterator = collection.iterator();
/* 176 */     int size = collection.size();
/* 177 */     for (int i = 0; i < size; ) { this.heap[i] = ((Character)iterator.next()).charValue(); i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharHeapPriorityQueue(Collection<? extends Character> collection) {
/* 189 */     this(collection, (CharComparator)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void enqueue(char x) {
/* 194 */     if (this.size == this.heap.length) this.heap = CharArrays.grow(this.heap, this.size + 1); 
/* 195 */     this.heap[this.size++] = x;
/* 196 */     CharHeaps.upHeap(this.heap, this.size, this.size - 1, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public char dequeueChar() {
/* 201 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 202 */     char result = this.heap[0];
/* 203 */     this.heap[0] = this.heap[--this.size];
/* 204 */     if (this.size != 0) CharHeaps.downHeap(this.heap, this.size, 0, this.c); 
/* 205 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public char firstChar() {
/* 210 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 211 */     return this.heap[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public void changed() {
/* 216 */     CharHeaps.downHeap(this.heap, this.size, 0, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 221 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 226 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trim() {
/* 231 */     this.heap = CharArrays.trim(this.heap, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public CharComparator comparator() {
/* 236 */     return this.c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 240 */     s.defaultWriteObject();
/* 241 */     s.writeInt(this.heap.length);
/* 242 */     for (int i = 0; i < this.size; ) { s.writeChar(this.heap[i]); i++; }
/*     */   
/*     */   }
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 246 */     s.defaultReadObject();
/* 247 */     this.heap = new char[s.readInt()];
/* 248 */     for (int i = 0; i < this.size; ) { this.heap[i] = s.readChar(); i++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharHeapPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */