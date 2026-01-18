/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public interface CharSet
/*     */   extends CharCollection, Set<Character>
/*     */ {
/*     */   default CharSpliterator spliterator() {
/*  79 */     return CharSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 321);
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
/*     */   default boolean add(Character o) {
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
/*     */   default boolean rem(char k) {
/* 141 */     return remove(k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static CharSet of() {
/* 151 */     return CharSets.UNMODIFIABLE_EMPTY_SET;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static CharSet of(char e) {
/* 161 */     return CharSets.singleton(e);
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
/*     */   static CharSet of(char e0, char e1) {
/* 173 */     CharArraySet innerSet = new CharArraySet(2);
/* 174 */     innerSet.add(e0);
/* 175 */     if (!innerSet.add(e1)) {
/* 176 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*     */     }
/* 178 */     return CharSets.unmodifiable(innerSet);
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
/*     */   static CharSet of(char e0, char e1, char e2) {
/* 191 */     CharArraySet innerSet = new CharArraySet(3);
/* 192 */     innerSet.add(e0);
/* 193 */     if (!innerSet.add(e1)) {
/* 194 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*     */     }
/* 196 */     if (!innerSet.add(e2)) {
/* 197 */       throw new IllegalArgumentException("Duplicate element: " + e2);
/*     */     }
/* 199 */     return CharSets.unmodifiable(innerSet);
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
/*     */   static CharSet of(char... a) {
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
/* 224 */     CharSet innerSet = (a.length <= 4) ? new CharArraySet(a.length) : new CharOpenHashSet(a.length);
/* 225 */     for (char element : a) {
/* 226 */       if (!innerSet.add(element)) {
/* 227 */         throw new IllegalArgumentException("Duplicate element: " + element);
/*     */       }
/*     */     } 
/* 230 */     return CharSets.unmodifiable(innerSet);
/*     */   }
/*     */   
/*     */   CharIterator iterator();
/*     */   
/*     */   boolean remove(char paramChar);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */