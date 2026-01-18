/*     */ package com.hypixel.hytale.server.core.modules.time;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
/*     */ import java.time.temporal.TemporalUnit;
/*     */ import java.util.function.Supplier;
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
/*     */ public class TimeResource
/*     */   implements Resource<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<TimeResource> CODEC;
/*     */   @Nonnull
/*     */   private Instant now;
/*     */   
/*     */   static {
/*  34 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(TimeResource.class, TimeResource::new).append(new KeyedCodec("Now", (Codec)Codec.INSTANT), (o, now) -> o.now = now, o -> o.now).documentation("Now. The current instant of time.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ResourceType<EntityStore, TimeResource> getResourceType() {
/*  41 */     return TimeModule.get().getTimeResourceType();
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
/*  54 */   private float timeDilationModifier = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimeResource() {
/*  62 */     this(Instant.EPOCH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimeResource(@Nonnull Instant now) {
/*  71 */     this.now = now;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getTimeDilationModifier() {
/*  78 */     return this.timeDilationModifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimeDilationModifier(float timeDilationModifier) {
/*  87 */     this.timeDilationModifier = timeDilationModifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Instant getNow() {
/*  95 */     return this.now;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNow(@Nonnull Instant now) {
/* 104 */     this.now = now;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(@Nonnull Duration duration) {
/* 113 */     this.now = this.now.plus(duration);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(long time, @Nonnull TemporalUnit unit) {
/* 123 */     this.now = this.now.plus(time, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Resource<EntityStore> clone() {
/* 129 */     return new TimeResource(this.now);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 135 */     return "TimeResource{now=" + String.valueOf(this.now) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\time\TimeResource.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */