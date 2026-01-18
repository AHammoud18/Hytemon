/*     */ package com.hypixel.hytale.server.spawning.suppression;
/*     */ 
/*     */ import com.hypixel.hytale.server.spawning.suppression.component.ChunkSuppressionEntry;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SuppressionSpanHelper
/*     */ {
/*  16 */   private static final ThreadLocal<ArrayDeque<Span>> SPAN_POOL = ThreadLocal.withInitial(ArrayDeque::new);
/*     */   
/*  18 */   private final List<Span> optimisedSuppressionSpans = (List<Span>)new ObjectArrayList();
/*     */   
/*  20 */   private int currentSpanIndex = 0;
/*     */   
/*     */   public void optimiseSuppressedSpans(int roleIndex, @Nullable ChunkSuppressionEntry entry) {
/*  23 */     if (entry == null)
/*     */       return; 
/*  25 */     ArrayDeque<Span> spanPool = SPAN_POOL.get();
/*     */     
/*  27 */     List<ChunkSuppressionEntry.SuppressionSpan> suppressionSpans = entry.getSuppressionSpans();
/*     */ 
/*     */     
/*  30 */     Span initialSpan = allocateSpan(spanPool);
/*  31 */     initialSpan.init(0, 0);
/*  32 */     this.optimisedSuppressionSpans.add(initialSpan);
/*     */     
/*  34 */     boolean matchedRole = false;
/*  35 */     for (ChunkSuppressionEntry.SuppressionSpan suppressionSpan : suppressionSpans) {
/*  36 */       if (!suppressionSpan.includesRole(roleIndex))
/*     */         continue; 
/*  38 */       matchedRole = true;
/*  39 */       int minY = suppressionSpan.getMinY();
/*  40 */       int maxY = suppressionSpan.getMaxY();
/*     */ 
/*     */       
/*  43 */       Span latestSpan = this.optimisedSuppressionSpans.getLast();
/*  44 */       if (latestSpan.includes(minY)) {
/*  45 */         if (latestSpan.includes(maxY))
/*     */           continue; 
/*  47 */         latestSpan.expandTo(maxY);
/*     */         
/*     */         continue;
/*     */       } 
/*  51 */       Span span = allocateSpan(spanPool);
/*  52 */       span.init(minY, maxY);
/*  53 */       this.optimisedSuppressionSpans.add(span);
/*     */     } 
/*     */     
/*  56 */     if (!matchedRole) {
/*     */       
/*  58 */       Span span = this.optimisedSuppressionSpans.removeFirst();
/*  59 */       span.reset();
/*  60 */       spanPool.push(span);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int adjustSpawnRangeMin(int min) {
/*  65 */     if (this.optimisedSuppressionSpans.isEmpty()) return min;
/*     */     
/*  67 */     int maxSpanIndex = this.optimisedSuppressionSpans.size() - 1;
/*  68 */     Span currentSpan = this.optimisedSuppressionSpans.get(this.currentSpanIndex);
/*     */     
/*  70 */     while (min >= currentSpan.max && this.currentSpanIndex < maxSpanIndex) {
/*  71 */       this.currentSpanIndex++;
/*  72 */       currentSpan = this.optimisedSuppressionSpans.get(this.currentSpanIndex);
/*     */     } 
/*     */     
/*  75 */     if (currentSpan.includes(min)) {
/*  76 */       if (this.currentSpanIndex < maxSpanIndex) this.currentSpanIndex++; 
/*  77 */       return currentSpan.max;
/*     */     } 
/*     */     
/*  80 */     return min;
/*     */   }
/*     */   
/*     */   public int adjustSpawnRangeMax(int min, int max) {
/*  84 */     if (this.optimisedSuppressionSpans.isEmpty()) return max;
/*     */     
/*  86 */     Span currentSpan = this.optimisedSuppressionSpans.get(this.currentSpanIndex);
/*  87 */     if (max < currentSpan.min) return max; 
/*  88 */     if (currentSpan.includes(max)) return currentSpan.min; 
/*  89 */     if (min < currentSpan.min && max >= currentSpan.max) return currentSpan.min;
/*     */     
/*  91 */     return max;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  95 */     ArrayDeque<Span> spanPool = SPAN_POOL.get();
/*  96 */     for (int i = this.optimisedSuppressionSpans.size() - 1; i >= 0; i--) {
/*  97 */       Span span = this.optimisedSuppressionSpans.remove(i);
/*  98 */       span.reset();
/*  99 */       spanPool.push(span);
/*     */     } 
/* 101 */     this.currentSpanIndex = 0;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static Span allocateSpan(@Nonnull ArrayDeque<Span> spanPool) {
/* 106 */     if (spanPool.isEmpty()) return new Span(); 
/* 107 */     return spanPool.pop();
/*     */   }
/*     */   
/*     */   private static class Span {
/* 111 */     private int min = -1;
/* 112 */     private int max = -1;
/*     */     
/*     */     public void init(int min, int max) {
/* 115 */       this.min = min;
/* 116 */       this.max = max;
/*     */     }
/*     */     
/*     */     public void expandTo(int max) {
/* 120 */       this.max = max;
/*     */     }
/*     */     
/*     */     public boolean includes(int value) {
/* 124 */       return (value >= this.min && value <= this.max);
/*     */     }
/*     */     
/*     */     public void reset() {
/* 128 */       this.min = this.max = -1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\suppression\SuppressionSpanHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */