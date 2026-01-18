/*     */ package com.hypixel.hytale.event;
/*     */ 
/*     */ import java.util.function.Consumer;
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
/*     */ public class SyncEventConsumer<EventType extends IEvent>
/*     */   extends EventBusRegistry.EventConsumer
/*     */ {
/*     */   @Nonnull
/*     */   private final Consumer<EventType> consumer;
/*     */   @Nonnull
/*     */   private final Consumer<EventType> timedConsumer;
/*     */   
/*     */   public SyncEventConsumer(short priority, @Nonnull Consumer<EventType> consumer) {
/* 133 */     super(priority, consumer.toString());
/* 134 */     this.consumer = consumer;
/* 135 */     this.timedConsumer = (t -> {
/*     */         long before = System.nanoTime();
/*     */         consumer.accept(t);
/*     */         long after = System.nanoTime();
/*     */         this.timer.add(after - before);
/*     */       });
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected Consumer<EventType> getConsumer() {
/* 145 */     return this.consumer;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Consumer<EventType> getTimedConsumer() {
/* 150 */     return this.timedConsumer;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 156 */     return "SyncEventConsumer{consumer=" + String.valueOf(this.consumer) + ", timedConsumer=" + String.valueOf(this.timedConsumer) + "} " + super
/*     */ 
/*     */       
/* 159 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\event\SyncEventBusRegistry$SyncEventConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */