/*     */ package it.unimi.dsi.fastutil.objects;
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
/*     */ public interface ObjectSet<K>
/*     */   extends ObjectCollection<K>, Set<K>
/*     */ {
/*     */   default ObjectSpliterator<K> spliterator() {
/*  79 */     return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 65);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <K> ObjectSet<K> of() {
/*  89 */     return ObjectSets.UNMODIFIABLE_EMPTY_SET;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <K> ObjectSet<K> of(K e) {
/*  99 */     return ObjectSets.singleton(e);
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
/*     */   static <K> ObjectSet<K> of(K e0, K e1) {
/* 111 */     ObjectArraySet<K> innerSet = new ObjectArraySet<>(2);
/* 112 */     innerSet.add(e0);
/* 113 */     if (!innerSet.add(e1)) {
/* 114 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*     */     }
/* 116 */     return ObjectSets.unmodifiable(innerSet);
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
/*     */   static <K> ObjectSet<K> of(K e0, K e1, K e2) {
/* 129 */     ObjectArraySet<K> innerSet = new ObjectArraySet<>(3);
/* 130 */     innerSet.add(e0);
/* 131 */     if (!innerSet.add(e1)) {
/* 132 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*     */     }
/* 134 */     if (!innerSet.add(e2)) {
/* 135 */       throw new IllegalArgumentException("Duplicate element: " + e2);
/*     */     }
/* 137 */     return ObjectSets.unmodifiable(innerSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SafeVarargs
/*     */   static <K> ObjectSet<K> of(K... a) {
/* 149 */     switch (a.length) {
/*     */       case 0:
/* 151 */         return of();
/*     */       case 1:
/* 153 */         return of(a[0]);
/*     */       case 2:
/* 155 */         return of(a[0], a[1]);
/*     */       case 3:
/* 157 */         return of(a[0], a[1], a[2]);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 162 */     ObjectSet<K> innerSet = (a.length <= 4) ? new ObjectArraySet<>(a.length) : new ObjectOpenHashSet<>(a.length);
/* 163 */     for (K element : a) {
/* 164 */       if (!innerSet.add(element)) {
/* 165 */         throw new IllegalArgumentException("Duplicate element: " + element);
/*     */       }
/*     */     } 
/* 168 */     return ObjectSets.unmodifiable(innerSet);
/*     */   }
/*     */   
/*     */   ObjectIterator<K> iterator();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */