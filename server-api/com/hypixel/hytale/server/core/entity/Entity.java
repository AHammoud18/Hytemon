/*     */ package com.hypixel.hytale.server.core.entity;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.DirectDecodeCodec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.NonSerialized;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.event.IEventDispatcher;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.MovementStates;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.event.events.entity.EntityRemoveEvent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.function.Function;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonString;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ public abstract class Entity implements Component<EntityStore> {
/*     */   @Nonnull
/*  41 */   public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int VERSION = 5;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  52 */   public static final KeyedCodec<Model.ModelReference> MODEL = new KeyedCodec("Model", (Codec)Model.ModelReference.CODEC);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  58 */   public static final KeyedCodec<String> DISPLAY_NAME = new KeyedCodec("DisplayName", (Codec)Codec.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  64 */   public static final KeyedCodec<UUID> UUID = new KeyedCodec("UUID", (Codec)Codec.UUID_BINARY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static final BuilderCodec<Entity> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int UNASSIGNED_ID = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  83 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(Entity.class).legacyVersioned()).codecVersion(5)).append(DISPLAY_NAME, (entity, o) -> entity.legacyDisplayName = o, entity -> entity.legacyDisplayName).add()).append(UUID, (entity, o) -> entity.legacyUuid = o, entity -> entity.legacyUuid).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   protected int networkId = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected UUID legacyUuid;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected World world;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Ref<EntityStore> reference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   private TransformComponent transformComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   protected String legacyDisplayName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 133 */   protected final AtomicBoolean wasRemoved = new AtomicBoolean();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Throwable removedBy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Entity(@Nullable World world) {
/* 149 */     this();
/* 150 */     this.networkId = (world != null) ? world.getEntityStore().takeNextNetworkId() : -1;
/* 151 */     this.world = world;
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
/*     */   @Deprecated(forRemoval = true)
/*     */   public void markNeedsSave() {
/* 169 */     if (this.transformComponent == null)
/* 170 */       return;  WorldChunk chunk = this.transformComponent.getChunk();
/* 171 */     if (chunk != null) chunk.getEntityChunk().markNeedsSaving();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLegacyUUID(@Nullable UUID uuid) {
/* 180 */     this.legacyUuid = uuid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove() {
/* 189 */     this.world.debugAssertInTickingThread();
/*     */     
/* 191 */     if (this.wasRemoved.getAndSet(true)) return false; 
/* 192 */     this.removedBy = new Throwable();
/*     */     
/*     */     try {
/* 195 */       String key = (this.world != null) ? this.world.getName() : null;
/* 196 */       IEventDispatcher<EntityRemoveEvent, EntityRemoveEvent> dispatcher = HytaleServer.get().getEventBus().dispatchFor(EntityRemoveEvent.class, key);
/* 197 */       if (dispatcher.hasListener()) dispatcher.dispatch((IBaseEvent)new EntityRemoveEvent(this));
/*     */ 
/*     */       
/* 200 */       if (this.reference.isValid()) this.world.getEntityStore().getStore().removeEntity(this.reference, RemoveReason.REMOVE); 
/* 201 */     } catch (Throwable t) {
/* 202 */       this.wasRemoved.set(false);
/*     */     } 
/*     */ 
/*     */     
/* 206 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadIntoWorld(@Nonnull World world) {
/* 215 */     if (this.world != null) throw new IllegalArgumentException("Entity is already in a world! " + String.valueOf(this)); 
/* 216 */     this.world = world;
/* 217 */     if (this.networkId == -1) this.networkId = world.getEntityStore().takeNextNetworkId();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unloadFromWorld() {
/* 224 */     if (this.world == null) throw new IllegalArgumentException("Entity is already not in a world! " + String.valueOf(this)); 
/* 225 */     this.networkId = -1;
/* 226 */     this.world = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public int getNetworkId() {
/* 235 */     return this.networkId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public String getLegacyDisplayName() {
/* 244 */     return this.legacyDisplayName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @Deprecated(forRemoval = true)
/*     */   public UUID getUuid() {
/* 254 */     return this.legacyUuid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public void setTransformComponent(TransformComponent transform) {
/* 264 */     this.transformComponent = transform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public TransformComponent getTransformComponent() {
/* 273 */     if (this.world == null || this.reference == null) {
/* 274 */       throw new IllegalStateException("Called before entity was init");
/*     */     }
/* 276 */     if (!this.world.isInThread()) {
/* 277 */       ((HytaleLogger.Api)((HytaleLogger.Api)LOGGER.at(Level.WARNING)
/* 278 */         .atMostEvery(5, TimeUnit.MINUTES))
/* 279 */         .withCause(new Throwable()))
/* 280 */         .log("getPositionComponent called async");
/* 281 */       return this.transformComponent;
/*     */     } 
/* 283 */     Store<EntityStore> store = this.world.getEntityStore().getStore();
/* 284 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(this.reference, TransformComponent.getComponentType());
/* 285 */     assert transformComponent != null;
/* 286 */     return transformComponent;
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
/*     */   @Deprecated
/*     */   public void moveTo(@Nonnull Ref<EntityStore> ref, double locX, double locY, double locZ, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 299 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 300 */     assert transformComponent != null;
/*     */     
/* 302 */     transformComponent.getPosition().assign(locX, locY, locZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public World getWorld() {
/* 310 */     return this.world;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wasRemoved() {
/* 317 */     return this.wasRemoved.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCollidable() {
/* 324 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 329 */     int result = this.networkId;
/* 330 */     result = 31 * result + ((this.world != null) ? this.world.hashCode() : 0);
/* 331 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 336 */     if (this == o) return true; 
/* 337 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 339 */     Entity entity = (Entity)o;
/*     */     
/* 341 */     if (this.networkId != entity.networkId) return false; 
/* 342 */     return (this.world != null) ? this.world.equals(entity.world) : ((entity.world == null));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 348 */     return "Entity{id=" + this.networkId + ", uuid=" + String.valueOf(this.legacyUuid) + ", reference='" + String.valueOf(this.reference) + "', world=" + (
/*     */ 
/*     */ 
/*     */       
/* 352 */       (this.world != null) ? this.world.getName() : null) + ", displayName='" + this.legacyDisplayName + "', wasRemoved='" + String.valueOf(this.wasRemoved) + "', removedBy='" + (
/*     */ 
/*     */       
/* 355 */       (this.removedBy != null) ? (String.valueOf(this.removedBy) + "\n" + String.valueOf(this.removedBy)) : null) + "'}";
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
/*     */   public boolean isHiddenFromLivingEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 368 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReference(@Nonnull Ref<EntityStore> reference) {
/* 377 */     if (this.reference != null && this.reference.isValid()) {
/* 378 */       throw new IllegalArgumentException("Entity already has a valid EntityReference: " + String.valueOf(this.reference) + " new reference " + String.valueOf(reference));
/*     */     }
/* 380 */     this.reference = reference;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getReference() {
/* 388 */     return this.reference;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void clearReference() {
/* 396 */     this.reference = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Component<EntityStore> clone() {
/* 403 */     DirectDecodeCodec<Entity> codec = EntityModule.get().getCodec(getClass());
/* 404 */     Function<World, Entity> constructor = EntityModule.get().getConstructor(getClass());
/*     */     
/* 406 */     BsonDocument document = codec.encode(this, ExtraInfo.THREAD_LOCAL.get()).asDocument();
/* 407 */     document.put("EntityType", (BsonValue)new BsonString(EntityModule.get().getIdentifier(getClass())));
/*     */     
/* 409 */     Entity t = constructor.apply(null);
/* 410 */     codec.decode((BsonValue)document, t, ExtraInfo.THREAD_LOCAL.get());
/* 411 */     return t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Holder<EntityStore> toHolder() {
/* 421 */     if (this.reference != null && this.reference.isValid() && this.world != null) {
/* 422 */       if (!this.world.isInThread()) {
/* 423 */         return CompletableFuture.<Holder<EntityStore>>supplyAsync(this::toHolder, (Executor)this.world).join();
/*     */       }
/*     */       
/* 426 */       Holder<EntityStore> holder1 = EntityStore.REGISTRY.newHolder();
/* 427 */       Store<EntityStore> componentStore = this.world.getEntityStore().getStore();
/* 428 */       Archetype<EntityStore> archetype = componentStore.getArchetype(this.reference);
/* 429 */       for (int i = archetype.getMinIndex(); i < archetype.length(); i++) {
/*     */         
/* 431 */         ComponentType componentType = archetype.get(i);
/* 432 */         if (componentType != null) {
/*     */           
/* 434 */           Component component = componentStore.getComponent(this.reference, componentType);
/* 435 */           assert component != null;
/* 436 */           holder1.addComponent(componentType, component);
/*     */         } 
/* 438 */       }  return holder1;
/*     */     } 
/*     */     
/* 441 */     Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/* 442 */     if (this instanceof Player) {
/* 443 */       holder.addComponent(Player.getComponentType(), this);
/*     */     } else {
/* 445 */       ComponentType<EntityStore, ? extends Entity> componentType = EntityModule.get().getComponentType(getClass());
/*     */       
/* 447 */       holder.addComponent(componentType, this);
/*     */     } 
/*     */ 
/*     */     
/* 451 */     DirectDecodeCodec<? extends Entity> codec = EntityModule.get().getCodec(getClass());
/* 452 */     if (codec == null) {
/* 453 */       holder.addComponent(EntityStore.REGISTRY.getNonSerializedComponentType(), (Component)NonSerialized.get());
/*     */     }
/*     */     
/* 456 */     return holder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Entity() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DefaultAnimations
/*     */   {
/*     */     @Nonnull
/*     */     public static final String DEATH = "Death";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public static final String HURT = "Hurt";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public static final String DESPAWN = "Despawn";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public static final String SWIM_SUFFIX = "Swim";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public static final String FLY_SUFFIX = "Fly";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public static String[] getHurtAnimationIds(@Nonnull MovementStates movementStates, @Nonnull DamageCause damageCause) {
/* 506 */       String animationId = damageCause.getAnimationId();
/*     */       
/* 508 */       if (movementStates.swimming)
/* 509 */         return new String[] { animationId + "Swim", animationId, "Hurt" }; 
/* 510 */       if (movementStates.flying) {
/* 511 */         return new String[] { animationId + "Fly", animationId, "Hurt" };
/*     */       }
/* 513 */       return new String[] { animationId, "Hurt" };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public static String[] getDeathAnimationIds(@Nonnull MovementStates movementStates, @Nonnull DamageCause damageCause) {
/* 529 */       String animationId = damageCause.getDeathAnimationId();
/*     */       
/* 531 */       if (movementStates.swimming)
/* 532 */         return new String[] { animationId + "Swim", animationId, "Death" }; 
/* 533 */       if (movementStates.flying) {
/* 534 */         return new String[] { animationId + "Fly", animationId, "Death" };
/*     */       }
/* 536 */       return new String[] { animationId, "Death" };
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\Entity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */