/*     */ package com.hypixel.hytale.builtin.portals.integrations;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.instances.removal.InstanceDataResource;
/*     */ import com.hypixel.hytale.builtin.instances.removal.RemovalCondition;
/*     */ import com.hypixel.hytale.builtin.instances.removal.TimeoutCondition;
/*     */ import com.hypixel.hytale.builtin.instances.removal.WorldEmptyCondition;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PortalRemovalCondition
/*     */   implements RemovalCondition
/*     */ {
/*     */   public static final BuilderCodec<PortalRemovalCondition> CODEC;
/*     */   
/*     */   static {
/*  40 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PortalRemovalCondition.class, PortalRemovalCondition::new).documentation("A condition for temporary portal worlds.")).append(new KeyedCodec("TimeoutSeconds", (Codec)Codec.DOUBLE), (o, i) -> o.setTimeLimitSeconds(i.doubleValue()), o -> Double.valueOf(o.getTimeLimitSeconds())).documentation("How long the portal world will stay open (in seconds) after being joined.").add()).build();
/*     */   }
/*  42 */   private final WorldEmptyCondition worldEmptyCondition = new WorldEmptyCondition(90.0D);
/*     */   private TimeoutCondition timeLimitCondition;
/*     */   
/*     */   public PortalRemovalCondition() {
/*  46 */     this(60.0D);
/*     */   }
/*     */   
/*     */   public PortalRemovalCondition(double timeLimitSeconds) {
/*  50 */     this.timeLimitCondition = new TimeoutCondition(timeLimitSeconds);
/*     */   }
/*     */   
/*     */   private double getTimeLimitSeconds() {
/*  54 */     return this.timeLimitCondition.getTimeoutSeconds();
/*     */   }
/*     */   
/*     */   private void setTimeLimitSeconds(double timeLimitSeconds) {
/*  58 */     this.timeLimitCondition = new TimeoutCondition(timeLimitSeconds);
/*     */   }
/*     */   
/*     */   public double getElapsedSeconds(World world) {
/*  62 */     double timeLimitSeconds = this.timeLimitCondition.getTimeoutSeconds();
/*  63 */     double remainingSeconds = getRemainingSeconds(world);
/*  64 */     return Math.max(0.0D, timeLimitSeconds - remainingSeconds);
/*     */   }
/*     */   
/*     */   public double getRemainingSeconds(World world) {
/*  68 */     Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*  69 */     Store<EntityStore> entityStore = world.getEntityStore().getStore();
/*     */     
/*  71 */     InstanceDataResource instanceData = (InstanceDataResource)chunkStore.getResource(InstanceDataResource.getResourceType());
/*  72 */     TimeResource timeResource = (TimeResource)entityStore.getResource(TimeResource.getResourceType());
/*     */     
/*  74 */     Instant timeoutInstant = instanceData.getTimeoutTimer();
/*  75 */     if (timeoutInstant == null) {
/*  76 */       return this.timeLimitCondition.getTimeoutSeconds();
/*     */     }
/*     */     
/*  79 */     double remainingSeconds = Duration.between(timeResource.getNow(), timeoutInstant).toNanos() / 1.0E9D;
/*  80 */     return Math.max(0.0D, remainingSeconds);
/*     */   }
/*     */   
/*     */   public void setRemainingSeconds(World world, double seconds) {
/*  84 */     seconds = Math.max(0.0D, seconds);
/*     */     
/*  86 */     Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*  87 */     Store<EntityStore> entityStore = world.getEntityStore().getStore();
/*     */     
/*  89 */     InstanceDataResource instanceData = (InstanceDataResource)chunkStore.getResource(InstanceDataResource.getResourceType());
/*  90 */     TimeResource timeResource = (TimeResource)entityStore.getResource(TimeResource.getResourceType());
/*     */     
/*  92 */     instanceData.setTimeoutTimer(timeResource.getNow().plusMillis((long)(seconds * 1000.0D)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRemoveWorld(@Nonnull Store<ChunkStore> store) {
/*  97 */     InstanceDataResource instanceData = (InstanceDataResource)store.getResource(InstanceDataResource.getResourceType());
/*     */     
/*  99 */     if (instanceData.hadPlayer() && this.timeLimitCondition.shouldRemoveWorld(store)) {
/* 100 */       return true;
/*     */     }
/*     */     
/* 103 */     if (this.worldEmptyCondition.shouldRemoveWorld(store)) {
/* 104 */       return true;
/*     */     }
/*     */     
/* 107 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\integrations\PortalRemovalCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */