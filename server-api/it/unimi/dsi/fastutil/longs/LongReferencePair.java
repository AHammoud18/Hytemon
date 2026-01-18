/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Pair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface LongReferencePair<V>
/*     */   extends Pair<Long, V>
/*     */ {
/*     */   @Deprecated
/*     */   default Long left() {
/*  38 */     return Long.valueOf(leftLong());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default LongReferencePair<V> left(long l) {
/*  49 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default LongReferencePair<V> left(Long l) {
/*  60 */     return left(l.longValue());
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
/*     */   default long firstLong() {
/*  72 */     return leftLong();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Long first() {
/*  83 */     return Long.valueOf(firstLong());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default LongReferencePair<V> first(long l) {
/*  94 */     return left(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default LongReferencePair<V> first(Long l) {
/* 105 */     return first(l.longValue());
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
/*     */   default long keyLong() {
/* 117 */     return firstLong();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Long key() {
/* 128 */     return Long.valueOf(keyLong());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default LongReferencePair<V> key(long l) {
/* 139 */     return left(l);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default LongReferencePair<V> key(Long l) {
/* 145 */     return key(l.longValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <V> LongReferencePair<V> of(long left, V right) {
/* 156 */     return new LongReferenceImmutablePair<>(left, right);
/*     */   }
/*     */   
/*     */   long leftLong();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongReferencePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */