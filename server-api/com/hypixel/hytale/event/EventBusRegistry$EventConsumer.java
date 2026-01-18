/*     */ package com.hypixel.hytale.event;
/*     */ 
/*     */ import com.hypixel.hytale.metrics.metric.Metric;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EventConsumer
/*     */ {
/*     */   @Nonnull
/* 176 */   protected static final AtomicInteger consumerIndex = new AtomicInteger();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int index;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final short priority;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected final String consumerString;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 197 */   protected final Metric timer = new Metric();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EventConsumer(short priority, @Nonnull String consumerString) {
/* 207 */     this.priority = priority;
/* 208 */     this.consumerString = consumerString;
/* 209 */     this.index = consumerIndex.getAndIncrement();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/* 216 */     return this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getPriority() {
/* 223 */     return this.priority;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getConsumerString() {
/* 231 */     return this.consumerString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Metric getTimer() {
/* 239 */     return this.timer;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 245 */     return "EventConsumer{index=" + this.index + ", priority=" + this.priority + ", consumerString='" + this.consumerString + "', timer=" + String.valueOf(this.timer) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\event\EventBusRegistry$EventConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */