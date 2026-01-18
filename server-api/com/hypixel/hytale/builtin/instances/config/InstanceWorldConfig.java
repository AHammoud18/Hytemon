/*     */ package com.hypixel.hytale.builtin.instances.config;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.instances.removal.RemovalCondition;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InstanceWorldConfig
/*     */ {
/*     */   public static final String ID = "Instance";
/*     */   @Nonnull
/*     */   public static final BuilderCodec<InstanceWorldConfig> CODEC;
/*     */   
/*     */   static {
/*  63 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(InstanceWorldConfig.class, InstanceWorldConfig::new).append(new KeyedCodec("RemovalConditions", (Codec)new ArrayCodec((Codec)RemovalCondition.CODEC, x$0 -> new RemovalCondition[x$0])), (o, i) -> o.removalConditions = i, o -> o.removalConditions).documentation("The set of conditions that have to be met to remove the world.\n\nIf no conditions are provided (e.g. a empty list) then the world will not be removed automatically").addValidator(Validators.nonNull()).add()).append(new KeyedCodec("ReturnPoint", (Codec)WorldReturnPoint.CODEC), (o, i) -> o.returnPoint = i, o -> o.returnPoint).documentation("The location to send players to when leaving this world.").add()).append(new KeyedCodec("PreventReconnection", (Codec)Codec.BOOLEAN), (o, i) -> o.preventReconnection = i.booleanValue(), o -> Boolean.valueOf(o.preventReconnection)).documentation("Whether to prevent reconnecting into this world.\n\nPlayers that reconnect back into the world will forced out of the world to the return point (or their own).").add()).append(new KeyedCodec("Discovery", (Codec)InstanceDiscoveryConfig.CODEC), (o, i) -> o.discovery = i, o -> o.discovery).documentation("Optional discovery configuration for displaying an event title when a player enters this instance for the first time.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static InstanceWorldConfig get(@Nonnull WorldConfig config) {
/*  73 */     return (InstanceWorldConfig)config.getPluginConfig().get(InstanceWorldConfig.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static InstanceWorldConfig ensureAndGet(@Nonnull WorldConfig config) {
/*  84 */     return (InstanceWorldConfig)config.getPluginConfig().computeIfAbsent(InstanceWorldConfig.class, v -> new InstanceWorldConfig());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  90 */   private RemovalCondition[] removalConditions = RemovalCondition.EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private WorldReturnPoint returnPoint;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean preventReconnection = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private InstanceDiscoveryConfig discovery;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldPreventReconnection() {
/* 114 */     return this.preventReconnection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public RemovalCondition[] getRemovalConditions() {
/* 122 */     return this.removalConditions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRemovalConditions(@Nonnull RemovalCondition... removalConditions) {
/* 131 */     this.removalConditions = removalConditions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WorldReturnPoint getReturnPoint() {
/* 139 */     return this.returnPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReturnPoint(@Nullable WorldReturnPoint returnPoint) {
/* 148 */     this.returnPoint = returnPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public InstanceDiscoveryConfig getDiscovery() {
/* 156 */     return this.discovery;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDiscovery(@Nullable InstanceDiscoveryConfig discovery) {
/* 165 */     this.discovery = discovery;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\config\InstanceWorldConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */