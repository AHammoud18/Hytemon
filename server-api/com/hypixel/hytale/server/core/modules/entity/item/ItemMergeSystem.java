/*     */ package com.hypixel.hytale.server.core.modules.entity.item;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.ColorLight;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.modules.entity.DespawnComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.DynamicLight;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.Interactable;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.List;
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
/*     */ public class ItemMergeSystem
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   public static final float RADIUS = 2.0F;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, ItemComponent> itemComponentComponentType;
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, Interactable> interactableComponentType;
/*     */   @Nonnull
/*     */   private final ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> itemSpatialComponent;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   
/*     */   public ItemMergeSystem(@Nonnull ComponentType<EntityStore, ItemComponent> itemComponentComponentType, @Nonnull ComponentType<EntityStore, Interactable> interactableComponentType, @Nonnull ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> itemSpatialComponent) {
/*  57 */     this.itemComponentComponentType = itemComponentComponentType;
/*  58 */     this.itemSpatialComponent = itemSpatialComponent;
/*  59 */     this.interactableComponentType = interactableComponentType;
/*     */ 
/*     */     
/*  62 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)itemComponentComponentType, (Query)Query.not((Query)interactableComponentType), (Query)Query.not((Query)PreventItemMerging.getComponentType()) });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  68 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  73 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  78 */     ItemComponent itemComponent = (ItemComponent)archetypeChunk.getComponent(index, this.itemComponentComponentType);
/*  79 */     assert itemComponent != null;
/*  80 */     ItemStack itemStack = itemComponent.getItemStack();
/*     */ 
/*     */     
/*  83 */     if (itemStack == null)
/*     */       return; 
/*  85 */     Item itemAsset = itemStack.getItem();
/*  86 */     int maxStack = itemAsset.getMaxStack();
/*     */     
/*  88 */     if (maxStack <= 1 || itemStack.getQuantity() >= maxStack)
/*     */       return; 
/*  90 */     if (!itemComponent.pollMergeDelay(dt))
/*     */       return; 
/*  92 */     SpatialResource<Ref<EntityStore>, EntityStore> spatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)store.getResource(this.itemSpatialComponent);
/*  93 */     TimeResource timeResource = (TimeResource)store.getResource(TimeResource.getResourceType());
/*     */     
/*  95 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/*  96 */     assert transformComponent != null;
/*     */     
/*  98 */     Vector3d position = transformComponent.getPosition();
/*     */ 
/*     */     
/* 101 */     ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/* 102 */     spatialResource.getSpatialStructure().ordered(position, 2.0D, (List)results);
/*     */     
/* 104 */     Ref<EntityStore> reference = archetypeChunk.getReferenceTo(index);
/*     */     
/* 106 */     for (ObjectListIterator<Ref<EntityStore>> objectListIterator = results.iterator(); objectListIterator.hasNext(); ) { Ref<EntityStore> otherReference = objectListIterator.next();
/* 107 */       if (!otherReference.isValid() || otherReference.equals(reference))
/*     */         continue; 
/* 109 */       ItemComponent otherItemComponent = (ItemComponent)store.getComponent(otherReference, this.itemComponentComponentType);
/* 110 */       assert otherItemComponent != null;
/*     */ 
/*     */       
/* 113 */       ItemStack otherItemStack = otherItemComponent.getItemStack();
/* 114 */       if (otherItemStack == null) {
/*     */         continue;
/*     */       }
/* 117 */       if (commandBuffer.getArchetype(otherReference).contains(this.interactableComponentType))
/*     */         continue; 
/* 119 */       if (!itemStack.isStackableWith(otherItemStack))
/*     */         continue; 
/* 121 */       int otherQuantity = otherItemStack.getQuantity();
/* 122 */       if (otherQuantity >= maxStack) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 127 */       int combinedTotal = itemStack.getQuantity() + otherQuantity;
/* 128 */       if (combinedTotal <= maxStack) {
/* 129 */         commandBuffer.removeEntity(otherReference, RemoveReason.REMOVE);
/*     */ 
/*     */ 
/*     */         
/* 133 */         otherItemComponent.setItemStack(null);
/*     */         
/* 135 */         itemStack = itemStack.withQuantity(combinedTotal);
/*     */       } else {
/* 137 */         otherItemComponent.setItemStack(itemStack.withQuantity(combinedTotal - maxStack));
/* 138 */         float f = otherItemComponent.computeLifetimeSeconds((ComponentAccessor<EntityStore>)commandBuffer);
/* 139 */         DespawnComponent.trySetDespawn(commandBuffer, timeResource, otherReference, (DespawnComponent)commandBuffer.getComponent(otherReference, DespawnComponent.getComponentType()), Float.valueOf(f));
/*     */         
/* 141 */         ColorLight otherItemDynamicLight = otherItemComponent.computeDynamicLight();
/* 142 */         if (otherItemDynamicLight != null) {
/* 143 */           DynamicLight otherDynamicLightComponent = (DynamicLight)commandBuffer.getComponent(otherReference, DynamicLight.getComponentType());
/* 144 */           if (otherDynamicLightComponent != null) {
/* 145 */             otherDynamicLightComponent.setColorLight(otherItemDynamicLight);
/*     */           } else {
/* 147 */             commandBuffer.putComponent(otherReference, DynamicLight.getComponentType(), (Component)new DynamicLight(otherItemDynamicLight));
/*     */           } 
/*     */         } 
/*     */         
/* 151 */         itemStack = itemStack.withQuantity(maxStack);
/*     */       } 
/*     */       
/* 154 */       itemComponent.setItemStack(itemStack);
/*     */       
/* 156 */       float newLifetime = itemComponent.computeLifetimeSeconds((ComponentAccessor<EntityStore>)commandBuffer);
/* 157 */       DespawnComponent.trySetDespawn(commandBuffer, timeResource, reference, (DespawnComponent)archetypeChunk.getComponent(index, DespawnComponent.getComponentType()), Float.valueOf(newLifetime));
/*     */ 
/*     */       
/* 160 */       if (itemStack.getQuantity() >= maxStack)
/*     */         break;  }
/*     */   
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\item\ItemMergeSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */