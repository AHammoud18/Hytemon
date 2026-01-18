/*     */ package com.hypixel.hytale.builtin.buildertools.interactions;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSettings;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class PickupItemInteraction extends SimpleInstantInteraction {
/*  26 */   public static final BuilderCodec<PickupItemInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(PickupItemInteraction.class, PickupItemInteraction::new, SimpleInstantInteraction.CODEC)
/*  27 */     .documentation("Picks up an item entity and adds it to the player's inventory."))
/*  28 */     .build();
/*     */   
/*     */   public static final String DEFAULT_ID = "*PickupItem";
/*  31 */   public static final RootInteraction DEFAULT_ROOT = new RootInteraction("*PickupItem", new String[] { "*PickupItem" });
/*     */   
/*     */   public PickupItemInteraction(String id) {
/*  34 */     super(id);
/*     */   }
/*     */ 
/*     */   
/*     */   protected PickupItemInteraction() {}
/*     */ 
/*     */   
/*     */   protected final void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*  42 */     Ref<EntityStore> ref = context.getEntity();
/*  43 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*     */     
/*  45 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/*  46 */     if (playerComponent == null) {
/*  47 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/*  51 */     Ref<EntityStore> targetRef = context.getTargetEntity();
/*  52 */     if (targetRef == null || !targetRef.isValid()) {
/*  53 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/*  57 */     ItemComponent itemComponent = (ItemComponent)commandBuffer.getComponent(targetRef, ItemComponent.getComponentType());
/*  58 */     if (itemComponent == null) {
/*  59 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/*  63 */     TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(targetRef, TransformComponent.getComponentType());
/*  64 */     if (transformComponent == null) {
/*  65 */       (context.getState()).state = InteractionState.Failed;
/*     */       
/*     */       return;
/*     */     } 
/*  69 */     ItemStack itemStack = itemComponent.getItemStack();
/*  70 */     Item item = itemStack.getItem();
/*  71 */     Vector3d itemEntityPosition = transformComponent.getPosition();
/*     */     
/*  73 */     PlayerSettings playerSettings = (PlayerSettings)commandBuffer.getComponent(ref, PlayerSettings.getComponentType());
/*  74 */     if (playerSettings == null) playerSettings = PlayerSettings.defaults();
/*     */     
/*  76 */     ItemContainer itemContainer = playerComponent.getInventory().getContainerForItemPickup(item, playerSettings);
/*  77 */     ItemStackTransaction transaction = itemContainer.addItemStack(itemStack);
/*  78 */     ItemStack remainder = transaction.getRemainder();
/*     */     
/*  80 */     if (ItemStack.isEmpty(remainder)) {
/*  81 */       itemComponent.setRemovedByPlayerPickup(true);
/*  82 */       commandBuffer.removeEntity(targetRef, RemoveReason.REMOVE);
/*  83 */       playerComponent.notifyPickupItem(ref, itemStack, itemEntityPosition, (ComponentAccessor)commandBuffer);
/*     */       
/*  85 */       Holder<EntityStore> pickupItemHolder = ItemComponent.generatePickedUpItem(targetRef, (ComponentAccessor)commandBuffer, ref, itemEntityPosition);
/*  86 */       commandBuffer.addEntity(pickupItemHolder, AddReason.SPAWN);
/*  87 */     } else if (!remainder.equals(itemStack)) {
/*  88 */       int quantity = itemStack.getQuantity() - remainder.getQuantity();
/*  89 */       itemComponent.setItemStack(remainder);
/*     */       
/*  91 */       Holder<EntityStore> pickupItemHolder = ItemComponent.generatePickedUpItem(targetRef, (ComponentAccessor)commandBuffer, ref, itemEntityPosition);
/*  92 */       commandBuffer.addEntity(pickupItemHolder, AddReason.SPAWN);
/*     */       
/*  94 */       if (quantity > 0) {
/*  95 */         playerComponent.notifyPickupItem(ref, itemStack.withQuantity(quantity), itemEntityPosition, (ComponentAccessor)commandBuffer);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 103 */     return "PickupItemInteraction{} " + super.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\interactions\PickupItemInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */