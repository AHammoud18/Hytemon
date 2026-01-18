/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObjectBigSpliterators
/*     */ {
/*     */   public static abstract class AbstractIndexBasedSpliterator<K>
/*     */     extends AbstractObjectSpliterator<K>
/*     */   {
/*     */     protected long pos;
/*     */     
/*     */     protected AbstractIndexBasedSpliterator(long initialPos) {
/*  58 */       this.pos = initialPos;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected long computeSplitPoint() {
/* 142 */       return this.pos + (getMaxPos() - this.pos) / 2L;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void splitPointCheck(long splitPoint, long observedMax) {
/* 148 */       if (splitPoint < this.pos || splitPoint > observedMax) {
/* 149 */         throw new IndexOutOfBoundsException("splitPoint " + splitPoint + " outside of range of current position " + this.pos + " and range end " + observedMax);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 156 */       return 16464;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 161 */       return getMaxPos() - this.pos;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(Consumer<? super K> action) {
/* 166 */       if (this.pos >= getMaxPos()) return false; 
/* 167 */       action.accept(get(this.pos++));
/* 168 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super K> action) {
/* 173 */       for (long max = getMaxPos(); this.pos < max; this.pos++) {
/* 174 */         action.accept(get(this.pos));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 180 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 181 */       long max = getMaxPos();
/* 182 */       if (this.pos >= max) return 0L; 
/* 183 */       long remaining = max - this.pos;
/* 184 */       if (n < remaining) {
/* 185 */         this.pos += n;
/* 186 */         return n;
/*     */       } 
/* 188 */       n = remaining;
/* 189 */       this.pos = max;
/* 190 */       return n;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> trySplit() {
/* 210 */       long max = getMaxPos();
/* 211 */       long splitPoint = computeSplitPoint();
/* 212 */       if (splitPoint == this.pos || splitPoint == max) return null; 
/* 213 */       splitPointCheck(splitPoint, max);
/* 214 */       long oldPos = this.pos;
/* 215 */       ObjectSpliterator<K> maybeSplit = makeForSplit(oldPos, splitPoint);
/* 216 */       if (maybeSplit != null) this.pos = splitPoint; 
/* 217 */       return maybeSplit;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract K get(long param1Long);
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract long getMaxPos();
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract ObjectSpliterator<K> makeForSplit(long param1Long1, long param1Long2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class EarlyBindingSizeIndexBasedSpliterator<K>
/*     */     extends AbstractIndexBasedSpliterator<K>
/*     */   {
/*     */     protected final long maxPos;
/*     */ 
/*     */ 
/*     */     
/*     */     protected EarlyBindingSizeIndexBasedSpliterator(long initialPos, long maxPos) {
/* 243 */       super(initialPos);
/* 244 */       this.maxPos = maxPos;
/*     */     }
/*     */ 
/*     */     
/*     */     protected final long getMaxPos() {
/* 249 */       return this.maxPos;
/*     */     }
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
/*     */   public static abstract class LateBindingSizeIndexBasedSpliterator<K>
/*     */     extends AbstractIndexBasedSpliterator<K>
/*     */   {
/* 274 */     protected long maxPos = -1L;
/*     */     private boolean maxPosFixed;
/*     */     
/*     */     protected LateBindingSizeIndexBasedSpliterator(long initialPos) {
/* 278 */       super(initialPos);
/* 279 */       this.maxPosFixed = false;
/*     */     }
/*     */     
/*     */     protected LateBindingSizeIndexBasedSpliterator(long initialPos, long fixedMaxPos) {
/* 283 */       super(initialPos);
/* 284 */       this.maxPos = fixedMaxPos;
/* 285 */       this.maxPosFixed = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final long getMaxPos() {
/* 300 */       return this.maxPosFixed ? this.maxPos : getMaxPosFromBackingStore();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> trySplit() {
/* 305 */       ObjectSpliterator<K> maybeSplit = super.trySplit();
/* 306 */       if (!this.maxPosFixed && maybeSplit != null) {
/* 307 */         this.maxPos = getMaxPosFromBackingStore();
/* 308 */         this.maxPosFixed = true;
/*     */       } 
/* 310 */       return maybeSplit;
/*     */     }
/*     */     
/*     */     protected abstract long getMaxPosFromBackingStore();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectBigSpliterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */