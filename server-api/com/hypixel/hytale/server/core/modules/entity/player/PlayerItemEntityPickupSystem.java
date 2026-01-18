/*     */ package com.hypixel.hytale.server.core.modules.entity.player;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.OrderPriority;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.spatial.SpatialStructure;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionChain;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction;
/*     */ import com.hypixel.hytale.server.core.modules.entity.DespawnComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.Interactable;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.item.PickupItemComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.item.PreventPickup;
/*     */ import com.hypixel.hytale.server.core.modules.entity.system.PlayerSpatialSystem;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerItemEntityPickupSystem
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, ItemComponent> itemComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, Player> playerComponentType;
/*     */   @Nonnull
/*     */   private final ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> playerSpatialComponent;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, InteractionManager> interactionManagerType;
/*     */   @Nonnull
/*     */   private final Set<Dependency<EntityStore>> dependencies;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   
/*     */   public PlayerItemEntityPickupSystem(@Nonnull ComponentType<EntityStore, ItemComponent> itemComponentType, @Nonnull ComponentType<EntityStore, Player> playerComponentType, @Nonnull ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> playerSpatialComponent) {
/*  84 */     this.itemComponentType = itemComponentType;
/*  85 */     this.playerComponentType = playerComponentType;
/*  86 */     this.interactionManagerType = InteractionModule.get().getInteractionManagerComponent();
/*     */ 
/*     */     
/*  89 */     this.playerSpatialComponent = playerSpatialComponent;
/*  90 */     this.dependencies = Set.of(new SystemDependency(Order.AFTER, PlayerSpatialSystem.class, OrderPriority.CLOSEST));
/*  91 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)itemComponentType, (Query)Query.not((Query)Interactable.getComponentType()), (Query)Query.not((Query)PickupItemComponent.getComponentType()), (Query)Query.not((Query)PreventPickup.getComponentType()) });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/*  97 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 103 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 117 */     Ref<EntityStore> itemRef = archetypeChunk.getReferenceTo(index);
/* 118 */     ItemComponent itemComponent = (ItemComponent)archetypeChunk.getComponent(index, this.itemComponentType);
/* 119 */     assert itemComponent != null;
/*     */     
/* 121 */     if (!itemComponent.pollPickupDelay(dt)) {
/*     */       return;
/*     */     }
/* 124 */     if (!itemComponent.pollPickupThrottle(dt))
/*     */       return; 
/* 126 */     TimeResource timeResource = (TimeResource)commandBuffer.getResource(TimeResource.getResourceType());
/* 127 */     SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(this.playerSpatialComponent);
/* 128 */     SpatialStructure<Ref<EntityStore>> spatialStructure = playerSpatialResource.getSpatialStructure();
/* 129 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/* 130 */     assert transformComponent != null;
/*     */     
/* 132 */     Vector3d itemEntityPosition = transformComponent.getPosition();
/* 133 */     DespawnComponent despawnComponent = (DespawnComponent)archetypeChunk.getComponent(index, DespawnComponent.getComponentType());
/*     */     
/* 135 */     float pickupRadius = itemComponent.getPickupRadius((ComponentAccessor)commandBuffer);
/*     */     
/* 137 */     ItemStack itemStack = itemComponent.getItemStack();
/* 138 */     Item item = itemStack.getItem();
/* 139 */     String interactions = (String)item.getInteractions().get(InteractionType.Pickup);
/* 140 */     if (interactions != null) {
/* 141 */       Ref<EntityStore> targetRef = (Ref<EntityStore>)spatialStructure.closest(itemEntityPosition);
/* 142 */       if (targetRef == null)
/*     */         return; 
/* 144 */       TransformComponent targetTransformComponent = (TransformComponent)store.getComponent(targetRef, TransformComponent.getComponentType());
/* 145 */       assert targetTransformComponent != null;
/*     */       
/* 147 */       InteractionManager targetInteractionManagerComponent = (InteractionManager)store.getComponent(targetRef, this.interactionManagerType);
/* 148 */       assert targetInteractionManagerComponent != null;
/*     */       
/* 150 */       Vector3d targetPosition = targetTransformComponent.getPosition();
/*     */ 
/*     */       
/* 153 */       double distance = targetPosition.distanceTo(itemEntityPosition);
/* 154 */       if (distance > pickupRadius)
/*     */         return; 
/* 156 */       Ref<EntityStore> reference = archetypeChunk.getReferenceTo(index);
/* 157 */       commandBuffer.run(_store -> {
/*     */             InteractionContext context = InteractionContext.forInteraction(targetInteractionManagerComponent, targetRef, InteractionType.Pickup, (ComponentAccessor)commandBuffer);
/*     */             
/*     */             InteractionChain chain = targetInteractionManagerComponent.initChain(InteractionType.Pickup, context, RootInteraction.getRootInteractionOrUnknown(interactions), false);
/*     */             context.getMetaStore().putMetaObject(Interaction.TARGET_ENTITY, reference);
/*     */             targetInteractionManagerComponent.executeChain(reference, commandBuffer, chain);
/*     */             _store.removeEntity(reference, RemoveReason.REMOVE);
/*     */           });
/*     */       return;
/*     */     } 
/* 167 */     ObjectList<Ref<EntityStore>> targetPlayerRefs = SpatialResource.getThreadLocalReferenceList();
/* 168 */     spatialStructure.ordered(itemEntityPosition, pickupRadius, (List)targetPlayerRefs);
/*     */     
/* 170 */     for (ObjectListIterator<Ref<EntityStore>> objectListIterator = targetPlayerRefs.iterator(); objectListIterator.hasNext(); ) { Ref<EntityStore> targetPlayerRef = objectListIterator.next();
/* 171 */       if (store.getArchetype(targetPlayerRef).contains(DeathComponent.getComponentType())) {
/*     */         continue;
/*     */       }
/*     */       
/* 175 */       Player playerComponent = (Player)store.getComponent(targetPlayerRef, this.playerComponentType);
/* 176 */       assert playerComponent != null;
/*     */       
/* 178 */       PlayerSettings playerSettings = (PlayerSettings)commandBuffer.getComponent(targetPlayerRef, PlayerSettings.getComponentType());
/* 179 */       if (playerSettings == null) playerSettings = PlayerSettings.defaults();
/*     */       
/* 181 */       ItemContainer itemContainer = playerComponent.getInventory().getContainerForItemPickup(item, playerSettings);
/*     */       
/* 183 */       ItemStackTransaction transaction = itemContainer.addItemStack(itemStack);
/* 184 */       ItemStack remainder = transaction.getRemainder();
/*     */ 
/*     */       
/* 187 */       if (ItemStack.isEmpty(remainder)) {
/* 188 */         itemComponent.setRemovedByPlayerPickup(true);
/* 189 */         commandBuffer.removeEntity(itemRef, RemoveReason.REMOVE);
/* 190 */         playerComponent.notifyPickupItem(targetPlayerRef, itemStack, itemEntityPosition, (ComponentAccessor)commandBuffer);
/*     */ 
/*     */         
/* 193 */         Holder<EntityStore> holder = ItemComponent.generatePickedUpItem(itemRef, (ComponentAccessor)commandBuffer, targetPlayerRef, itemEntityPosition);
/* 194 */         commandBuffer.addEntity(holder, AddReason.SPAWN);
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 199 */       if (remainder.equals(itemStack))
/*     */         continue; 
/* 201 */       int quantity = itemStack.getQuantity() - remainder.getQuantity();
/* 202 */       itemStack = remainder;
/* 203 */       itemComponent.setItemStack(remainder);
/* 204 */       float newLifetime = itemComponent.computeLifetimeSeconds((ComponentAccessor)commandBuffer);
/* 205 */       DespawnComponent.trySetDespawn(commandBuffer, timeResource, itemRef, despawnComponent, Float.valueOf(newLifetime));
/*     */ 
/*     */       
/* 208 */       Holder<EntityStore> pickupItemHolder = ItemComponent.generatePickedUpItem(itemRef, (ComponentAccessor)commandBuffer, targetPlayerRef, itemEntityPosition);
/* 209 */       commandBuffer.addEntity(pickupItemHolder, AddReason.SPAWN);
/*     */ 
/*     */       
/* 212 */       if (quantity > 0)
/* 213 */         playerComponent.notifyPickupItem(targetPlayerRef, itemStack.withQuantity(quantity), itemEntityPosition, (ComponentAccessor)commandBuffer);  }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerItemEntityPickupSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */