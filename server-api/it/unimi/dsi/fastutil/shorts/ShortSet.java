/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public interface ShortSet
/*     */   extends ShortCollection, Set<Short>
/*     */ {
/*     */   default ShortSpliterator spliterator() {
/*  79 */     return ShortSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 321);
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
/*     */   default boolean add(Short o) {
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
/*     */   default boolean rem(short k) {
/* 141 */     return remove(k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ShortSet of() {
/* 151 */     return ShortSets.UNMODIFIABLE_EMPTY_SET;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ShortSet of(short e) {
/* 161 */     return ShortSets.singleton(e);
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
/*     */   static ShortSet of(short e0, short e1) {
/* 173 */     ShortArraySet innerSet = new ShortArraySet(2);
/* 174 */     innerSet.add(e0);
/* 175 */     if (!innerSet.add(e1)) {
/* 176 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*     */     }
/* 178 */     return ShortSets.unmodifiable(innerSet);
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
/*     */   static ShortSet of(short e0, short e1, short e2) {
/* 191 */     ShortArraySet innerSet = new ShortArraySet(3);
/* 192 */     innerSet.add(e0);
/* 193 */     if (!innerSet.add(e1)) {
/* 194 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*     */     }
/* 196 */     if (!innerSet.add(e2)) {
/* 197 */       throw new IllegalArgumentException("Duplicate element: " + e2);
/*     */     }
/* 199 */     return ShortSets.unmodifiable(innerSet);
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
/*     */   static ShortSet of(short... a) {
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
/* 224 */     ShortSet innerSet = (a.length <= 4) ? new ShortArraySet(a.length) : new ShortOpenHashSet(a.length);
/* 225 */     for (short element : a) {
/* 226 */       if (!innerSet.add(element)) {
/* 227 */         throw new IllegalArgumentException("Duplicate element: " + element);
/*     */       }
/*     */     } 
/* 230 */     return ShortSets.unmodifiable(innerSet);
/*     */   }
/*     */   
/*     */   ShortIterator iterator();
/*     */   
/*     */   boolean remove(short paramShort);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */