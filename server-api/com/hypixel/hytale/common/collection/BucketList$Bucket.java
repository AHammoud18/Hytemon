/*     */ package com.hypixel.hytale.common.collection;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrays;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class Bucket<E>
/*     */ {
/*     */   protected BucketItem<E>[] bucketItems;
/*     */   protected int size;
/*     */   protected boolean isUnsorted;
/*     */   protected boolean isEmpty;
/*     */   
/*     */   public Bucket(int initialBucketArraySize) {
/* 220 */     this.bucketItems = (BucketItem<E>[])new BucketItem[initialBucketArraySize];
/* 221 */     this.size = 0;
/* 222 */     this.isUnsorted = false;
/* 223 */     this.isEmpty = true;
/*     */   }
/*     */   
/*     */   public BucketItem<E>[] getItems() {
/* 227 */     return this.bucketItems;
/*     */   }
/*     */   
/*     */   public int size() {
/* 231 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isUnsorted() {
/* 235 */     return this.isUnsorted;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 239 */     return this.isEmpty;
/*     */   }
/*     */   
/*     */   public void clear(@Nonnull BucketItemPool<E> pool) {
/* 243 */     if (this.isEmpty)
/* 244 */       return;  pool.deallocate(this.bucketItems, this.size);
/* 245 */     for (int i = 0; i < this.size; ) { this.bucketItems[i] = null; i++; }
/* 246 */      this.size = 0;
/* 247 */     this.isUnsorted = false;
/* 248 */     this.isEmpty = true;
/*     */   }
/*     */   
/*     */   public void add(@Nonnull BucketItem<E> item) {
/* 252 */     this.isEmpty = false;
/* 253 */     if (this.size == this.bucketItems.length) this.bucketItems = (BucketItem<E>[])ObjectArrays.grow((Object[])this.bucketItems, this.size + 1); 
/* 254 */     this.bucketItems[this.size++] = item;
/* 255 */     this.isUnsorted = true;
/*     */   }
/*     */   
/*     */   public void sort(@Nonnull BucketList.SortBufferProvider sortBufferProvider) {
/* 259 */     this.isUnsorted = false;
/*     */ 
/*     */     
/* 262 */     if (this.size <= 1)
/*     */       return; 
/* 264 */     BucketItem[] sortBuffer = sortBufferProvider.apply(this.size);
/* 265 */     System.arraycopy(this.bucketItems, 0, sortBuffer, 0, this.size);
/* 266 */     ObjectArrays.mergeSort((Object[])this.bucketItems, 0, this.size, BucketList.CLOSER_TO_SELF, (Object[])sortBuffer);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\collection\BucketList$Bucket.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */