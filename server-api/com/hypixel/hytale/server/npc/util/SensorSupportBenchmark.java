/*     */ package com.hypixel.hytale.server.npc.util;
/*     */ 
/*     */ import com.hypixel.hytale.common.benchmark.ContinuousValueRecorder;
/*     */ import com.hypixel.hytale.common.benchmark.DiscreteValueRecorder;
/*     */ import com.hypixel.hytale.common.benchmark.TimeRecorder;
/*     */ import com.hypixel.hytale.common.util.FormatUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import java.util.Formatter;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SensorSupportBenchmark
/*     */ {
/*     */   public static final char DEFAULT_COLUMN_SEPARATOR = '|';
/*     */   public static final String DEFAULT_COLUMN_FORMAT_HEADER = "|%-6.6s";
/*     */   public static final String DEFAULT_COLUMN_FORMAT_VALUE = "|%6.6s";
/*  21 */   public static final String[] DEFAULT_COLUMNS_UPDATE = new String[] { "KIND", "COUNT", "G-AVG", "G-MIN", "G-MAX", "L-AVG", "L-MAX", "SD-MAX", "UD-MAX", "AD-MAX" };
/*  22 */   public static final String[] DEFAULT_COLUMNS_LOS = new String[] { "L-CNT", "L-AVG", "L-MAX", "L-HIT%", "T-CNT", "T-AVG", "T-MIN", "T-MAX", "I-CNT", "I-AVG", "I-MAX", "I-HIT%", "F-CNT", "F-AVG", "F-MAX", "F-HIT%" };
/*     */   
/*     */   @Nonnull
/*  25 */   protected TimeRecorder playerGetTime = new TimeRecorder();
/*     */   @Nonnull
/*  27 */   protected DiscreteValueRecorder playerDistance = new DiscreteValueRecorder();
/*     */   @Nonnull
/*  29 */   protected DiscreteValueRecorder playerDistanceSorted = new DiscreteValueRecorder();
/*     */   @Nonnull
/*  31 */   protected DiscreteValueRecorder playerDistanceAvoidance = new DiscreteValueRecorder();
/*     */   @Nonnull
/*  33 */   protected DiscreteValueRecorder playerCount = new DiscreteValueRecorder();
/*     */   @Nonnull
/*  35 */   protected TimeRecorder entityGetTime = new TimeRecorder();
/*     */   @Nonnull
/*  37 */   protected DiscreteValueRecorder entityDistance = new DiscreteValueRecorder();
/*     */   @Nonnull
/*  39 */   protected DiscreteValueRecorder entityDistanceSorted = new DiscreteValueRecorder();
/*     */   @Nonnull
/*  41 */   protected DiscreteValueRecorder entityDistanceAvoidance = new DiscreteValueRecorder();
/*     */   @Nonnull
/*  43 */   protected DiscreteValueRecorder entityCount = new DiscreteValueRecorder();
/*     */   @Nonnull
/*  45 */   protected DiscreteValueRecorder losTest = new DiscreteValueRecorder();
/*     */   @Nonnull
/*  47 */   protected ContinuousValueRecorder losCacheHit = new ContinuousValueRecorder();
/*     */   @Nonnull
/*  49 */   protected DiscreteValueRecorder inverseLosTest = new DiscreteValueRecorder();
/*     */   @Nonnull
/*  51 */   protected ContinuousValueRecorder inverseLosCacheHit = new ContinuousValueRecorder();
/*     */   @Nonnull
/*  53 */   protected DiscreteValueRecorder friendlyBlockingTest = new DiscreteValueRecorder();
/*     */   @Nonnull
/*  55 */   protected ContinuousValueRecorder friendlyBlockingCacheHit = new ContinuousValueRecorder();
/*     */   @Nonnull
/*  57 */   protected TimeRecorder losTestTime = new TimeRecorder();
/*     */   
/*     */   protected long losTestTick;
/*     */   
/*     */   protected long losCacheHitTick;
/*     */   
/*     */   protected long inverseLosTestTick;
/*     */   protected long inverseLosCacheHitTick;
/*     */   protected long friendlyBlockingTestTick;
/*     */   protected long friendlyBlockingCacheHitTick;
/*     */   
/*     */   public void collectPlayerList(long getNanos, double maxPlayerDistanceSorted, double maxPlayerDistance, double maxPlayerDistanceAvoidance, int numPlayers) {
/*  69 */     this.playerGetTime.recordNanos(getNanos);
/*  70 */     this.playerDistance.record(MathUtil.fastCeil(maxPlayerDistance));
/*  71 */     this.playerDistanceSorted.record(MathUtil.fastCeil(maxPlayerDistanceSorted));
/*  72 */     this.playerDistanceAvoidance.record(MathUtil.fastCeil(maxPlayerDistanceAvoidance));
/*  73 */     this.playerCount.record(numPlayers);
/*     */   }
/*     */   
/*     */   public void collectEntityList(long getNanos, double maxEntityDistanceSorted, double maxEntityDistance, double maxEntityDistanceAvoidance, int numEntities) {
/*  77 */     this.entityGetTime.recordNanos(getNanos);
/*  78 */     this.entityDistance.record(MathUtil.fastCeil(maxEntityDistance));
/*  79 */     this.entityDistanceSorted.record(MathUtil.fastCeil(maxEntityDistanceSorted));
/*  80 */     this.entityDistanceAvoidance.record(MathUtil.fastCeil(maxEntityDistanceAvoidance));
/*  81 */     this.entityCount.record(numEntities);
/*     */   }
/*     */   
/*     */   public void collectLosTest(boolean cacheHit, long time) {
/*  85 */     this.losTestTick++;
/*  86 */     if (cacheHit) {
/*  87 */       this.losCacheHitTick++;
/*     */     } else {
/*  89 */       this.losTestTime.recordNanos(time);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void collectInverseLosTest(boolean cacheHit) {
/*  94 */     this.inverseLosTestTick++;
/*  95 */     if (cacheHit) this.inverseLosCacheHitTick++; 
/*     */   }
/*     */   
/*     */   public void collectFriendlyBlockingTest(boolean cacheHit) {
/*  99 */     this.friendlyBlockingTestTick++;
/* 100 */     if (cacheHit) this.friendlyBlockingCacheHitTick++; 
/*     */   }
/*     */   
/*     */   public void tickDone() {
/* 104 */     this.losTest.record(this.losTestTick);
/* 105 */     if (this.losTestTick > 0L) this.losCacheHit.record(this.losCacheHitTick / this.losTestTick); 
/* 106 */     this.losTestTick = 0L;
/* 107 */     this.losCacheHitTick = 0L;
/*     */     
/* 109 */     this.inverseLosTest.record(this.inverseLosTestTick);
/* 110 */     if (this.inverseLosTestTick > 0L) this.inverseLosCacheHit.record(this.inverseLosCacheHitTick / this.inverseLosTestTick); 
/* 111 */     this.inverseLosTestTick = 0L;
/* 112 */     this.inverseLosCacheHitTick = 0L;
/*     */     
/* 114 */     this.friendlyBlockingTest.record(this.friendlyBlockingTestTick);
/* 115 */     if (this.friendlyBlockingTestTick > 0L)
/* 116 */       this.friendlyBlockingCacheHit.record(this.friendlyBlockingCacheHitTick / this.friendlyBlockingTestTick); 
/* 117 */     this.friendlyBlockingTestTick = 0L;
/* 118 */     this.friendlyBlockingCacheHitTick = 0L;
/*     */   }
/*     */   
/*     */   public void formatHeaderUpdateTimes(@Nonnull Formatter formatter) {
/* 122 */     FormatUtil.formatArray(formatter, "|%-6.6s", (Object[])DEFAULT_COLUMNS_UPDATE);
/*     */   }
/*     */   
/*     */   public void formatValuesUpdateTimePlayer(@Nonnull Formatter formatter) {
/* 126 */     formatValuesUpdateTime(formatter, "Player", this.playerGetTime, this.playerCount, this.playerDistanceSorted, this.playerDistance, this.playerDistanceAvoidance);
/*     */   }
/*     */   
/*     */   public void formatValuesUpdateTimeEntity(@Nonnull Formatter formatter) {
/* 130 */     formatValuesUpdateTime(formatter, "Entity", this.entityGetTime, this.entityCount, this.entityDistanceSorted, this.entityDistance, this.entityDistanceAvoidance);
/*     */   }
/*     */   
/*     */   public void formatValuesUpdateTime(@Nonnull Formatter formatter, String kind, @Nonnull TimeRecorder getTime, @Nonnull DiscreteValueRecorder count, @Nonnull DiscreteValueRecorder distanceSorted, @Nonnull DiscreteValueRecorder distance, @Nonnull DiscreteValueRecorder distanceAvoidance) {
/* 134 */     long distanceMaxValue = distance.getMaxValue();
/* 135 */     long distanceMinValue = distance.getMinValue();
/* 136 */     long distanceMaxValueSorted = distanceSorted.getMaxValue();
/* 137 */     long distanceMinValueSorted = distanceSorted.getMinValue();
/* 138 */     long distanceMaxValueAvoidance = distanceAvoidance.getMaxValue();
/* 139 */     long distanceMinValueAvoidance = distanceAvoidance.getMinValue();
/*     */     
/* 141 */     formatter.format("|%-6.6s", new Object[] { kind });
/* 142 */     FormatUtil.formatArgs(formatter, "|%6.6s", new Object[] {
/* 143 */           Long.valueOf(getTime.getCount()), 
/* 144 */           TimeRecorder.formatTime(getTime.getAverage()), TimeRecorder.formatTime(getTime.getMinValue()), TimeRecorder.formatTime(getTime.getMaxValue()), 
/* 145 */           Long.valueOf(count.getAverage()), Long.valueOf(count.getMaxValue()), 
/* 146 */           (distanceMaxValueSorted == distanceMinValueSorted) ? Long.valueOf(distanceMaxValueSorted) : ("" + distanceMinValueSorted + "-" + distanceMinValueSorted), 
/* 147 */           (distanceMaxValue == distanceMinValue) ? Long.valueOf(distanceMaxValue) : ("" + distanceMinValue + "-" + distanceMinValue), 
/* 148 */           (distanceMaxValueAvoidance == distanceMinValueAvoidance) ? Long.valueOf(distanceMaxValueAvoidance) : ("" + distanceMinValueAvoidance + "-" + distanceMinValueAvoidance)
/*     */         });
/*     */   }
/*     */   
/*     */   public boolean haveUpdateTimes() {
/* 153 */     return (this.playerGetTime.getCount() > 0L || this.entityGetTime.getCount() > 0L);
/*     */   }
/*     */   
/*     */   public void formatHeaderLoS(@Nonnull Formatter formatter) {
/* 157 */     FormatUtil.formatArray(formatter, "|%-6.6s", (Object[])DEFAULT_COLUMNS_LOS);
/*     */   }
/*     */   
/*     */   public boolean formatValuesLoS(@Nonnull Formatter formatter) {
/* 161 */     if (this.losTest.getMaxValue() == 0L && this.inverseLosTest.getMaxValue() == 0L && this.friendlyBlockingTest.getMaxValue() == 0L) {
/* 162 */       return false;
/*     */     }
/*     */     
/* 165 */     FormatUtil.formatArgs(formatter, "|%6.6s", new Object[] { 
/* 166 */           Long.valueOf(this.losTest.getCount()), Long.valueOf(this.losTest.getAverage()), Long.valueOf(this.losTest.getMaxValue()), Integer.valueOf((int)(this.losCacheHit.getAverage() * 100.0D)), 
/* 167 */           Long.valueOf(this.losTestTime.getCount()), TimeRecorder.formatTime(this.losTestTime.getAverage()), TimeRecorder.formatTime(this.losTestTime.getMinValue()), TimeRecorder.formatTime(this.losTestTime.getMaxValue()), 
/* 168 */           Long.valueOf(this.inverseLosTest.getCount()), Long.valueOf(this.inverseLosTest.getAverage()), Long.valueOf(this.inverseLosTest.getMaxValue()), Integer.valueOf((int)(this.inverseLosCacheHit.getAverage() * 100.0D)), 
/* 169 */           Long.valueOf(this.friendlyBlockingTest.getCount()), Long.valueOf(this.friendlyBlockingTest.getAverage()), Long.valueOf(this.friendlyBlockingTest.getMaxValue()), Integer.valueOf((int)(this.friendlyBlockingCacheHit.getAverage() * 100.0D)) });
/* 170 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\SensorSupportBenchmark.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */