/*    */ package com.hypixel.hytale.metrics.metric;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Metric
/*    */ {
/*    */   public static final Codec<Metric> CODEC;
/*    */   private long min;
/*    */   
/*    */   static {
/* 31 */     CODEC = (Codec<Metric>)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Metric.class, Metric::new).append(new KeyedCodec("Min", (Codec)Codec.LONG), (metric, s) -> metric.min = s.longValue(), metric -> Long.valueOf(metric.min)).add()).append(new KeyedCodec("Average", (Codec)Codec.DOUBLE), (metric, s) -> metric.average.set(s.doubleValue()), metric -> Double.valueOf(metric.average.get())).add()).append(new KeyedCodec("Max", (Codec)Codec.LONG), (metric, s) -> metric.max = s.longValue(), metric -> Long.valueOf(metric.max)).add()).build();
/*    */   }
/*    */   
/* 34 */   private final AverageCollector average = new AverageCollector();
/*    */   private long max;
/*    */   
/*    */   public Metric() {
/* 38 */     clear();
/*    */   }
/*    */   
/*    */   public void add(long value) {
/* 42 */     if (value < this.min) this.min = value; 
/* 43 */     this.average.add(value);
/* 44 */     if (value > this.max) this.max = value; 
/*    */   }
/*    */   
/*    */   public void remove(long value) {
/* 48 */     this.average.remove(value);
/*    */   }
/*    */   
/*    */   public long getMin() {
/* 52 */     return this.min;
/*    */   }
/*    */   
/*    */   public double getAverage() {
/* 56 */     return this.average.get();
/*    */   }
/*    */   
/*    */   public long getMax() {
/* 60 */     return this.max;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 64 */     this.min = Long.MAX_VALUE;
/* 65 */     this.average.clear();
/* 66 */     this.max = Long.MIN_VALUE;
/*    */   }
/*    */   
/*    */   public void resetMinMax() {
/* 70 */     this.min = Long.MAX_VALUE;
/* 71 */     this.max = Long.MIN_VALUE;
/*    */   }
/*    */   
/*    */   public void calculateMinMax(long value) {
/* 75 */     if (value < this.min) this.min = value; 
/* 76 */     if (value > this.max) this.max = value; 
/*    */   }
/*    */   
/*    */   public void addToAverage(long value) {
/* 80 */     this.average.add(value);
/*    */   }
/*    */   
/*    */   public void set(@Nonnull Metric metric) {
/* 84 */     this.min = metric.min;
/* 85 */     this.average.set(metric.average.get());
/* 86 */     this.max = metric.max;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 92 */     return "Metric{min=" + this.min + ", average=" + this.average
/*    */       
/* 94 */       .get() + ", max=" + this.max + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\metrics\metric\Metric.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */