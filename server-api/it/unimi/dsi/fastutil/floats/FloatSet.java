/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public interface FloatSet
/*     */   extends FloatCollection, Set<Float>
/*     */ {
/*     */   default FloatSpliterator spliterator() {
/*  79 */     return FloatSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 321);
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
/*     */   default boolean add(Float o) {
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
/*     */   default boolean rem(float k) {
/* 141 */     return remove(k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static FloatSet of() {
/* 151 */     return FloatSets.UNMODIFIABLE_EMPTY_SET;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static FloatSet of(float e) {
/* 161 */     return FloatSets.singleton(e);
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
/*     */   static FloatSet of(float e0, float e1) {
/* 173 */     FloatArraySet innerSet = new FloatArraySet(2);
/* 174 */     innerSet.add(e0);
/* 175 */     if (!innerSet.add(e1)) {
/* 176 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*     */     }
/* 178 */     return FloatSets.unmodifiable(innerSet);
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
/*     */   static FloatSet of(float e0, float e1, float e2) {
/* 191 */     FloatArraySet innerSet = new FloatArraySet(3);
/* 192 */     innerSet.add(e0);
/* 193 */     if (!innerSet.add(e1)) {
/* 194 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*     */     }
/* 196 */     if (!innerSet.add(e2)) {
/* 197 */       throw new IllegalArgumentException("Duplicate element: " + e2);
/*     */     }
/* 199 */     return FloatSets.unmodifiable(innerSet);
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
/*     */   static FloatSet of(float... a) {
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
/* 224 */     FloatSet innerSet = (a.length <= 4) ? new FloatArraySet(a.length) : new FloatOpenHashSet(a.length);
/* 225 */     for (float element : a) {
/* 226 */       if (!innerSet.add(element)) {
/* 227 */         throw new IllegalArgumentException("Duplicate element: " + element);
/*     */       }
/*     */     } 
/* 230 */     return FloatSets.unmodifiable(innerSet);
/*     */   }
/*     */   
/*     */   FloatIterator iterator();
/*     */   
/*     */   boolean remove(float paramFloat);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */