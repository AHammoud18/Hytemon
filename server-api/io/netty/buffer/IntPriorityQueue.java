/*     */ package io.netty.buffer;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ final class IntPriorityQueue
/*     */ {
/*     */   public static final int NO_VALUE = -1;
/*  26 */   private int[] array = new int[9];
/*     */   private int size;
/*     */   
/*     */   public void offer(int handle) {
/*  30 */     if (handle == -1) {
/*  31 */       throw new IllegalArgumentException("The NO_VALUE (-1) cannot be added to the queue.");
/*     */     }
/*  33 */     this.size++;
/*  34 */     if (this.size == this.array.length)
/*     */     {
/*  36 */       this.array = Arrays.copyOf(this.array, 1 + (this.array.length - 1) * 2);
/*     */     }
/*  38 */     this.array[this.size] = handle;
/*  39 */     lift(this.size);
/*     */   }
/*     */   
/*     */   public void remove(int value) {
/*  43 */     for (int i = 1; i <= this.size; i++) {
/*  44 */       if (this.array[i] == value) {
/*  45 */         this.array[i] = this.array[this.size--];
/*  46 */         lift(i);
/*  47 */         sink(i);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int peek() {
/*  54 */     if (this.size == 0) {
/*  55 */       return -1;
/*     */     }
/*  57 */     return this.array[1];
/*     */   }
/*     */   
/*     */   public int poll() {
/*  61 */     if (this.size == 0) {
/*  62 */       return -1;
/*     */     }
/*  64 */     int val = this.array[1];
/*  65 */     this.array[1] = this.array[this.size];
/*  66 */     this.array[this.size] = 0;
/*  67 */     this.size--;
/*  68 */     sink(1);
/*  69 */     return val;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  73 */     return (this.size == 0);
/*     */   }
/*     */   
/*     */   private void lift(int index) {
/*     */     int parentIndex;
/*  78 */     while (index > 1 && subord(parentIndex = index >> 1, index)) {
/*  79 */       swap(index, parentIndex);
/*  80 */       index = parentIndex;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sink(int index) {
/*     */     int child;
/*  86 */     while ((child = index << 1) <= this.size) {
/*  87 */       if (child < this.size && subord(child, child + 1)) {
/*  88 */         child++;
/*     */       }
/*  90 */       if (!subord(index, child)) {
/*     */         break;
/*     */       }
/*  93 */       swap(index, child);
/*  94 */       index = child;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean subord(int a, int b) {
/*  99 */     return (this.array[a] > this.array[b]);
/*     */   }
/*     */   
/*     */   private void swap(int a, int b) {
/* 103 */     int value = this.array[a];
/* 104 */     this.array[a] = this.array[b];
/* 105 */     this.array[b] = value;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\IntPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */