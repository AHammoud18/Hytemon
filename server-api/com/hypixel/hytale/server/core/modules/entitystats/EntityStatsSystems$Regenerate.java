/*     */ package com.hypixel.hytale.server.core.modules.entitystats;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.Invulnerable;
/*     */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.time.Instant;
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
/*     */ public class Regenerate<EntityType extends LivingEntity>
/*     */   extends EntityTickingSystem<EntityStore>
/*     */   implements EntityStatsSystems.StatModifyingSystem
/*     */ {
/*     */   private final ComponentType<EntityStore, EntityStatMap> componentType;
/*     */   private final ComponentType<EntityStore, EntityType> entityTypeComponent;
/*     */   private final Query<EntityStore> query;
/*     */   
/*     */   public Regenerate(ComponentType<EntityStore, EntityStatMap> componentType, ComponentType<EntityStore, EntityType> entityTypeComponent) {
/*  80 */     this.componentType = componentType;
/*  81 */     this.entityTypeComponent = entityTypeComponent;
/*     */     
/*  83 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)componentType, (Query)entityTypeComponent });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  90 */     return this.query;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 101 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/* 102 */     EntityStatMap map = (EntityStatMap)archetypeChunk.getComponent(index, this.componentType);
/* 103 */     assert map != null;
/*     */     
/* 105 */     Instant now = ((TimeResource)store.getResource(TimeResource.getResourceType())).getNow();
/* 106 */     int size = map.size();
/*     */     
/* 108 */     if (map.tempRegenerationValues.length < size) {
/* 109 */       map.tempRegenerationValues = new float[size];
/*     */     }
/*     */     
/* 112 */     for (int statIndex = 1; statIndex < size; statIndex++) {
/* 113 */       EntityStatValue value = map.get(statIndex);
/* 114 */       if (value != null) {
/* 115 */         map.tempRegenerationValues[statIndex] = 0.0F;
/*     */         
/* 117 */         RegeneratingValue[] regenerating = value.getRegeneratingValues();
/* 118 */         if (regenerating != null)
/* 119 */           for (RegeneratingValue regeneratingValue : regenerating) {
/* 120 */             if ((regeneratingValue.getRegenerating().getAmount() > 0.0F) ? (
/* 121 */               value.get() >= value.getMax()) : (
/*     */               
/* 123 */               value.get() <= value.getMin()))
/*     */             {
/* 125 */               map.tempRegenerationValues[statIndex] = map.tempRegenerationValues[statIndex] + regeneratingValue.regenerate((ComponentAccessor<EntityStore>)commandBuffer, ref, now, dt, value, map.tempRegenerationValues[statIndex]);
/*     */             }
/*     */           }  
/*     */       } 
/*     */     } 
/* 130 */     LivingEntity livingEntity = (LivingEntity)archetypeChunk.getComponent(index, this.entityTypeComponent);
/* 131 */     assert livingEntity != null;
/*     */     
/* 133 */     ItemContainer armorContainer = livingEntity.getInventory().getArmor();
/* 134 */     short armorContainerCapacity = armorContainer.getCapacity(); short i;
/* 135 */     for (i = 0; i < armorContainerCapacity; i = (short)(i + 1)) {
/* 136 */       ItemStack itemStack = armorContainer.getItemStack(i);
/* 137 */       if (!ItemStack.isEmpty(itemStack)) {
/*     */         
/* 139 */         Item item = itemStack.getItem();
/* 140 */         if (item.getArmor() != null && item.getArmor().getRegeneratingValues() != null && 
/* 141 */           !item.getArmor().getRegeneratingValues().isEmpty())
/* 142 */           for (int k = 1; k < size; k++) {
/* 143 */             EntityStatValue value = map.get(k);
/* 144 */             if (value != null) {
/*     */               
/* 146 */               List<RegeneratingValue> regenValues = (List<RegeneratingValue>)item.getArmor().getRegeneratingValues().get(k);
/* 147 */               if (regenValues != null && !regenValues.isEmpty())
/*     */               {
/* 149 */                 for (RegeneratingValue regeneratingValue : regenValues) {
/* 150 */                   if ((regeneratingValue.getRegenerating().getAmount() > 0.0F) ? (
/* 151 */                     value.get() >= value.getMax()) : (
/*     */                     
/* 153 */                     value.get() <= value.getMin()))
/*     */                     continue; 
/* 155 */                   map.tempRegenerationValues[k] = map.tempRegenerationValues[k] + regeneratingValue.regenerate((ComponentAccessor<EntityStore>)commandBuffer, ref, now, dt, value, map.tempRegenerationValues[k]);
/*     */                 }  } 
/*     */             } 
/*     */           }  
/*     */       } 
/* 160 */     }  for (int j = 1; j < size; j++) {
/* 161 */       EntityStatValue value = map.get(j);
/* 162 */       if (value != null) {
/* 163 */         float amount = map.tempRegenerationValues[j];
/*     */         
/* 165 */         boolean invulnerable = commandBuffer.getArchetype(ref).contains(Invulnerable.getComponentType());
/* 166 */         if (amount < 0.0F && !value.getIgnoreInvulnerability() && invulnerable) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 171 */         if (amount != 0.0F) map.addStatValue(j, amount); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entitystats\EntityStatsSystems$Regenerate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */