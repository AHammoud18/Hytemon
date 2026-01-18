/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public interface ByteReferencePair<V>
/*     */   extends Pair<Byte, V>
/*     */ {
/*     */   @Deprecated
/*     */   default Byte left() {
/*  38 */     return Byte.valueOf(leftByte());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ByteReferencePair<V> left(byte l) {
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
/*     */   default ByteReferencePair<V> left(Byte l) {
/*  60 */     return left(l.byteValue());
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
/*     */   default byte firstByte() {
/*  72 */     return leftByte();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Byte first() {
/*  83 */     return Byte.valueOf(firstByte());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ByteReferencePair<V> first(byte l) {
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
/*     */   default ByteReferencePair<V> first(Byte l) {
/* 105 */     return first(l.byteValue());
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
/*     */   default byte keyByte() {
/* 117 */     return firstByte();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Byte key() {
/* 128 */     return Byte.valueOf(keyByte());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ByteReferencePair<V> key(byte l) {
/* 139 */     return left(l);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default ByteReferencePair<V> key(Byte l) {
/* 145 */     return key(l.byteValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <V> ByteReferencePair<V> of(byte left, V right) {
/* 156 */     return new ByteReferenceImmutablePair<>(left, right);
/*     */   }
/*     */   
/*     */   byte leftByte();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteReferencePair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */