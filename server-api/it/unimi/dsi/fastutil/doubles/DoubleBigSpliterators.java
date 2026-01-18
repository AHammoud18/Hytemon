/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.DoubleConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DoubleBigSpliterators
/*     */ {
/*     */   public static abstract class AbstractIndexBasedSpliterator
/*     */     extends AbstractDoubleSpliterator
/*     */   {
/*     */     protected long pos;
/*     */     
/*     */     protected AbstractIndexBasedSpliterator(long initialPos) {
/*  56 */       this.pos = initialPos;
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
/* 140 */       return this.pos + (getMaxPos() - this.pos) / 2L;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void splitPointCheck(long splitPoint, long observedMax) {
/* 146 */       if (splitPoint < this.pos || splitPoint > observedMax) {
/* 147 */         throw new IndexOutOfBoundsException("splitPoint " + splitPoint + " outside of range of current position " + this.pos + " and range end " + observedMax);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 154 */       return 16720;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 159 */       return getMaxPos() - this.pos;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(DoubleConsumer action) {
/* 164 */       if (this.pos >= getMaxPos()) return false; 
/* 165 */       action.accept(get(this.pos++));
/* 166 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(DoubleConsumer action) {
/* 171 */       for (long max = getMaxPos(); this.pos < max; this.pos++) {
/* 172 */         action.accept(get(this.pos));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 178 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 179 */       long max = getMaxPos();
/* 180 */       if (this.pos >= max) return 0L; 
/* 181 */       long remaining = max - this.pos;
/* 182 */       if (n < remaining) {
/* 183 */         this.pos += n;
/* 184 */         return n;
/*     */       } 
/* 186 */       n = remaining;
/* 187 */       this.pos = max;
/* 188 */       return n;
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
/*     */     public DoubleSpliterator trySplit() {
/* 208 */       long max = getMaxPos();
/* 209 */       long splitPoint = computeSplitPoint();
/* 210 */       if (splitPoint == this.pos || splitPoint == max) return null; 
/* 211 */       splitPointCheck(splitPoint, max);
/* 212 */       long oldPos = this.pos;
/* 213 */       DoubleSpliterator maybeSplit = makeForSplit(oldPos, splitPoint);
/* 214 */       if (maybeSplit != null) this.pos = splitPoint; 
/* 215 */       return maybeSplit;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract double get(long param1Long);
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract long getMaxPos();
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract DoubleSpliterator makeForSplit(long param1Long1, long param1Long2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class EarlyBindingSizeIndexBasedSpliterator
/*     */     extends AbstractIndexBasedSpliterator
/*     */   {
/*     */     protected final long maxPos;
/*     */ 
/*     */ 
/*     */     
/*     */     protected EarlyBindingSizeIndexBasedSpliterator(long initialPos, long maxPos) {
/* 241 */       super(initialPos);
/* 242 */       this.maxPos = maxPos;
/*     */     }
/*     */ 
/*     */     
/*     */     protected final long getMaxPos() {
/* 247 */       return this.maxPos;
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
/*     */   public static abstract class LateBindingSizeIndexBasedSpliterator
/*     */     extends AbstractIndexBasedSpliterator
/*     */   {
/* 272 */     protected long maxPos = -1L;
/*     */     private boolean maxPosFixed;
/*     */     
/*     */     protected LateBindingSizeIndexBasedSpliterator(long initialPos) {
/* 276 */       super(initialPos);
/* 277 */       this.maxPosFixed = false;
/*     */     }
/*     */     
/*     */     protected LateBindingSizeIndexBasedSpliterator(long initialPos, long fixedMaxPos) {
/* 281 */       super(initialPos);
/* 282 */       this.maxPos = fixedMaxPos;
/* 283 */       this.maxPosFixed = true;
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
/* 298 */       return this.maxPosFixed ? this.maxPos : getMaxPosFromBackingStore();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSpliterator trySplit() {
/* 303 */       DoubleSpliterator maybeSplit = super.trySplit();
/* 304 */       if (!this.maxPosFixed && maybeSplit != null) {
/* 305 */         this.maxPos = getMaxPosFromBackingStore();
/* 306 */         this.maxPosFixed = true;
/*     */       } 
/* 308 */       return maybeSplit;
/*     */     }
/*     */     
/*     */     protected abstract long getMaxPosFromBackingStore();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleBigSpliterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */