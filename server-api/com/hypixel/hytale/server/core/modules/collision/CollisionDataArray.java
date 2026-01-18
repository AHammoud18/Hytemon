/*     */ package com.hypixel.hytale.server.core.modules.collision;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CollisionDataArray<T>
/*     */ {
/*     */   @Nonnull
/*  18 */   private final List<T> array = (List<T>)new ObjectArrayList();
/*     */   
/*     */   private final Supplier<T> supplier;
/*     */   private final Consumer<T> dispose;
/*     */   private final List<T> freeList;
/*     */   private int head;
/*     */   
/*     */   public CollisionDataArray(Supplier<T> supplier, Consumer<T> dispose, List<T> freeList) {
/*  26 */     Objects.requireNonNull(supplier, "Must provide supplier for CollisionDataArray");
/*  27 */     this.supplier = supplier;
/*  28 */     this.dispose = dispose;
/*  29 */     this.freeList = freeList;
/*  30 */     this.head = 0;
/*     */   }
/*     */   
/*     */   public int getCount() {
/*  34 */     return this.array.size() - this.head;
/*     */   }
/*     */   
/*     */   public T alloc() {
/*     */     T result;
/*  39 */     if (this.freeList.isEmpty()) {
/*  40 */       result = this.supplier.get();
/*     */     } else {
/*  42 */       int last = this.freeList.size() - 1;
/*  43 */       result = this.freeList.get(last);
/*  44 */       this.freeList.remove(last);
/*     */     } 
/*  46 */     this.array.add(result);
/*  47 */     return result;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  51 */     int count = this.array.size();
/*  52 */     if (count > 0) {
/*  53 */       if (this.dispose != null) {
/*  54 */         for (int i = 0; i < count; i++) {
/*  55 */           T value = this.array.get(i);
/*  56 */           this.dispose.accept(value);
/*  57 */           this.freeList.add(value);
/*     */         } 
/*     */       } else {
/*  60 */         for (int i = 0; i < count; i++) {
/*  61 */           T value = this.array.get(i);
/*  62 */           this.freeList.add(value);
/*     */         } 
/*     */       } 
/*  65 */       this.array.clear();
/*  66 */       this.head = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T getFirst() {
/*  73 */     return (this.head < this.array.size()) ? this.array.get(this.head) : null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T forgetFirst() {
/*  78 */     this.head++;
/*  79 */     return getFirst();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  83 */     return this.array.isEmpty();
/*     */   }
/*     */   
/*     */   public void sort(Comparator<? super T> comparator) {
/*  87 */     this.array.sort(comparator);
/*     */   }
/*     */   
/*     */   public void remove(int l) {
/*  91 */     int index = this.head + l;
/*  92 */     if (index < this.array.size()) {
/*  93 */       this.freeList.add(this.array.get(index));
/*  94 */       this.array.remove(index);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int size() {
/*  99 */     return this.array.size() - this.head;
/*     */   }
/*     */   
/*     */   public T get(int i) {
/* 103 */     return this.array.get(this.head + i);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\CollisionDataArray.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */