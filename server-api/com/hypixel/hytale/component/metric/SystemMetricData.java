/*     */ package com.hypixel.hytale.component.metric;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.metrics.MetricResults;
/*     */ import com.hypixel.hytale.metrics.metric.HistoricMetric;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SystemMetricData
/*     */ {
/*     */   @Nonnull
/*     */   public static final Codec<SystemMetricData> CODEC;
/*     */   private String name;
/*     */   private int archetypeChunkCount;
/*     */   private int entityCount;
/*     */   @Nullable
/*     */   private HistoricMetric historicMetric;
/*     */   private MetricResults metrics;
/*     */   
/*     */   static {
/*  47 */     CODEC = (Codec<SystemMetricData>)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SystemMetricData.class, SystemMetricData::new).append(new KeyedCodec("Name", (Codec)Codec.STRING), (systemMetricData, o) -> systemMetricData.name = o, systemMetricData -> systemMetricData.name).add()).append(new KeyedCodec("ArchetypeChunkCount", (Codec)Codec.INTEGER), (systemMetricData, o) -> systemMetricData.archetypeChunkCount = o.intValue(), systemMetricData -> Integer.valueOf(systemMetricData.archetypeChunkCount)).add()).append(new KeyedCodec("EntityCount", (Codec)Codec.INTEGER), (systemMetricData, o) -> systemMetricData.entityCount = o.intValue(), systemMetricData -> Integer.valueOf(systemMetricData.entityCount)).add()).append(new KeyedCodec("HistoricMetric", HistoricMetric.METRICS_CODEC), (systemMetricData, o) -> systemMetricData.historicMetric = o, systemMetricData -> systemMetricData.historicMetric).add()).append(new KeyedCodec("Metrics", MetricResults.CODEC), (systemMetricData, o) -> systemMetricData.metrics = o, systemMetricData -> systemMetricData.metrics).add()).build();
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
/*     */   public SystemMetricData() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SystemMetricData(@Nonnull String name, int archetypeChunkCount, int entityCount, @Nullable HistoricMetric historicMetric, @Nonnull MetricResults metrics) {
/*  97 */     this.name = name;
/*  98 */     this.archetypeChunkCount = archetypeChunkCount;
/*  99 */     this.entityCount = entityCount;
/* 100 */     this.historicMetric = historicMetric;
/* 101 */     this.metrics = metrics;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\metric\SystemMetricData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */