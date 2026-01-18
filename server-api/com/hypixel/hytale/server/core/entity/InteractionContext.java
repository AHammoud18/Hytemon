/*     */ package com.hypixel.hytale.server.core.entity;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Vector4d;
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.ForkedChainId;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.protocol.InteractionChainData;
/*     */ import com.hypixel.hytale.protocol.InteractionSyncData;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.PrioritySlot;
/*     */ import com.hypixel.hytale.protocol.RootInteractionSettings;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemContext;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.meta.DynamicMetaStore;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.SnapshotBuffer;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.Interactions;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.UnarmedInteractions;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.operation.Label;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.function.Function;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class InteractionContext {
/*     */   @Nonnull
/*  41 */   private static final Function<InteractionContext, Map<String, String>> DEFAULT_VAR_GETTER = InteractionContext::defaultGetVars;
/*     */   
/*  43 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int heldItemSectionId;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private final ItemContainer heldItemContainer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final byte heldItemSlot;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private ItemStack heldItem;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private final Item originalItemType;
/*     */ 
/*     */ 
/*     */   
/*  73 */   private Function<InteractionContext, Map<String, String>> interactionVarsGetter = DEFAULT_VAR_GETTER;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private final InteractionManager interactionManager;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private final Ref<EntityStore> owningEntity;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private final Ref<EntityStore> runningForEntity;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private LivingEntity entity;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private InteractionChain chain;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private InteractionEntry entry;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Label[] labels;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private SnapshotProvider snapshotProvider;
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final DynamicMetaStore<InteractionContext> metaStore;
/*     */ 
/*     */ 
/*     */   
/*     */   private InteractionContext(@Nullable InteractionManager interactionManager, @Nullable Ref<EntityStore> owningEntity, int heldItemSectionId, @Nullable ItemContainer heldItemContainer, byte heldItemSlot, @Nullable ItemStack heldItem) {
/* 119 */     this(interactionManager, owningEntity, owningEntity, heldItemSectionId, heldItemContainer, heldItemSlot, heldItem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InteractionContext(@Nullable InteractionManager interactionManager, @Nullable Ref<EntityStore> owningEntity, @Nullable Ref<EntityStore> runningForEntity, int heldItemSectionId, @Nullable ItemContainer heldItemContainer, byte heldItemSlot, @Nullable ItemStack heldItem) {
/* 129 */     this.interactionManager = interactionManager;
/* 130 */     this.owningEntity = owningEntity;
/* 131 */     this.runningForEntity = runningForEntity;
/* 132 */     this.heldItemSectionId = heldItemSectionId;
/* 133 */     this.heldItemContainer = heldItemContainer;
/* 134 */     this.heldItemSlot = heldItemSlot;
/* 135 */     this.heldItem = heldItem;
/* 136 */     this.originalItemType = (heldItem != null) ? heldItem.getItem() : null;
/* 137 */     this.metaStore = new DynamicMetaStore(this, (IMetaRegistry)Interaction.CONTEXT_META_REGISTRY);
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
/*     */   public InteractionChain fork(@Nonnull InteractionContext context, @Nonnull RootInteraction rootInteraction, boolean predicted) {
/* 155 */     assert this.chain != null;
/* 156 */     return fork(this.chain.getType(), context, rootInteraction, predicted);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionChain fork(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull RootInteraction rootInteraction, boolean predicted) {
/* 209 */     InteractionChainData data = new InteractionChainData(this.chain.getChainData());
/* 210 */     return fork(data, type, context, rootInteraction, predicted);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionChain fork(@Nonnull InteractionChainData data, @Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull RootInteraction rootInteraction, boolean predicted) {
/* 219 */     if (context == this) throw new IllegalArgumentException("Cannot use current context");
/*     */ 
/*     */ 
/*     */     
/* 223 */     Integer slot = (Integer)context.metaStore.getMetaObject(Interaction.TARGET_SLOT);
/*     */ 
/*     */     
/* 226 */     if (slot == null) {
/* 227 */       slot = (Integer)this.metaStore.getMetaObject(Interaction.TARGET_SLOT);
/* 228 */       context.metaStore.putMetaObject(Interaction.TARGET_SLOT, slot);
/*     */     } 
/* 230 */     if (slot != null) data.targetSlot = slot.intValue();
/*     */     
/* 232 */     Ref<EntityStore> targetEntity = (Ref<EntityStore>)context.metaStore.getIfPresentMetaObject(Interaction.TARGET_ENTITY);
/* 233 */     if (targetEntity != null && targetEntity.isValid()) {
/* 234 */       CommandBuffer<EntityStore> commandBuffer = getCommandBuffer();
/* 235 */       assert commandBuffer != null;
/* 236 */       NetworkId networkComponent = (NetworkId)commandBuffer.getComponent(targetEntity, NetworkId.getComponentType());
/* 237 */       if (networkComponent != null) data.entityId = networkComponent.getId();
/*     */     
/*     */     } 
/* 240 */     Vector4d hitLocation = (Vector4d)context.metaStore.getIfPresentMetaObject(Interaction.HIT_LOCATION);
/* 241 */     if (hitLocation != null) {
/* 242 */       data.hitLocation = new Vector3f((float)hitLocation.x, (float)hitLocation.y, (float)hitLocation.z);
/*     */     }
/*     */     
/* 245 */     String hitDetail = (String)context.metaStore.getIfPresentMetaObject(Interaction.HIT_DETAIL);
/* 246 */     if (hitDetail != null) {
/* 247 */       data.hitDetail = hitDetail;
/*     */     }
/*     */     
/* 250 */     BlockPosition targetBlock = (BlockPosition)context.metaStore.getIfPresentMetaObject(Interaction.TARGET_BLOCK_RAW);
/* 251 */     if (targetBlock != null) {
/* 252 */       data.blockPosition = targetBlock;
/*     */     }
/*     */     
/* 255 */     int index = this.chain.getChainId();
/* 256 */     ForkedChainId forkedChainId = this.chain.getForkedChainId();
/* 257 */     ForkedChainId newChainId = new ForkedChainId(this.entry.getIndex(), this.entry.nextForkId(), null);
/* 258 */     if (forkedChainId != null) {
/*     */ 
/*     */       
/* 261 */       ForkedChainId root = forkedChainId = new ForkedChainId(forkedChainId);
/* 262 */       while (root.forkedId != null) {
/* 263 */         root = root.forkedId;
/*     */       }
/* 265 */       root.forkedId = newChainId;
/*     */     } else {
/* 267 */       forkedChainId = newChainId;
/*     */     } 
/*     */     
/* 270 */     InteractionChain forkChain = new InteractionChain(forkedChainId, newChainId, type, context, data, rootInteraction, null, true);
/* 271 */     forkChain.setChainId(index);
/* 272 */     forkChain.setBaseType(this.chain.getBaseType());
/* 273 */     forkChain.setPredicted(predicted);
/* 274 */     forkChain.skipChainOnClick = allowSkipChainOnClick();
/* 275 */     forkChain.setTimeShift(this.chain.getTimeShift());
/*     */     
/* 277 */     this.chain.putForkedChain(newChainId, forkChain);
/* 278 */     InteractionChain.TempChain tempData = this.chain.removeTempForkedChain(newChainId, forkChain);
/* 279 */     if (tempData != null) {
/* 280 */       LOGGER.at(Level.FINEST).log("Loading temp chain data for fork %s", newChainId);
/*     */       
/* 282 */       forkChain.copyTempFrom(tempData);
/*     */     } 
/*     */     
/* 285 */     return forkChain;
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
/*     */   @Nonnull
/*     */   public InteractionContext duplicate() {
/* 298 */     InteractionContext ctx = new InteractionContext(this.interactionManager, this.owningEntity, this.runningForEntity, this.heldItemSectionId, this.heldItemContainer, this.heldItemSlot, this.heldItem);
/* 299 */     ctx.interactionVarsGetter = this.interactionVarsGetter;
/* 300 */     ctx.metaStore.copyFrom(this.metaStore);
/* 301 */     return ctx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Ref<EntityStore> getEntity() {
/* 309 */     return this.runningForEntity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Ref<EntityStore> getOwningEntity() {
/* 317 */     return this.owningEntity;
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
/*     */   public void execute(@Nonnull RootInteraction nextInteraction) {
/* 331 */     (this.chain.getContext().getState()).enteredRootInteraction = RootInteraction.getAssetMap().getIndex(nextInteraction.getId());
/* 332 */     this.chain.pushRoot(nextInteraction, this.entry.isUseSimulationState());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public InteractionChain getChain() {
/* 340 */     return this.chain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public InteractionEntry getEntry() {
/* 348 */     return this.entry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOperationCounter() {
/* 358 */     return this.entry.isUseSimulationState() ? this.chain.getSimulatedOperationCounter() : this.chain.getOperationCounter();
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
/*     */   public void setOperationCounter(int operationCounter) {
/* 370 */     if (this.entry.isUseSimulationState()) {
/* 371 */       this.chain.setSimulatedOperationCounter(operationCounter);
/*     */     } else {
/* 373 */       this.chain.setOperationCounter(operationCounter);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void jump(@Nonnull Label label) {
/* 384 */     setOperationCounter(label.getIndex());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Item getOriginalItemType() {
/* 392 */     return this.originalItemType;
/*     */   }
/*     */   
/*     */   public int getHeldItemSectionId() {
/* 396 */     return this.heldItemSectionId;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ItemContainer getHeldItemContainer() {
/* 401 */     return this.heldItemContainer;
/*     */   }
/*     */   
/*     */   public byte getHeldItemSlot() {
/* 405 */     return this.heldItemSlot;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ItemStack getHeldItem() {
/* 410 */     return this.heldItem;
/*     */   }
/*     */   
/*     */   public void setHeldItem(@Nullable ItemStack heldItem) {
/* 414 */     this.heldItem = heldItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ItemContext createHeldItemContext() {
/* 422 */     if (this.heldItemContainer == null || this.heldItem == null) {
/* 423 */       return null;
/*     */     }
/*     */     
/* 426 */     return new ItemContext(this.heldItemContainer, (short)this.heldItemSlot, this.heldItem);
/*     */   }
/*     */   
/*     */   public Function<InteractionContext, Map<String, String>> getInteractionVarsGetter() {
/* 430 */     return this.interactionVarsGetter;
/*     */   }
/*     */   
/*     */   public Map<String, String> getInteractionVars() {
/* 434 */     return this.interactionVarsGetter.apply(this);
/*     */   }
/*     */   
/*     */   public void setInteractionVarsGetter(Function<InteractionContext, Map<String, String>> interactionVarsGetter) {
/* 438 */     this.interactionVarsGetter = interactionVarsGetter;
/*     */   }
/*     */   
/*     */   public InteractionManager getInteractionManager() {
/* 442 */     return this.interactionManager;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Ref<EntityStore> getTargetEntity() {
/* 447 */     return (Ref<EntityStore>)this.metaStore.getIfPresentMetaObject(Interaction.TARGET_ENTITY);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockPosition getTargetBlock() {
/* 452 */     return (BlockPosition)this.metaStore.getIfPresentMetaObject(Interaction.TARGET_BLOCK);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public DynamicMetaStore<InteractionContext> getMetaStore() {
/* 457 */     return this.metaStore;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public InteractionSyncData getState() {
/* 462 */     return this.entry.getState();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public InteractionSyncData getClientState() {
/* 467 */     return this.entry.getClientState();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public InteractionSyncData getServerState() {
/* 472 */     return this.entry.getServerState();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public DynamicMetaStore<Interaction> getInstanceStore() {
/* 477 */     return this.entry.getMetaStore();
/*     */   }
/*     */   
/*     */   public boolean allowSkipChainOnClick() {
/* 481 */     return this.chain.skipChainOnClick;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLabels(Label[] labels) {
/* 490 */     this.labels = labels;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasLabels() {
/* 499 */     return (this.labels != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Label getLabel(int index) {
/* 509 */     return this.labels[index];
/*     */   }
/*     */   
/*     */   public EntitySnapshot getSnapshot(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 513 */     NetworkId networkIdComponent = (NetworkId)componentAccessor.getComponent(ref, NetworkId.getComponentType());
/* 514 */     assert networkIdComponent != null;
/* 515 */     int networkId = networkIdComponent.getId();
/*     */     
/* 517 */     if (this.snapshotProvider != null) {
/* 518 */       return this.snapshotProvider.getSnapshot(getCommandBuffer(), this.runningForEntity, networkId);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 523 */     SnapshotBuffer snapshotBufferComponent = (SnapshotBuffer)componentAccessor.getComponent(ref, SnapshotBuffer.getComponentType());
/* 524 */     assert snapshotBufferComponent != null;
/*     */     
/* 526 */     EntitySnapshot snapshot = snapshotBufferComponent.getSnapshot(snapshotBufferComponent.getCurrentTickIndex());
/* 527 */     if (snapshot != null) {
/* 528 */       return snapshot;
/*     */     }
/*     */     
/* 531 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 532 */     assert transformComponent != null;
/*     */     
/* 534 */     return new EntitySnapshot(transformComponent
/* 535 */         .getPosition(), transformComponent
/* 536 */         .getRotation());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSnapshotProvider(@Nullable SnapshotProvider snapshotProvider) {
/* 541 */     this.snapshotProvider = snapshotProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimeShift(float shift) {
/* 548 */     if (this.entry.isUseSimulationState()) {
/*     */       return;
/*     */     }
/*     */     
/* 552 */     this.chain.setTimeShift(shift);
/*     */ 
/*     */ 
/*     */     
/* 556 */     if (this.chain.getForkedChainId() == null) {
/* 557 */       this.interactionManager.setGlobalTimeShift(this.chain.getType(), shift);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public CommandBuffer<EntityStore> getCommandBuffer() {
/* 569 */     return this.interactionManager.commandBuffer;
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
/*     */   @Nullable
/*     */   public String getRootInteractionId(@Nonnull InteractionType type) {
/*     */     String interactionIds;
/* 583 */     if (this.runningForEntity != null && this.runningForEntity.isValid()) {
/* 584 */       Interactions interactions = (Interactions)this.runningForEntity.getStore().getComponent(this.runningForEntity, Interactions.getComponentType());
/* 585 */       if (interactions != null) {
/* 586 */         String interactionId = interactions.getInteractionId(type);
/* 587 */         if (interactionId != null) return interactionId;
/*     */       
/*     */       } 
/*     */     } 
/*     */     
/* 592 */     Item heldItem = this.originalItemType;
/* 593 */     if (heldItem == null) {
/* 594 */       UnarmedInteractions unarmedInteraction = (UnarmedInteractions)UnarmedInteractions.getAssetMap().getAsset("Empty");
/* 595 */       interactionIds = (unarmedInteraction != null) ? (String)unarmedInteraction.getInteractions().get(type) : null;
/*     */     } else {
/* 597 */       interactionIds = (String)heldItem.getInteractions().get(type);
/*     */     } 
/* 599 */     return interactionIds;
/*     */   }
/*     */   
/*     */   void initEntry(@Nonnull InteractionChain chain, InteractionEntry entry, @Nullable LivingEntity entity) {
/* 603 */     CommandBuffer<EntityStore> commandBuffer = getCommandBuffer();
/* 604 */     assert commandBuffer != null;
/*     */     
/* 606 */     this.chain = chain;
/* 607 */     this.entry = entry;
/* 608 */     this.entity = entity;
/* 609 */     this.labels = null;
/*     */     
/* 611 */     Player playerComponent = null;
/* 612 */     if (entity != null) {
/* 613 */       playerComponent = (Player)commandBuffer.getComponent(entity.getReference(), Player.getComponentType());
/*     */     }
/*     */     
/* 616 */     GameMode gameMode = (playerComponent != null) ? playerComponent.getGameMode() : GameMode.Adventure;
/* 617 */     RootInteractionSettings settings = (RootInteractionSettings)chain.getRootInteraction().getSettings().get(gameMode);
/*     */     
/* 619 */     chain.skipChainOnClick |= (settings != null && settings.allowSkipChainOnClick) ? 1 : 0;
/*     */   }
/*     */   
/*     */   void deinitEntry(InteractionChain chain, InteractionEntry entry, LivingEntity entity) {
/* 623 */     this.chain = null;
/* 624 */     this.entry = null;
/* 625 */     this.entity = null;
/* 626 */     this.labels = null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 632 */     return "InteractionContext{heldItemSectionId=" + this.heldItemSectionId + ", heldItemContainer=" + String.valueOf(this.heldItemContainer) + ", heldItemSlot=" + this.heldItemSlot + ", heldItem=" + String.valueOf(this.heldItem) + ", originalItemType=" + String.valueOf(this.originalItemType) + ", interactionVarsGetter=" + String.valueOf(this.interactionVarsGetter) + ", entity=" + String.valueOf(this.entity) + ", labels=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 640 */       Arrays.toString((Object[])this.labels) + ", snapshotProvider=" + String.valueOf(this.snapshotProvider) + ", metaStore=" + String.valueOf(this.metaStore) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static InteractionContext forProxyEntity(InteractionManager manager, @Nonnull LivingEntity entity, Ref<EntityStore> runningForEntity) {
/* 648 */     Inventory entityInventory = entity.getInventory();
/* 649 */     return new InteractionContext(manager, entity
/*     */         
/* 651 */         .getReference(), runningForEntity, -1, entityInventory
/*     */ 
/*     */         
/* 654 */         .getHotbar(), entityInventory
/* 655 */         .getActiveHotbarSlot(), entityInventory
/* 656 */         .getItemInHand());
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
/*     */   @Nonnull
/*     */   public static InteractionContext forInteraction(@Nonnull InteractionManager manager, @Nonnull Ref<EntityStore> ref, @Nonnull InteractionType type, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 671 */     if (type == InteractionType.Equipped) {
/* 672 */       throw new IllegalArgumentException("Equipped interaction type requires a slot set");
/*     */     }
/* 674 */     return forInteraction(manager, ref, type, 0, componentAccessor);
/*     */   } @Nonnull
/*     */   public static InteractionContext forInteraction(@Nonnull InteractionManager manager, @Nonnull Ref<EntityStore> ref, @Nonnull InteractionType type, int equipSlot, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*     */     ItemStack primary, secondary;
/*     */     int selectedInventory;
/* 679 */     LivingEntity entity = (LivingEntity)EntityUtils.getEntity(ref, componentAccessor);
/* 680 */     Inventory entityInventory = entity.getInventory();
/* 681 */     switch (type) {
/*     */       case Equipped:
/* 683 */         return new InteractionContext(manager, ref, -3, entityInventory
/*     */ 
/*     */ 
/*     */             
/* 687 */             .getArmor(), (byte)equipSlot, entityInventory
/*     */             
/* 689 */             .getArmor().getItemStack((short)equipSlot));
/*     */       
/*     */       case HeldOffhand:
/* 692 */         return new InteractionContext(manager, ref, -5, entityInventory
/*     */ 
/*     */ 
/*     */             
/* 696 */             .getUtility(), entityInventory
/* 697 */             .getActiveUtilitySlot(), entityInventory
/* 698 */             .getUtilityItem());
/*     */       
/*     */       case Ability1:
/*     */       case Ability2:
/*     */       case Ability3:
/*     */       case Pick:
/*     */       case Primary:
/*     */       case Secondary:
/* 706 */         if (entityInventory.usingToolsItem()) {
/* 707 */           return new InteractionContext(manager, ref, -8, entityInventory
/*     */ 
/*     */ 
/*     */               
/* 711 */               .getTools(), entityInventory
/* 712 */               .getActiveToolsSlot(), entityInventory
/* 713 */               .getToolsItem());
/*     */         }
/*     */         
/* 716 */         primary = entityInventory.getItemInHand();
/* 717 */         secondary = entityInventory.getUtilityItem();
/*     */         
/* 719 */         selectedInventory = -1;
/*     */         
/* 721 */         if (primary == null && secondary != null) {
/*     */           
/* 723 */           selectedInventory = -5;
/* 724 */         } else if (primary != null && secondary != null) {
/*     */           
/* 726 */           int prioPrimary = primary.getItem().getInteractionConfig().getPriorityFor(type, PrioritySlot.MainHand);
/* 727 */           int prioSecondary = secondary.getItem().getInteractionConfig().getPriorityFor(type, PrioritySlot.OffHand);
/*     */           
/* 729 */           if (prioPrimary == prioSecondary) {
/*     */             
/* 731 */             if (type == InteractionType.Secondary && primary.getItem().getUtility().isCompatible()) {
/* 732 */               selectedInventory = -5;
/*     */             }
/* 734 */           } else if (prioPrimary < prioSecondary) {
/* 735 */             selectedInventory = -5;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 740 */         if (selectedInventory == -5) {
/* 741 */           return new InteractionContext(manager, ref, -5, entityInventory
/*     */ 
/*     */ 
/*     */               
/* 745 */               .getUtility(), entityInventory
/* 746 */               .getActiveUtilitySlot(), entityInventory
/* 747 */               .getUtilityItem());
/*     */         }
/*     */         
/* 750 */         return new InteractionContext(manager, ref, -1, entityInventory
/*     */ 
/*     */ 
/*     */             
/* 754 */             .getHotbar(), entityInventory
/* 755 */             .getActiveHotbarSlot(), entityInventory
/* 756 */             .getItemInHand());
/*     */     } 
/*     */ 
/*     */     
/* 760 */     return new InteractionContext(manager, ref, -1, entityInventory
/*     */ 
/*     */ 
/*     */         
/* 764 */         .getHotbar(), entityInventory
/* 765 */         .getActiveHotbarSlot(), entityInventory
/* 766 */         .getItemInHand());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static InteractionContext withoutEntity() {
/* 773 */     return new InteractionContext(null, null, -1, null, (byte)-1, null);
/*     */   } @Deprecated
/*     */   @FunctionalInterface
/*     */   public static interface SnapshotProvider { EntitySnapshot getSnapshot(CommandBuffer<EntityStore> param1CommandBuffer, Ref<EntityStore> param1Ref, int param1Int); } @Nullable
/*     */   private static Map<String, String> defaultGetVars(@Nonnull InteractionContext c) {
/* 778 */     Item item = c.originalItemType;
/* 779 */     if (item != null) {
/* 780 */       return item.getInteractionVars();
/*     */     }
/* 782 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\InteractionContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */