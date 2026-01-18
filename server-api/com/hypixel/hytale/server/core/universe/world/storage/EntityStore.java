/*     */ package com.hypixel.hytale.server.core.universe.world.storage;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.store.CodecKey;
/*     */ import com.hypixel.hytale.codec.store.CodecStore;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentRegistry;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.IResourceStorage;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.metrics.MetricsRegistry;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.WorldProvider;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class EntityStore implements WorldProvider {
/*     */   @Nonnull
/*  34 */   public static final MetricsRegistry<EntityStore> METRICS_REGISTRY = (new MetricsRegistry())
/*  35 */     .register("Store", EntityStore::getStore, (Codec)Store.METRICS_REGISTRY);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  41 */   public static final ComponentRegistry<EntityStore> REGISTRY = new ComponentRegistry();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  47 */   public static final CodecKey<Holder<EntityStore>> HOLDER_CODEC_KEY = new CodecKey("EntityHolder");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  53 */     Objects.requireNonNull(REGISTRY); CodecStore.STATIC.putCodecSupplier(HOLDER_CODEC_KEY, REGISTRY::getEntityCodec);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  60 */   public static final SystemGroup<EntityStore> SEND_PACKET_GROUP = REGISTRY.registerSystemGroup();
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  65 */   private final AtomicInteger networkIdCounter = new AtomicInteger(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final World world;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Store<EntityStore> store;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  84 */   private final Map<UUID, Ref<EntityStore>> entitiesByUuid = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  92 */   private final Int2ObjectMap<Ref<EntityStore>> networkIdToRef = (Int2ObjectMap<Ref<EntityStore>>)new Int2ObjectOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityStore(@Nonnull World world) {
/* 101 */     this.world = world;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start(@Nonnull IResourceStorage resourceStorage) {
/* 110 */     this.store = REGISTRY.addStore(this, resourceStorage, store -> this.store = store);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 119 */     this.store.shutdown();
/* 120 */     this.entitiesByUuid.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Store<EntityStore> getStore() {
/* 127 */     return this.store;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getRefFromUUID(@Nonnull UUID uuid) {
/* 138 */     return this.entitiesByUuid.get(uuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getRefFromNetworkId(int networkId) {
/* 149 */     return (Ref<EntityStore>)this.networkIdToRef.get(networkId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int takeNextNetworkId() {
/* 158 */     return this.networkIdCounter.getAndIncrement();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public World getWorld() {
/* 164 */     return this.world;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class UUIDSystem
/*     */     extends RefSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/* 176 */     private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 181 */       return (Query<EntityStore>)UUIDComponent.getComponentType();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 186 */       UUIDComponent uuidComponent = (UUIDComponent)commandBuffer.getComponent(ref, UUIDComponent.getComponentType());
/* 187 */       assert uuidComponent != null;
/*     */       
/* 189 */       Ref<EntityStore> currentRef = ((EntityStore)store.getExternalData()).entitiesByUuid.putIfAbsent(uuidComponent.getUuid(), ref);
/* 190 */       if (currentRef != null) {
/* 191 */         LOGGER.at(Level.WARNING).log("Removing duplicate entity with UUID: %s", uuidComponent.getUuid());
/* 192 */         commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 198 */       UUIDComponent uuidComponent = (UUIDComponent)commandBuffer.getComponent(ref, UUIDComponent.getComponentType());
/* 199 */       assert uuidComponent != null;
/*     */       
/* 201 */       ((EntityStore)store.getExternalData()).entitiesByUuid.remove(uuidComponent.getUuid(), ref);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class NetworkIdSystem
/*     */     extends RefSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 213 */       return (Query<EntityStore>)NetworkId.getComponentType();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 218 */       EntityStore entityStore = (EntityStore)store.getExternalData();
/*     */       
/* 220 */       NetworkId networkIdComponent = (NetworkId)commandBuffer.getComponent(ref, NetworkId.getComponentType());
/* 221 */       assert networkIdComponent != null;
/*     */       
/* 223 */       int networkId = networkIdComponent.getId();
/*     */ 
/*     */ 
/*     */       
/* 227 */       if (entityStore.networkIdToRef.putIfAbsent(networkId, ref) != null) {
/* 228 */         networkId = entityStore.takeNextNetworkId();
/* 229 */         commandBuffer.putComponent(ref, NetworkId.getComponentType(), (Component)new NetworkId(networkId));
/* 230 */         entityStore.networkIdToRef.put(networkId, ref);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 236 */       EntityStore entityStore = (EntityStore)store.getExternalData();
/*     */       
/* 238 */       NetworkId networkIdComponent = (NetworkId)commandBuffer.getComponent(ref, NetworkId.getComponentType());
/* 239 */       assert networkIdComponent != null;
/*     */       
/* 241 */       entityStore.networkIdToRef.remove(networkIdComponent.getId(), ref);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\EntityStore.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */