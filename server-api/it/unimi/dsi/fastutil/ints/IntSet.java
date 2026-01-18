/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.Spliterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface IntSet
/*     */   extends IntCollection, Set<Integer>
/*     */ {
/*     */   default IntSpliterator spliterator() {
/*  79 */     return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 321);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean remove(Object o) {
/* 102 */     return super.remove(o);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean add(Integer o) {
/* 114 */     return super.add(o);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean contains(Object o) {
/* 126 */     return super.contains(o);
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
/*     */   @Deprecated
/*     */   default boolean rem(int k) {
/* 141 */     return remove(k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static IntSet of() {
/* 151 */     return IntSets.UNMODIFIABLE_EMPTY_SET;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static IntSet of(int e) {
/* 161 */     return IntSets.singleton(e);
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
/*     */   static IntSet of(int e0, int e1) {
/* 173 */     IntArraySet innerSet = new IntArraySet(2);
/* 174 */     innerSet.add(e0);
/* 175 */     if (!innerSet.add(e1)) {
/* 176 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*     */     }
/* 178 */     return IntSets.unmodifiable(innerSet);
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
/*     */   static IntSet of(int e0, int e1, int e2) {
/* 191 */     IntArraySet innerSet = new IntArraySet(3);
/* 192 */     innerSet.add(e0);
/* 193 */     if (!innerSet.add(e1)) {
/* 194 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*     */     }
/* 196 */     if (!innerSet.add(e2)) {
/* 197 */       throw new IllegalArgumentException("Duplicate element: " + e2);
/*     */     }
/* 199 */     return IntSets.unmodifiable(innerSet);
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
/*     */   static IntSet of(int... a) {
/* 211 */     switch (a.length) {
/*     */       case 0:
/* 213 */         return of();
/*     */       case 1:
/* 215 */         return of(a[0]);
/*     */       case 2:
/* 217 */         return of(a[0], a[1]);
/*     */       case 3:
/* 219 */         return of(a[0], a[1], a[2]);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 224 */     IntSet innerSet = (a.length <= 4) ? new IntArraySet(a.length) : new IntOpenHashSet(a.length);
/* 225 */     for (int element : a) {
/* 226 */       if (!innerSet.add(element)) {
/* 227 */         throw new IllegalArgumentException("Duplicate element: " + element);
/*     */       }
/*     */     } 
/* 230 */     return IntSets.unmodifiable(innerSet);
/*     */   }
/*     */   
/*     */   IntIterator iterator();
/*     */   
/*     */   boolean remove(int paramInt);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */