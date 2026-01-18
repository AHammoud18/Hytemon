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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface ReferenceSet<K>
/*     */   extends ReferenceCollection<K>, Set<K>
/*     */ {
/*     */   default ObjectSpliterator<K> spliterator() {
/*  87 */     return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 65);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <K> ReferenceSet<K> of() {
/*  97 */     return ReferenceSets.UNMODIFIABLE_EMPTY_SET;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <K> ReferenceSet<K> of(K e) {
/* 107 */     return ReferenceSets.singleton(e);
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
/*     */   static <K> ReferenceSet<K> of(K e0, K e1) {
/* 119 */     ReferenceArraySet<K> innerSet = new ReferenceArraySet<>(2);
/* 120 */     innerSet.add(e0);
/* 121 */     if (!innerSet.add(e1)) {
/* 122 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*     */     }
/* 124 */     return ReferenceSets.unmodifiable(innerSet);
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
/*     */   static <K> ReferenceSet<K> of(K e0, K e1, K e2) {
/* 137 */     ReferenceArraySet<K> innerSet = new ReferenceArraySet<>(3);
/* 138 */     innerSet.add(e0);
/* 139 */     if (!innerSet.add(e1)) {
/* 140 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*     */     }
/* 142 */     if (!innerSet.add(e2)) {
/* 143 */       throw new IllegalArgumentException("Duplicate element: " + e2);
/*     */     }
/* 145 */     return ReferenceSets.unmodifiable(innerSet);
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
/*     */   static <K> ReferenceSet<K> of(K... a) {
/* 157 */     switch (a.length) {
/*     */       case 0:
/* 159 */         return of();
/*     */       case 1:
/* 161 */         return of(a[0]);
/*     */       case 2:
/* 163 */         return of(a[0], a[1]);
/*     */       case 3:
/* 165 */         return of(a[0], a[1], a[2]);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 170 */     ReferenceSet<K> innerSet = (a.length <= 4) ? new ReferenceArraySet<>(a.length) : new ReferenceOpenHashSet<>(a.length);
/* 171 */     for (K element : a) {
/* 172 */       if (!innerSet.add(element)) {
/* 173 */         throw new IllegalArgumentException("Duplicate element: " + element);
/*     */       }
/*     */     } 
/* 176 */     return ReferenceSets.unmodifiable(innerSet);
/*     */   }
/*     */   
/*     */   ObjectIterator<K> iterator();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ReferenceSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */