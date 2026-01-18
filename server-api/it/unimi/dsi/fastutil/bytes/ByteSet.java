/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public interface ByteSet
/*     */   extends ByteCollection, Set<Byte>
/*     */ {
/*     */   default ByteSpliterator spliterator() {
/*  79 */     return ByteSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 321);
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
/*     */   default boolean add(Byte o) {
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
/*     */   default boolean rem(byte k) {
/* 141 */     return remove(k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ByteSet of() {
/* 151 */     return ByteSets.UNMODIFIABLE_EMPTY_SET;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ByteSet of(byte e) {
/* 161 */     return ByteSets.singleton(e);
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
/*     */   static ByteSet of(byte e0, byte e1) {
/* 173 */     ByteArraySet innerSet = new ByteArraySet(2);
/* 174 */     innerSet.add(e0);
/* 175 */     if (!innerSet.add(e1)) {
/* 176 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*     */     }
/* 178 */     return ByteSets.unmodifiable(innerSet);
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
/*     */   static ByteSet of(byte e0, byte e1, byte e2) {
/* 191 */     ByteArraySet innerSet = new ByteArraySet(3);
/* 192 */     innerSet.add(e0);
/* 193 */     if (!innerSet.add(e1)) {
/* 194 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*     */     }
/* 196 */     if (!innerSet.add(e2)) {
/* 197 */       throw new IllegalArgumentException("Duplicate element: " + e2);
/*     */     }
/* 199 */     return ByteSets.unmodifiable(innerSet);
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
/*     */   static ByteSet of(byte... a) {
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
/* 224 */     ByteSet innerSet = (a.length <= 4) ? new ByteArraySet(a.length) : new ByteOpenHashSet(a.length);
/* 225 */     for (byte element : a) {
/* 226 */       if (!innerSet.add(element)) {
/* 227 */         throw new IllegalArgumentException("Duplicate element: " + element);
/*     */       }
/*     */     } 
/* 230 */     return ByteSets.unmodifiable(innerSet);
/*     */   }
/*     */   
/*     */   ByteIterator iterator();
/*     */   
/*     */   boolean remove(byte paramByte);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */