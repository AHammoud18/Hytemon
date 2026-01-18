/*     */ package com.hypixel.hytale.server.core.modules.entity.item;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.iterator.CircleIterator;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.ColorLight;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemEntityConfig;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction;
/*     */ import com.hypixel.hytale.server.core.modules.entity.BlockMigrationExtraInfo;
/*     */ import com.hypixel.hytale.server.core.modules.entity.DespawnComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.Intangible;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ItemComponent implements Component<EntityStore> {
/*     */   @Nonnull
/*  45 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass(); @Nonnull
/*     */   public static final BuilderCodec<ItemComponent> CODEC; private static final float DROPPED_ITEM_VERTICAL_BOUNCE_VELOCITY = 3.25F;
/*     */   private static final float DROPPED_ITEM_HORIZONTAL_BOUNCE_VELOCITY = 3.0F;
/*     */   public static final float DEFAULT_PICKUP_DELAY = 0.5F;
/*     */   
/*     */   @Nonnull
/*     */   public static ComponentType<EntityStore, ItemComponent> getComponentType() {
/*  52 */     return EntityModule.get().getItemComponentType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float PICKUP_DELAY_DROPPED = 1.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float PICKUP_THROTTLE = 0.25F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float DEFAULT_MERGE_DELAY = 1.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private ItemStack itemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isNetworkOutdated;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  98 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ItemComponent.class, ItemComponent::new).append(new KeyedCodec("Item", (Codec)ItemStack.CODEC), (item, itemStack, extraInfo) -> { item.itemStack = itemStack; if (extraInfo instanceof BlockMigrationExtraInfo) { String newItemId = ((BlockMigrationExtraInfo)extraInfo).getBlockMigration().apply(itemStack.getItemId()); if (!newItemId.equals(itemStack.getItemId())) item.itemStack = new ItemStack(newItemId, itemStack.getQuantity(), itemStack.getMetadata());  }  }(item, extraInfo) -> item.itemStack).add()).append(new KeyedCodec("StackDelay", (Codec)Codec.FLOAT), (item, v) -> item.mergeDelay = v.floatValue(), item -> Float.valueOf(item.mergeDelay)).add()).append(new KeyedCodec("PickupDelay", (Codec)Codec.FLOAT), (item, v) -> item.pickupDelay = v.floatValue(), item -> Float.valueOf(item.pickupDelay)).add()).append(new KeyedCodec("PickupThrottle", (Codec)Codec.FLOAT), (item, v) -> item.pickupThrottle = v.floatValue(), item -> Float.valueOf(item.pickupThrottle)).add()).append(new KeyedCodec("RemovedByPlayerPickup", (Codec)Codec.BOOLEAN), (item, v) -> item.removedByPlayerPickup = v.booleanValue(), item -> Boolean.valueOf(item.removedByPlayerPickup)).add()).build();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   private float mergeDelay = 1.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   private float pickupDelay = 0.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float pickupThrottle;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean removedByPlayerPickup;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   private float pickupRange = -1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemComponent(@Nullable ItemStack itemStack) {
/* 178 */     this.itemStack = itemStack;
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
/*     */   public ItemComponent(@Nullable ItemStack itemStack, float mergeDelay, float pickupDelay, float pickupThrottle, boolean removedByPlayerPickup) {
/* 191 */     this.itemStack = itemStack;
/* 192 */     this.mergeDelay = mergeDelay;
/* 193 */     this.pickupDelay = pickupDelay;
/* 194 */     this.pickupThrottle = pickupThrottle;
/* 195 */     this.removedByPlayerPickup = removedByPlayerPickup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ItemStack getItemStack() {
/* 203 */     return this.itemStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemStack(@Nullable ItemStack itemStack) {
/* 212 */     this.itemStack = itemStack;
/* 213 */     this.isNetworkOutdated = true;
/*     */     
/* 215 */     this.pickupRange = -1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPickupDelay(float pickupDelay) {
/* 224 */     this.pickupDelay = pickupDelay;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPickupRadius(@Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 234 */     if (this.pickupRange < 0.0F) {
/* 235 */       World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/* 236 */       ItemEntityConfig defaultConfig = world.getGameplayConfig().getItemEntityConfig();
/* 237 */       ItemEntityConfig config = (this.itemStack != null) ? this.itemStack.getItem().getItemEntityConfig() : null;
/* 238 */       this.pickupRange = (config != null && config.getPickupRadius() != -1.0F) ? config.getPickupRadius() : defaultConfig.getPickupRadius();
/*     */     } 
/* 240 */     return this.pickupRange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float computeLifetimeSeconds(@Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 250 */     ItemEntityConfig itemEntityConfig = (this.itemStack != null) ? this.itemStack.getItem().getItemEntityConfig() : null;
/* 251 */     ItemEntityConfig defaultConfig = ((EntityStore)componentAccessor.getExternalData()).getWorld().getGameplayConfig().getItemEntityConfig();
/* 252 */     Float ttl = (itemEntityConfig != null && itemEntityConfig.getTtl() != null) ? itemEntityConfig.getTtl() : defaultConfig.getTtl();
/* 253 */     return (ttl != null) ? ttl.floatValue() : 120.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ColorLight computeDynamicLight() {
/* 263 */     ColorLight dynamicLight = null;
/*     */     
/* 265 */     Item item = (this.itemStack != null) ? this.itemStack.getItem() : null;
/*     */     
/* 267 */     if (item != null) {
/* 268 */       if (item.hasBlockType()) {
/* 269 */         BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(this.itemStack.getBlockKey());
/* 270 */         if (blockType != null && blockType.getLight() != null) {
/* 271 */           dynamicLight = blockType.getLight();
/*     */         }
/* 273 */       } else if (item.getLight() != null) {
/* 274 */         dynamicLight = item.getLight();
/*     */       } 
/*     */     }
/*     */     
/* 278 */     return dynamicLight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean pollPickupDelay(float dt) {
/* 288 */     if (this.pickupDelay <= 0.0F) return true; 
/* 289 */     this.pickupDelay -= dt;
/* 290 */     return (this.pickupDelay <= 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean pollPickupThrottle(float dt) {
/* 300 */     this.pickupThrottle -= dt;
/* 301 */     if (this.pickupThrottle <= 0.0F) {
/* 302 */       this.pickupThrottle = 0.25F;
/* 303 */       return true;
/*     */     } 
/* 305 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean pollMergeDelay(float dt) {
/* 315 */     this.mergeDelay -= dt;
/* 316 */     if (this.mergeDelay <= 0.0F) {
/* 317 */       this.mergeDelay = 1.5F;
/* 318 */       return true;
/*     */     } 
/* 320 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPickUp() {
/* 329 */     return (this.pickupDelay <= 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRemovedByPlayerPickup() {
/* 336 */     return this.removedByPlayerPickup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRemovedByPlayerPickup(boolean removedByPlayerPickup) {
/* 345 */     this.removedByPlayerPickup = removedByPlayerPickup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean consumeNetworkOutdated() {
/* 354 */     boolean temp = this.isNetworkOutdated;
/* 355 */     this.isNetworkOutdated = false;
/* 356 */     return temp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemComponent clone() {
/* 363 */     return new ItemComponent(this.itemStack, this.mergeDelay, this.pickupDelay, this.pickupThrottle, this.removedByPlayerPickup);
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
/*     */   @Nonnull
/*     */   public static Holder<EntityStore>[] generateItemDrops(@Nonnull ComponentAccessor<EntityStore> accessor, @Nonnull List<ItemStack> itemStacks, @Nonnull Vector3d position, @Nonnull Vector3f rotation) {
/* 389 */     if (itemStacks.size() == 1) {
/* 390 */       Holder<EntityStore> itemEntityHolder = generateItemDrop(accessor, itemStacks.getFirst(), position, rotation, 0.0F, 3.25F, 0.0F);
/* 391 */       if (itemEntityHolder == null) return (Holder<EntityStore>[])Holder.emptyArray(); 
/* 392 */       return (Holder<EntityStore>[])new Holder[] { itemEntityHolder };
/*     */     } 
/*     */ 
/*     */     
/* 396 */     float randomAngleOffset = ThreadLocalRandom.current().nextFloat() * 6.2831855F;
/* 397 */     CircleIterator iterator = new CircleIterator(Vector3d.ZERO, 3.0D, itemStacks.size(), randomAngleOffset);
/* 398 */     return (Holder<EntityStore>[])itemStacks.stream()
/* 399 */       .map(item -> {
/*     */           Vector3d circlePos = iterator.next();
/*     */           
/*     */           return generateItemDrop(accessor, item, position, rotation, (float)circlePos.getX(), 3.25F, (float)circlePos.getZ());
/* 403 */         }).filter(Objects::nonNull)
/* 404 */       .toArray(x$0 -> new Holder[x$0]);
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
/*     */   @Nullable
/*     */   public static Holder<EntityStore> generateItemDrop(@Nonnull ComponentAccessor<EntityStore> accessor, @Nullable ItemStack itemStack, @Nonnull Vector3d position, @Nonnull Vector3f rotation, float velocityX, float velocityY, float velocityZ) {
/* 428 */     if (itemStack == null || itemStack.isEmpty() || !itemStack.isValid()) {
/* 429 */       LOGGER.at(Level.WARNING).log("Attempted to drop invalid item %s at %s", itemStack, position);
/* 430 */       return null;
/*     */     } 
/*     */     
/* 433 */     Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/* 434 */     ItemComponent itemComponent = new ItemComponent(itemStack);
/* 435 */     holder.addComponent(getComponentType(), itemComponent);
/* 436 */     holder.addComponent(TransformComponent.getComponentType(), (Component)new TransformComponent(position, rotation));
/*     */     
/* 438 */     ((Velocity)holder.ensureAndGetComponent(Velocity.getComponentType())).set(velocityX, velocityY, velocityZ);
/*     */     
/* 440 */     holder.ensureComponent(PhysicsValues.getComponentType());
/* 441 */     holder.ensureComponent(UUIDComponent.getComponentType());
/* 442 */     holder.ensureComponent(Intangible.getComponentType());
/*     */     
/* 444 */     float tempTtl = itemComponent.computeLifetimeSeconds(accessor);
/* 445 */     TimeResource timeResource = (TimeResource)accessor.getResource(TimeResource.getResourceType());
/* 446 */     holder.addComponent(DespawnComponent.getComponentType(), (Component)DespawnComponent.despawnInSeconds(timeResource, tempTtl));
/*     */     
/* 448 */     return holder;
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
/*     */   @Nonnull
/*     */   public static Holder<EntityStore> generatePickedUpItem(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Ref<EntityStore> targetRef, @Nonnull Vector3d targetPosition) {
/* 466 */     Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/*     */     
/* 468 */     TransformComponent itemTransformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 469 */     assert itemTransformComponent != null;
/*     */     
/* 471 */     ItemComponent itemItemComponent = (ItemComponent)componentAccessor.getComponent(ref, getComponentType());
/* 472 */     assert itemItemComponent != null;
/*     */     
/* 474 */     HeadRotation itemHeadRotationComponent = (HeadRotation)componentAccessor.getComponent(ref, HeadRotation.getComponentType());
/* 475 */     assert itemHeadRotationComponent != null;
/*     */ 
/*     */     
/* 478 */     PickupItemComponent pickupItemComponent = new PickupItemComponent(targetRef, targetPosition.clone());
/* 479 */     holder.addComponent(PickupItemComponent.getComponentType(), pickupItemComponent);
/*     */     
/* 481 */     holder.addComponent(getComponentType(), itemItemComponent.clone());
/* 482 */     holder.addComponent(TransformComponent.getComponentType(), (Component)itemTransformComponent.clone());
/*     */     
/* 484 */     holder.ensureComponent(PreventItemMerging.getComponentType());
/* 485 */     holder.ensureComponent(Intangible.getComponentType());
/*     */ 
/*     */     
/* 488 */     holder.addComponent(NetworkId.getComponentType(), (Component)new NetworkId(((EntityStore)ref.getStore().getExternalData()).takeNextNetworkId()));
/*     */ 
/*     */     
/* 491 */     holder.ensureComponent(EntityStore.REGISTRY.getNonSerializedComponentType());
/*     */     
/* 493 */     return holder;
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
/*     */   @Nonnull
/*     */   public static Holder<EntityStore> generatePickedUpItem(@Nonnull ItemStack itemStack, @Nonnull Vector3d position, @Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Ref<EntityStore> targetRef) {
/* 511 */     Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/*     */ 
/*     */     
/* 514 */     PickupItemComponent pickupItemComponent = new PickupItemComponent(targetRef, position.clone());
/* 515 */     holder.addComponent(PickupItemComponent.getComponentType(), pickupItemComponent);
/*     */     
/* 517 */     holder.addComponent(getComponentType(), new ItemComponent(new ItemStack(itemStack.getItemId())));
/* 518 */     holder.addComponent(TransformComponent.getComponentType(), (Component)new TransformComponent(position.clone(), Vector3f.ZERO.clone()));
/*     */     
/* 520 */     holder.ensureComponent(PreventItemMerging.getComponentType());
/* 521 */     holder.ensureComponent(Intangible.getComponentType());
/*     */ 
/*     */     
/* 524 */     holder.addComponent(NetworkId.getComponentType(), (Component)new NetworkId(((EntityStore)componentAccessor.getExternalData()).takeNextNetworkId()));
/*     */ 
/*     */     
/* 527 */     holder.ensureComponent(EntityStore.REGISTRY.getNonSerializedComponentType());
/*     */     
/* 529 */     return holder;
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
/*     */   @Nullable
/*     */   public static ItemStack addToItemContainer(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> itemRef, @Nonnull ItemContainer itemContainer) {
/* 546 */     if (!itemRef.isValid()) return null;
/*     */     
/* 548 */     ItemComponent itemComponent = (ItemComponent)store.getComponent(itemRef, getComponentType());
/* 549 */     if (itemComponent == null || itemComponent.pickupDelay > 0.0F) return null;
/*     */     
/* 551 */     ItemStack itemStack = itemComponent.getItemStack();
/* 552 */     if (itemStack == null) return null;
/*     */     
/* 554 */     ItemStackTransaction transaction = itemContainer.addItemStack(itemStack);
/* 555 */     ItemStack remainder = transaction.getRemainder();
/*     */     
/* 557 */     if (remainder != null && !remainder.isEmpty()) {
/* 558 */       itemComponent.setPickupDelay(0.25F);
/* 559 */       itemComponent.setItemStack(remainder);
/*     */       
/* 561 */       int quantity = itemStack.getQuantity() - remainder.getQuantity();
/*     */ 
/*     */       
/* 564 */       if (quantity <= 0) return null;
/*     */ 
/*     */       
/* 567 */       return itemStack.withQuantity(quantity);
/*     */     } 
/*     */ 
/*     */     
/* 571 */     store.removeEntity(itemRef, RemoveReason.REMOVE);
/*     */ 
/*     */     
/* 574 */     return itemStack;
/*     */   }
/*     */   
/*     */   public ItemComponent() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\item\ItemComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */