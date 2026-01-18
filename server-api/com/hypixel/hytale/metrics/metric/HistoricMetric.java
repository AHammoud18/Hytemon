/*     */ package com.hypixel.hytale.metrics.metric;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import it.unimi.dsi.fastutil.longs.LongArrayList;
/*     */ import it.unimi.dsi.fastutil.longs.LongList;
/*     */ import java.util.Arrays;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class HistoricMetric {
/*  15 */   public static final HistoricMetric[] EMPTY_ARRAY = new HistoricMetric[0];
/*     */   
/*     */   public static final Codec<HistoricMetric> METRICS_CODEC;
/*     */   
/*     */   private final long[] periodsNanos;
/*     */   
/*     */   @Nonnull
/*     */   private final AverageCollector[] periodAverages;
/*     */   
/*     */   @Nonnull
/*     */   private final int[] startIndices;
/*     */   
/*     */   private final int bufferSize;
/*     */   
/*     */   @Nonnull
/*     */   private final long[] timestamps;
/*     */   
/*     */   @Nonnull
/*     */   private final long[] values;
/*     */   
/*     */   int nextIndex;
/*     */ 
/*     */   
/*     */   static {
/*  39 */     METRICS_CODEC = (Codec<HistoricMetric>)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(HistoricMetric.class, HistoricMetric::new).append(new KeyedCodec("PeriodsNanos", (Codec)Codec.LONG_ARRAY), (historicMetric, s) -> { throw new UnsupportedOperationException("Not supported"); }historicMetric -> historicMetric.periodsNanos).add()).append(new KeyedCodec("Timestamps", (Codec)Codec.LONG_ARRAY), (historicMetric, s) -> { throw new UnsupportedOperationException("Not supported"); }HistoricMetric::getAllTimestamps).add()).append(new KeyedCodec("Values", (Codec)Codec.LONG_ARRAY), (historicMetric, s) -> { throw new UnsupportedOperationException("Not supported"); }HistoricMetric::getAllValues).add()).build();
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
/*     */   private HistoricMetric() {
/*  55 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */   
/*     */   private HistoricMetric(@Nonnull Builder builder) {
/*  59 */     this.periodsNanos = builder.periods.toLongArray();
/*     */     
/*  61 */     this.periodAverages = new AverageCollector[this.periodsNanos.length];
/*  62 */     for (int i = 0; i < this.periodAverages.length; ) { this.periodAverages[i] = new AverageCollector(); i++; }
/*  63 */      this.startIndices = new int[this.periodsNanos.length];
/*     */     
/*  65 */     long longestPeriod = 0L;
/*  66 */     for (long period : this.periodsNanos) {
/*  67 */       if (period > longestPeriod) longestPeriod = period;
/*     */     
/*     */     } 
/*     */     
/*  71 */     this.bufferSize = (int)MathUtil.fastCeil(longestPeriod / builder.minimumInterval);
/*  72 */     this.timestamps = new long[this.bufferSize];
/*  73 */     this.values = new long[this.bufferSize];
/*     */     
/*  75 */     Arrays.fill(this.timestamps, Long.MAX_VALUE);
/*     */   }
/*     */   
/*     */   public long[] getPeriodsNanos() {
/*  79 */     return this.periodsNanos;
/*     */   }
/*     */   
/*     */   public long calculateMin(int periodIndex) {
/*  83 */     int bufferSize = this.bufferSize;
/*  84 */     long[] values = this.values;
/*  85 */     int start = this.startIndices[periodIndex];
/*  86 */     int nextIndex = this.nextIndex;
/*     */     
/*  88 */     long min = Long.MAX_VALUE;
/*  89 */     if (start < nextIndex) {
/*  90 */       for (int i = start; i < nextIndex; i++) {
/*  91 */         long value = values[i];
/*  92 */         if (value < min) min = value; 
/*     */       } 
/*     */     } else {
/*  95 */       int i; for (i = start; i < bufferSize; i++) {
/*  96 */         long value = values[i];
/*  97 */         if (value < min) min = value; 
/*     */       } 
/*  99 */       for (i = 0; i < nextIndex; i++) {
/* 100 */         long value = values[i];
/* 101 */         if (value < min) min = value; 
/*     */       } 
/*     */     } 
/* 104 */     return min;
/*     */   }
/*     */   
/*     */   public double getAverage(int periodIndex) {
/* 108 */     return this.periodAverages[periodIndex].get();
/*     */   }
/*     */   
/*     */   public long calculateMax(int periodIndex) {
/* 112 */     int bufferSize = this.bufferSize;
/* 113 */     long[] values = this.values;
/* 114 */     int start = this.startIndices[periodIndex];
/* 115 */     int nextIndex = this.nextIndex;
/*     */     
/* 117 */     long max = Long.MIN_VALUE;
/* 118 */     if (start < nextIndex) {
/* 119 */       for (int i = start; i < nextIndex; i++) {
/* 120 */         long value = values[i];
/* 121 */         if (value > max) max = value; 
/*     */       } 
/*     */     } else {
/* 124 */       int i; for (i = start; i < bufferSize; i++) {
/* 125 */         long value = values[i];
/* 126 */         if (value > max) max = value; 
/*     */       } 
/* 128 */       for (i = 0; i < nextIndex; i++) {
/* 129 */         long value = values[i];
/* 130 */         if (value > max) max = value; 
/*     */       } 
/*     */     } 
/* 133 */     return max;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 137 */     for (AverageCollector average : this.periodAverages) average.clear(); 
/* 138 */     Arrays.fill(this.startIndices, 0);
/* 139 */     Arrays.fill(this.timestamps, Long.MAX_VALUE);
/* 140 */     Arrays.fill(this.values, 0L);
/* 141 */     this.nextIndex = 0;
/*     */   }
/*     */   
/*     */   public void add(long timestampNanos, long value) {
/* 145 */     long[] periodsNanos = this.periodsNanos;
/* 146 */     AverageCollector[] periodAverages = this.periodAverages;
/* 147 */     int[] startIndices = this.startIndices;
/* 148 */     int bufferSize = this.bufferSize;
/* 149 */     long[] timestamps = this.timestamps;
/* 150 */     long[] values = this.values;
/* 151 */     int nextIndex = this.nextIndex;
/*     */     
/* 153 */     int periodLength = periodsNanos.length;
/* 154 */     for (int i = 0; i < periodLength; i++) {
/* 155 */       long oldestPossibleTimestamp = timestampNanos - periodsNanos[i];
/* 156 */       AverageCollector average = periodAverages[i];
/*     */       
/* 158 */       int start = startIndices[i];
/* 159 */       while (timestamps[start] < oldestPossibleTimestamp) {
/* 160 */         long oldValue = values[start];
/* 161 */         average.remove(oldValue);
/*     */         
/* 163 */         start = (start + 1) % bufferSize;
/* 164 */         if (start == nextIndex)
/*     */           break; 
/* 166 */       }  startIndices[i] = start;
/*     */       
/* 168 */       average.add(value);
/*     */     } 
/*     */     
/* 171 */     timestamps[nextIndex] = timestampNanos;
/* 172 */     values[nextIndex] = value;
/* 173 */     this.nextIndex = (nextIndex + 1) % bufferSize;
/*     */   }
/*     */   
/*     */   public long[] getTimestamps(int periodIndex) {
/* 177 */     int start = this.startIndices[periodIndex];
/* 178 */     long[] timestamps = this.timestamps;
/* 179 */     int nextIndex = this.nextIndex;
/*     */     
/* 181 */     if (start < nextIndex) {
/* 182 */       return Arrays.copyOfRange(timestamps, start, nextIndex);
/*     */     }
/* 184 */     int length = timestamps.length - start;
/* 185 */     long[] data = new long[length + nextIndex];
/* 186 */     System.arraycopy(timestamps, start, data, 0, length);
/* 187 */     System.arraycopy(timestamps, 0, data, length, nextIndex);
/* 188 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   public long[] getValues(int periodIndex) {
/* 193 */     int start = this.startIndices[periodIndex];
/* 194 */     long[] values = this.values;
/* 195 */     int nextIndex = this.nextIndex;
/*     */     
/* 197 */     if (start < nextIndex) {
/* 198 */       return Arrays.copyOfRange(values, start, nextIndex);
/*     */     }
/* 200 */     int length = this.bufferSize - start;
/* 201 */     long[] data = new long[length + nextIndex];
/* 202 */     System.arraycopy(values, start, data, 0, length);
/* 203 */     System.arraycopy(values, 0, data, length, nextIndex);
/* 204 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   public long[] getAllTimestamps() {
/* 209 */     return getTimestamps(this.periodsNanos.length - 1);
/*     */   }
/*     */   
/*     */   public long[] getAllValues() {
/* 213 */     return getValues(this.periodsNanos.length - 1);
/*     */   }
/*     */   
/*     */   public void setAllTimestamps(@Nonnull long[] timestamps) {
/* 217 */     int length = timestamps.length;
/* 218 */     System.arraycopy(timestamps, 0, this.timestamps, 0, length);
/*     */     
/* 220 */     int periodIndex = 0;
/*     */ 
/*     */ 
/*     */     
/* 224 */     long last = timestamps[length - 1];
/* 225 */     for (int i = length - 2; i >= 0; i--) {
/* 226 */       if (last - timestamps[i] >= this.periodsNanos[periodIndex]) {
/*     */         
/* 228 */         this.startIndices[periodIndex] = i + 1;
/* 229 */         periodIndex++;
/*     */         
/* 231 */         if (periodIndex >= this.periodsNanos.length)
/*     */           break; 
/*     */       } 
/* 234 */     }  if (periodIndex < this.periodsNanos.length) {
/* 235 */       for (; periodIndex < this.periodsNanos.length; periodIndex++) {
/* 236 */         this.periodsNanos[periodIndex] = 0L;
/*     */       }
/*     */     }
/*     */     
/* 240 */     this.nextIndex = length;
/*     */   }
/*     */   
/*     */   public void setAllValues(@Nonnull long[] values) {
/* 244 */     System.arraycopy(values, 0, this.values, 0, values.length);
/*     */   }
/*     */   
/*     */   public long getLastValue() {
/* 248 */     if (this.nextIndex == 0) return this.values[this.bufferSize - 1]; 
/* 249 */     return this.values[this.nextIndex - 1];
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Builder builder(long minimumInterval, @Nonnull TimeUnit unit) {
/* 254 */     return new Builder(minimumInterval, unit);
/*     */   }
/*     */   
/*     */   public static class Builder {
/*     */     private final long minimumInterval;
/* 259 */     private final LongList periods = (LongList)new LongArrayList();
/*     */     
/*     */     private Builder(long minimumInterval, @Nonnull TimeUnit unit) {
/* 262 */       this.minimumInterval = unit.toNanos(minimumInterval);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Builder addPeriod(long period, @Nonnull TimeUnit unit) {
/* 267 */       long nanos = unit.toNanos(period);
/* 268 */       for (int i = 0; i < this.periods.size(); i++) {
/* 269 */         if (this.periods.getLong(i) > nanos) throw new IllegalArgumentException("Period's must be increasing in length"); 
/*     */       } 
/* 271 */       this.periods.add(nanos);
/* 272 */       return this;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public HistoricMetric build() {
/* 277 */       return new HistoricMetric(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\metrics\metric\HistoricMetric.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */