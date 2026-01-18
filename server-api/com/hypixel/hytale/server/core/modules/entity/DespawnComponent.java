/*     */ package com.hypixel.hytale.server.core.modules.entity;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class DespawnComponent implements Component<EntityStore> {
/*     */   public static final BuilderCodec<DespawnComponent> CODEC;
/*     */   @Nullable
/*     */   private Instant timeToDespawnAt;
/*     */   
/*     */   public static ComponentType<EntityStore, DespawnComponent> getComponentType() {
/*  24 */     return EntityModule.get().getDespawnComponentType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  33 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(DespawnComponent.class, DespawnComponent::new).append(new KeyedCodec("Despawn", (Codec)Codec.INSTANT), (despawnComponent, instant) -> despawnComponent.timeToDespawnAt = instant, despawnComponent -> despawnComponent.timeToDespawnAt).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DespawnComponent() {
/*  42 */     this(null);
/*     */   }
/*     */   
/*     */   public DespawnComponent(@Nullable Instant timeToDespawnAt) {
/*  46 */     this.timeToDespawnAt = timeToDespawnAt;
/*     */   }
/*     */   
/*     */   public void setDespawn(Instant timeToDespawnAt) {
/*  50 */     this.timeToDespawnAt = timeToDespawnAt;
/*     */   }
/*     */   
/*     */   public void setDespawnTo(@Nonnull Instant from, float additionalSeconds) {
/*  54 */     this.timeToDespawnAt = from.plusNanos((long)(additionalSeconds * 1.0E9F));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Instant getDespawn() {
/*  59 */     return this.timeToDespawnAt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static DespawnComponent despawnInSeconds(@Nonnull TimeResource time, int seconds) {
/*  70 */     return new DespawnComponent(time.getNow().plus(Duration.ofSeconds(seconds)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static DespawnComponent despawnInSeconds(@Nonnull TimeResource time, float seconds) {
/*  81 */     return new DespawnComponent(time.getNow().plusNanos((long)(seconds * 1.0E9F)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static DespawnComponent despawnInMilliseconds(@Nonnull TimeResource time, long milliseconds) {
/*  92 */     return new DespawnComponent(time.getNow().plus(Duration.ofMillis(milliseconds)));
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
/*     */   public static void trySetDespawn(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull TimeResource timeResource, @Nonnull Ref<EntityStore> ref, @Nullable DespawnComponent despawnComponent, @Nullable Float newLifetime) {
/* 106 */     if (despawnComponent != null) {
/* 107 */       if (newLifetime != null) {
/* 108 */         despawnComponent.setDespawnTo(timeResource.getNow(), newLifetime.floatValue());
/*     */       } else {
/* 110 */         commandBuffer.removeComponent(ref, getComponentType());
/*     */       } 
/*     */     } else {
/* 113 */       commandBuffer.putComponent(ref, getComponentType(), despawnInSeconds(timeResource, newLifetime.floatValue()));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<EntityStore> clone() {
/* 120 */     return new DespawnComponent(this.timeToDespawnAt);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\DespawnComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */