/*     */ package com.hypixel.hytale.builtin.instances.removal;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.instances.InstancesPlugin;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import java.time.Instant;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class InstanceDataResource
/*     */   implements Resource<ChunkStore> {
/*     */   @Nonnull
/*     */   public static ResourceType<ChunkStore, InstanceDataResource> getResourceType() {
/*  18 */     return InstancesPlugin.get().getInstanceDataResourceType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<InstanceDataResource> CODEC;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isRemoving;
/*     */ 
/*     */ 
/*     */   
/*     */   private Instant timeoutTimer;
/*     */ 
/*     */   
/*     */   private Instant idleTimeoutTimer;
/*     */ 
/*     */   
/*     */   private boolean hadPlayer;
/*     */ 
/*     */   
/*     */   private Instant worldTimeoutTimer;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  46 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(InstanceDataResource.class, InstanceDataResource::new).append(new KeyedCodec("TimeoutTimer", (Codec)Codec.INSTANT), (o, i) -> o.timeoutTimer = i, o -> o.timeoutTimer).add()).append(new KeyedCodec("IdleTimeoutTimer", (Codec)Codec.INSTANT), (o, i) -> o.idleTimeoutTimer = i, o -> o.idleTimeoutTimer).add()).append(new KeyedCodec("HadPlayer", (Codec)Codec.BOOLEAN), (o, i) -> o.hadPlayer = i.booleanValue(), o -> Boolean.valueOf(o.hadPlayer)).add()).append(new KeyedCodec("WorldTimeoutTimer", (Codec)Codec.INSTANT), (o, i) -> o.worldTimeoutTimer = i, o -> o.worldTimeoutTimer).add()).build();
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
/*     */   public boolean isRemoving() {
/*  62 */     return this.isRemoving;
/*     */   }
/*     */   
/*     */   public void setRemoving(boolean removing) {
/*  66 */     this.isRemoving = removing;
/*     */   }
/*     */   
/*     */   public Instant getTimeoutTimer() {
/*  70 */     return this.timeoutTimer;
/*     */   }
/*     */   
/*     */   public void setTimeoutTimer(Instant timeoutTimer) {
/*  74 */     this.timeoutTimer = timeoutTimer;
/*     */   }
/*     */   
/*     */   public Instant getIdleTimeoutTimer() {
/*  78 */     return this.idleTimeoutTimer;
/*     */   }
/*     */   
/*     */   public void setIdleTimeoutTimer(Instant idleTimeoutTimer) {
/*  82 */     this.idleTimeoutTimer = idleTimeoutTimer;
/*     */   }
/*     */   
/*     */   public boolean hadPlayer() {
/*  86 */     return this.hadPlayer;
/*     */   }
/*     */   
/*     */   public void setHadPlayer(boolean hadPlayer) {
/*  90 */     this.hadPlayer = hadPlayer;
/*     */   }
/*     */   
/*     */   public Instant getWorldTimeoutTimer() {
/*  94 */     return this.worldTimeoutTimer;
/*     */   }
/*     */   
/*     */   public void setWorldTimeoutTimer(Instant worldTimeoutTimer) {
/*  98 */     this.worldTimeoutTimer = worldTimeoutTimer;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InstanceDataResource clone() {
/* 104 */     return new InstanceDataResource();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\removal\InstanceDataResource.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */