/*     */ package com.hypixel.hytale.server.core.universe;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.logger.sentry.SkipSentryException;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.metrics.MetricProvider;
/*     */ import com.hypixel.hytale.metrics.MetricResults;
/*     */ import com.hypixel.hytale.metrics.MetricsRegistry;
/*     */ import com.hypixel.hytale.protocol.HostAddress;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketStatsRecorder;
/*     */ import com.hypixel.hytale.protocol.packets.auth.ClientReferral;
/*     */ import com.hypixel.hytale.protocol.packets.connection.PongType;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.ChatType;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.ServerMessage;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.CameraManager;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.HiddenPlayersManager;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
/*     */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.io.PacketStatsRecorderImpl;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.ChunkTracker;
/*     */ import com.hypixel.hytale.server.core.receiver.IMessageReceiver;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.Function;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class PlayerRef
/*     */   implements Component<EntityStore>, MetricProvider, IMessageReceiver
/*     */ {
/*     */   @Nonnull
/*     */   public static final MetricsRegistry<PlayerRef> METRICS_REGISTRY;
/*     */   @Nonnull
/*     */   public static final MetricsRegistry<PlayerRef> COMPONENT_METRICS_REGISTRY;
/*     */   
/*     */   @Nonnull
/*     */   public static ComponentType<EntityStore, PlayerRef> getComponentType() {
/*  57 */     return Universe.get().getPlayerRefComponentType();
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
/*     */   static {
/*  82 */     METRICS_REGISTRY = (new MetricsRegistry()).register("Username", PlayerRef::getUsername, (Codec)Codec.STRING).register("Language", PlayerRef::getLanguage, (Codec)Codec.STRING).register("QueuedPacketsCount", ref -> Integer.valueOf(ref.getPacketHandler().getQueuedPacketsCount()), (Codec)Codec.INTEGER).register("PingInfo", ref -> { PacketHandler handler = ref.getPacketHandler(); PongType[] pongTypes = PongType.values(); PacketHandler.PingInfo[] pingInfos = new PacketHandler.PingInfo[pongTypes.length]; for (int i = 0; i < pongTypes.length; i++) pingInfos[i] = handler.getPingInfo(pongTypes[i]);  return (Function)pingInfos; }(Codec)new ArrayCodec((Codec)PacketHandler.PingInfo.METRICS_REGISTRY, x$0 -> new PacketHandler.PingInfo[x$0])).register("PacketStatsRecorder", ref -> { PacketStatsRecorder recorder = ref.getPacketHandler().getPacketStatsRecorder(); PacketStatsRecorderImpl impl = (PacketStatsRecorderImpl)recorder; return (Function)((recorder instanceof PacketStatsRecorderImpl) ? impl : null); }(Codec)PacketStatsRecorderImpl.METRICS_REGISTRY).register("ChunkTracker", PlayerRef::getChunkTracker, (Codec)ChunkTracker.METRICS_REGISTRY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     COMPONENT_METRICS_REGISTRY = (new MetricsRegistry()).register("MovementStates", ref -> { Ref<EntityStore> entityRef = ref.getReference(); if (entityRef == null) return null;  MovementStatesComponent component = (MovementStatesComponent)entityRef.getStore().getComponent(entityRef, MovementStatesComponent.getComponentType()); return (component != null) ? component.getMovementStates().toString() : null; }(Codec)Codec.STRING).register("MovementManager", ref -> { Ref<EntityStore> entityRef = ref.getReference(); if (entityRef == null) return null;  MovementManager component = (MovementManager)entityRef.getStore().getComponent(entityRef, MovementManager.getComponentType()); return (component != null) ? component.toString() : null; }(Codec)Codec.STRING).register("CameraManager", ref -> {
/*     */           Ref<EntityStore> entityRef = ref.getReference();
/*     */           if (entityRef == null) {
/*     */             return null;
/*     */           }
/*     */           CameraManager component = (CameraManager)entityRef.getStore().getComponent(entityRef, CameraManager.getComponentType());
/*     */           return (component != null) ? component.toString() : null;
/*     */         }(Codec)Codec.STRING);
/*     */   }
/*     */   
/*     */   @Nonnull
/* 113 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final UUID uuid;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final String username;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final PacketHandler packetHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final ChunkTracker chunkTracker;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 142 */   private final HiddenPlayersManager hiddenPlayersManager = new HiddenPlayersManager();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private String language;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Ref<EntityStore> entity;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Holder<EntityStore> holder;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private UUID worldUuid;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 172 */   private Transform transform = new Transform(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 177 */   private Vector3f headRotation = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerRef(@Nonnull Holder<EntityStore> holder, @Nonnull UUID uuid, @Nonnull String username, @Nonnull String language, @Nonnull PacketHandler packetHandler, @Nonnull ChunkTracker chunkTracker) {
/* 195 */     this.holder = holder;
/* 196 */     this.uuid = uuid;
/* 197 */     this.username = username;
/* 198 */     this.language = language;
/* 199 */     this.packetHandler = packetHandler;
/* 200 */     this.chunkTracker = chunkTracker;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> addToStore(@Nonnull Store<EntityStore> store) {
/* 212 */     store.assertThread();
/* 213 */     if (this.holder == null) throw new IllegalStateException("Already in world"); 
/* 214 */     return store.addEntity(this.holder, AddReason.LOAD);
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
/*     */   public void addedToStore(Ref<EntityStore> ref) {
/* 227 */     this.holder = null;
/* 228 */     this.entity = ref;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Holder<EntityStore> removeFromStore() {
/* 239 */     if (this.entity == null) throw new IllegalStateException("Not in world"); 
/* 240 */     this.entity.getStore().assertThread();
/*     */     
/* 242 */     Ref<EntityStore> entity = this.entity;
/* 243 */     this.entity = null;
/* 244 */     return this.holder = entity.getStore().removeEntity(entity, RemoveReason.UNLOAD);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/* 253 */     return (this.entity != null || this.holder != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getReference() {
/* 261 */     if (this.entity != null && this.entity.isValid()) return this.entity; 
/* 262 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Holder<EntityStore> getHolder() {
/* 270 */     return this.holder;
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
/*     */   @Nullable
/*     */   @Deprecated
/*     */   public <T extends Component<EntityStore>> T getComponent(@Nonnull ComponentType<EntityStore, T> componentType) {
/* 290 */     if (this.holder != null) {
/* 291 */       return (T)this.holder.getComponent(componentType);
/*     */     }
/*     */     
/* 294 */     Store<EntityStore> store = this.entity.getStore();
/* 295 */     if (store.isInThread()) {
/* 296 */       return (T)store.getComponent(this.entity, componentType);
/*     */     }
/*     */     
/* 299 */     ((HytaleLogger.Api)LOGGER.at(Level.SEVERE)
/* 300 */       .withCause((Throwable)new SkipSentryException()))
/* 301 */       .log("PlayerRef.getComponent(%s) called async with player in world", componentType.getTypeClass().getSimpleName());
/* 302 */     return (T)CompletableFuture.<Component>supplyAsync(() -> getComponent(componentType), (Executor)((EntityStore)store.getExternalData()).getWorld()).join();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public UUID getUuid() {
/* 310 */     return this.uuid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getUsername() {
/* 318 */     return this.username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PacketHandler getPacketHandler() {
/* 326 */     return this.packetHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ChunkTracker getChunkTracker() {
/* 334 */     return this.chunkTracker;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public HiddenPlayersManager getHiddenPlayersManager() {
/* 342 */     return this.hiddenPlayersManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getLanguage() {
/* 350 */     return this.language;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLanguage(@Nonnull String language) {
/* 359 */     this.language = language;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Transform getTransform() {
/* 367 */     return this.transform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public UUID getWorldUuid() {
/* 375 */     return this.worldUuid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3f getHeadRotation() {
/* 383 */     return this.headRotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updatePosition(@Nonnull World world, @Nonnull Transform transform, @Nonnull Vector3f headRotation) {
/* 394 */     this.worldUuid = world.getWorldConfig().getUuid();
/* 395 */     this.transform.assign(transform);
/* 396 */     this.headRotation.assign(headRotation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void replaceHolder(@Nonnull Holder<EntityStore> holder) {
/* 407 */     if (holder == null) throw new IllegalStateException("Player is still in the world"); 
/* 408 */     this.holder = holder;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<EntityStore> clone() {
/* 415 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public MetricResults toMetricResults() {
/* 421 */     return METRICS_REGISTRY.toMetricResults(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void referToServer(@Nonnull String host, int port) {
/* 431 */     referToServer(host, port, null);
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
/*     */   public void referToServer(@Nonnull String host, int port, @Nullable byte[] data) {
/* 443 */     int MAX_REFERRAL_DATA_SIZE = 4096;
/*     */     
/* 445 */     Objects.requireNonNull(host, "Host cannot be null");
/* 446 */     if (port <= 0 || port > 65535) {
/* 447 */       throw new IllegalArgumentException("Port must be between 1 and 65535");
/*     */     }
/*     */     
/* 450 */     if (data != null && data.length > 4096) {
/* 451 */       throw new IllegalArgumentException("Referral data exceeds maximum size of 4096 bytes (got " + data.length + ")");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 456 */     HytaleLogger.getLogger().at(Level.INFO).log("Referring player %s (%s) to %s:%d with %d bytes of data", this.username, this.uuid, host, 
/*     */         
/* 458 */         Integer.valueOf(port), Integer.valueOf((data != null) ? data.length : 0));
/*     */ 
/*     */     
/* 461 */     this.packetHandler.writeNoCache((Packet)new ClientReferral(new HostAddress(host, (short)port), data));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessage(@Nonnull Message message) {
/* 471 */     this.packetHandler.writeNoCache((Packet)new ServerMessage(ChatType.Chat, message.getFormattedMessage()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\PlayerRef.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */