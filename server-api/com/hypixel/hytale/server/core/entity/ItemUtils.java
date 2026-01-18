/*     */ package com.hypixel.hytale.server.core.entity;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.system.EcsEvent;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.event.events.ecs.DropItemEvent;
/*     */ import com.hypixel.hytale.server.core.event.events.ecs.InteractivelyPickupItemEvent;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSettings;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.logging.Level;
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
/*     */ public class ItemUtils
/*     */ {
/*     */   public static void interactivelyPickupItem(@Nonnull Ref<EntityStore> ref, @Nonnull ItemStack itemStack, @Nullable Vector3d origin, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  44 */     LivingEntity entity = (LivingEntity)EntityUtils.getEntity(ref, componentAccessor);
/*     */ 
/*     */ 
/*     */     
/*  48 */     InteractivelyPickupItemEvent event = new InteractivelyPickupItemEvent(itemStack);
/*  49 */     componentAccessor.invoke(ref, (EcsEvent)event);
/*     */     
/*  51 */     if (event.isCancelled()) {
/*  52 */       dropItem(ref, itemStack, componentAccessor);
/*     */       
/*     */       return;
/*     */     } 
/*  56 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/*  57 */     if (playerComponent != null) {
/*  58 */       TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/*  59 */       assert transformComponent != null;
/*     */       
/*  61 */       PlayerSettings playerSettingsComponent = (PlayerSettings)componentAccessor.getComponent(ref, PlayerSettings.getComponentType());
/*  62 */       if (playerSettingsComponent == null) playerSettingsComponent = PlayerSettings.defaults();
/*     */       
/*  64 */       Holder<EntityStore> pickupItemHolder = null;
/*     */       
/*  66 */       Item item = itemStack.getItem();
/*  67 */       ItemContainer itemContainer = playerComponent.getInventory().getContainerForItemPickup(item, playerSettingsComponent);
/*  68 */       ItemStackTransaction transaction = itemContainer.addItemStack(itemStack);
/*  69 */       ItemStack remainder = transaction.getRemainder();
/*     */       
/*  71 */       if (remainder != null && !remainder.isEmpty()) {
/*     */ 
/*     */         
/*  74 */         int quantity = itemStack.getQuantity() - remainder.getQuantity();
/*  75 */         if (quantity > 0) {
/*  76 */           ItemStack itemStackClone = itemStack.withQuantity(quantity);
/*  77 */           playerComponent.notifyPickupItem(ref, itemStackClone, null, componentAccessor);
/*     */           
/*  79 */           if (origin != null) {
/*  80 */             pickupItemHolder = ItemComponent.generatePickedUpItem(itemStackClone, origin, componentAccessor, ref);
/*     */           }
/*     */         } 
/*     */         
/*  84 */         dropItem(ref, remainder, componentAccessor);
/*     */       } else {
/*  86 */         playerComponent.notifyPickupItem(ref, itemStack, null, componentAccessor);
/*     */         
/*  88 */         if (origin != null) {
/*  89 */           pickupItemHolder = ItemComponent.generatePickedUpItem(itemStack, origin, componentAccessor, ref);
/*     */         }
/*     */       } 
/*     */       
/*  93 */       if (pickupItemHolder != null) {
/*  94 */         componentAccessor.addEntity(pickupItemHolder, AddReason.SPAWN);
/*     */       }
/*     */     } else {
/*  97 */       SimpleItemContainer.addOrDropItemStack(componentAccessor, ref, (ItemContainer)entity.getInventory().getCombinedHotbarFirst(), itemStack);
/*     */     } 
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
/*     */   @Nullable
/*     */   public static Ref<EntityStore> throwItem(@Nonnull Ref<EntityStore> ref, @Nonnull ItemStack itemStack, float throwSpeed, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 118 */     DropItemEvent.Drop event = new DropItemEvent.Drop(itemStack, throwSpeed);
/* 119 */     componentAccessor.invoke(ref, (EcsEvent)event);
/*     */ 
/*     */     
/* 122 */     if (event.isCancelled()) {
/* 123 */       return null;
/*     */     }
/*     */     
/* 126 */     throwSpeed = event.getThrowSpeed();
/* 127 */     itemStack = event.getItemStack();
/*     */     
/* 129 */     if (itemStack.isEmpty() || !itemStack.isValid()) {
/* 130 */       HytaleLogger.getLogger().at(Level.WARNING).log("Attempted to throw invalid item %s at %s by %s", itemStack, Float.valueOf(throwSpeed), Integer.valueOf(ref.getIndex()));
/* 131 */       return null;
/*     */     } 
/*     */     
/* 134 */     HeadRotation headRotationComponent = (HeadRotation)componentAccessor.getComponent(ref, HeadRotation.getComponentType());
/* 135 */     assert headRotationComponent != null;
/*     */     
/* 137 */     Vector3f rotation = headRotationComponent.getRotation();
/* 138 */     Vector3d direction = Transform.getDirection(rotation.getPitch(), rotation.getYaw());
/* 139 */     return throwItem(ref, componentAccessor, itemStack, direction, throwSpeed);
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
/*     */   @Nullable
/*     */   public static Ref<EntityStore> throwItem(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> store, @Nonnull ItemStack itemStack, @Nonnull Vector3d throwDirection, float throwSpeed) {
/* 160 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 161 */     assert transformComponent != null;
/*     */     
/* 163 */     ModelComponent modelComponent = (ModelComponent)store.getComponent(ref, ModelComponent.getComponentType());
/* 164 */     assert modelComponent != null;
/*     */     
/* 166 */     Vector3d throwPosition = transformComponent.getPosition().clone();
/* 167 */     Model model = modelComponent.getModel();
/*     */ 
/*     */     
/* 170 */     throwPosition
/* 171 */       .add(0.0D, model.getEyeHeight(ref, store), 0.0D)
/* 172 */       .add(throwDirection);
/*     */     
/* 174 */     Holder<EntityStore> itemEntityHolder = ItemComponent.generateItemDrop(store, itemStack, throwPosition, Vector3f.ZERO, (float)throwDirection.x * throwSpeed, (float)throwDirection.y * throwSpeed, (float)throwDirection.z * throwSpeed);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     if (itemEntityHolder == null) return null;
/*     */     
/* 181 */     ItemComponent itemComponent = (ItemComponent)itemEntityHolder.getComponent(ItemComponent.getComponentType());
/* 182 */     if (itemComponent != null) itemComponent.setPickupDelay(1.5F);
/*     */     
/* 184 */     return store.addEntity(itemEntityHolder, AddReason.SPAWN);
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
/*     */   @Nullable
/*     */   public static Ref<EntityStore> dropItem(@Nonnull Ref<EntityStore> ref, @Nonnull ItemStack itemStack, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 199 */     return throwItem(ref, itemStack, 1.0F, componentAccessor);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\ItemUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */