/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntSpliterator;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.IntPredicate;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.IntStream;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.stream.StreamSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface ByteCollection
/*     */   extends Collection<Byte>, ByteIterable
/*     */ {
/*     */   default IntIterator intIterator() {
/*  66 */     return super.intIterator();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default ByteSpliterator spliterator() {
/*  99 */     return ByteSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 320);
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
/*     */   default IntSpliterator intSpliterator() {
/* 115 */     return super.intSpliterator();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   default boolean add(Byte key) {
/* 154 */     return add(key.byteValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean contains(Object key) {
/* 165 */     if (key == null) return false; 
/* 166 */     return contains(((Byte)key).byteValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean remove(Object key) {
/* 177 */     if (key == null) return false; 
/* 178 */     return rem(((Byte)key).byteValue());
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default byte[] toByteArray(byte[] a) {
/* 204 */     return toArray(a);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   default boolean removeIf(Predicate<? super Byte> filter) {
/* 256 */     return removeIf((filter instanceof BytePredicate) ? (BytePredicate)filter : (key -> filter.test(Byte.valueOf(SafeMath.safeIntToByte(key)))));
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
/*     */   default boolean removeIf(BytePredicate filter) {
/* 270 */     Objects.requireNonNull(filter);
/* 271 */     boolean removed = false;
/* 272 */     ByteIterator each = iterator();
/* 273 */     while (each.hasNext()) {
/* 274 */       if (filter.test(each.nextByte())) {
/* 275 */         each.remove();
/* 276 */         removed = true;
/*     */       } 
/*     */     } 
/* 279 */     return removed;
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
/*     */   default boolean removeIf(IntPredicate filter) {
/* 294 */     Objects.requireNonNull(filter); return removeIf((filter instanceof BytePredicate) ? (BytePredicate)filter : filter::test);
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
/*     */   @Deprecated
/*     */   default Stream<Byte> stream() {
/* 314 */     return super.stream();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default IntStream intStream() {
/* 325 */     return StreamSupport.intStream((Spliterator.OfInt)intSpliterator(), false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Stream<Byte> parallelStream() {
/* 336 */     return super.parallelStream();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default IntStream intParallelStream() {
/* 347 */     return StreamSupport.intStream((Spliterator.OfInt)intSpliterator(), true);
/*     */   }
/*     */   
/*     */   ByteIterator iterator();
/*     */   
/*     */   boolean add(byte paramByte);
/*     */   
/*     */   boolean contains(byte paramByte);
/*     */   
/*     */   boolean rem(byte paramByte);
/*     */   
/*     */   byte[] toByteArray();
/*     */   
/*     */   byte[] toArray(byte[] paramArrayOfbyte);
/*     */   
/*     */   boolean addAll(ByteCollection paramByteCollection);
/*     */   
/*     */   boolean containsAll(ByteCollection paramByteCollection);
/*     */   
/*     */   boolean removeAll(ByteCollection paramByteCollection);
/*     */   
/*     */   boolean retainAll(ByteCollection paramByteCollection);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */