/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.selector;
/*     */ 
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.function.consumer.TriIntConsumer;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector4d;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.List;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Selector
/*     */ {
/*     */   static {
/*  30 */     if (null.$assertionsDisabled);
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
/*     */   static void selectNearbyBlocks(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull Ref<EntityStore> attackerRef, double range, @Nonnull TriIntConsumer consumer) {
/*  45 */     TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(attackerRef, TransformComponent.getComponentType());
/*  46 */     if (!null.$assertionsDisabled && transformComponent == null) throw new AssertionError();
/*     */     
/*  48 */     ModelComponent modelComponent = (ModelComponent)commandBuffer.getComponent(attackerRef, ModelComponent.getComponentType());
/*  49 */     if (!null.$assertionsDisabled && modelComponent == null) throw new AssertionError();
/*     */     
/*  51 */     Vector3d position = transformComponent.getPosition();
/*  52 */     Model model = modelComponent.getModel();
/*     */     
/*  54 */     selectNearbyBlocks(position.x, position.y + model.getEyeHeight(attackerRef, (ComponentAccessor)commandBuffer), position.z, range, consumer);
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
/*     */   static void selectNearbyBlocks(@Nonnull Vector3d position, double range, @Nonnull TriIntConsumer consumer) {
/*  66 */     selectNearbyBlocks(position.x, position.y, position.z, range, consumer);
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
/*     */   static void selectNearbyBlocks(double xPos, double yPos, double zPos, double range, @Nonnull TriIntConsumer consumer) {
/*  80 */     int xStart = MathUtil.floor(xPos - range);
/*  81 */     int yStart = MathUtil.floor(yPos - range);
/*  82 */     int zStart = MathUtil.floor(zPos - range);
/*     */     
/*  84 */     int xEnd = MathUtil.floor(xPos + range);
/*  85 */     int yEnd = MathUtil.floor(yPos + range);
/*  86 */     int zEnd = MathUtil.floor(zPos + range);
/*     */     
/*  88 */     for (int x = xStart; x < xEnd; x++) {
/*  89 */       for (int y = yStart; y < yEnd; y++) {
/*  90 */         for (int z = zStart; z < zEnd; z++) {
/*  91 */           consumer.accept(x, y, z);
/*     */         }
/*     */       } 
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
/*     */   static void selectNearbyEntities(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull Ref<EntityStore> attacker, double range, @Nonnull Consumer<Ref<EntityStore>> consumer, @Nonnull Predicate<Ref<EntityStore>> filter) {
/* 112 */     TransformComponent transformComponent = (TransformComponent)commandBuffer.getComponent(attacker, TransformComponent.getComponentType());
/* 113 */     if (!null.$assertionsDisabled && transformComponent == null) throw new AssertionError();
/*     */     
/* 115 */     ModelComponent modelComponent = (ModelComponent)commandBuffer.getComponent(attacker, ModelComponent.getComponentType());
/* 116 */     if (!null.$assertionsDisabled && modelComponent == null) throw new AssertionError();
/*     */     
/* 118 */     Vector3d attackerPosition = transformComponent.getPosition();
/* 119 */     Model model = modelComponent.getModel();
/*     */ 
/*     */     
/* 122 */     Vector3d position = attackerPosition.clone().add(0.0D, model.getEyeHeight(attacker, (ComponentAccessor)commandBuffer), 0.0D);
/* 123 */     selectNearbyEntities((ComponentAccessor<EntityStore>)commandBuffer, position, range, consumer, filter);
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
/*     */   static void selectNearbyEntities(@Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Vector3d position, double range, @Nonnull Consumer<Ref<EntityStore>> consumer, @Nullable Predicate<Ref<EntityStore>> filter) {
/* 142 */     ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/*     */     
/* 144 */     SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)componentAccessor.getResource(EntityModule.get().getPlayerSpatialResourceType());
/* 145 */     playerSpatialResource.getSpatialStructure().collect(position, range, (List)results);
/*     */     
/* 147 */     SpatialResource<Ref<EntityStore>, EntityStore> entitySpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)componentAccessor.getResource(EntityModule.get().getEntitySpatialResourceType());
/* 148 */     entitySpatialResource.getSpatialStructure().collect(position, range, (List)results);
/*     */     
/* 150 */     SpatialResource<Ref<EntityStore>, EntityStore> itemSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)componentAccessor.getResource(EntityModule.get().getItemSpatialResourceType());
/* 151 */     itemSpatialResource.getSpatialStructure().collect(position, range, (List)results);
/*     */     
/* 153 */     for (ObjectListIterator<Ref<EntityStore>> objectListIterator = results.iterator(); objectListIterator.hasNext(); ) { Ref<EntityStore> ref = objectListIterator.next();
/* 154 */       if (ref == null || !ref.isValid()) {
/*     */         continue;
/*     */       }
/* 157 */       if (filter != null && !filter.test(ref)) {
/*     */         continue;
/*     */       }
/* 160 */       consumer.accept(ref); }
/*     */   
/*     */   }
/*     */   
/*     */   void tick(@Nonnull CommandBuffer<EntityStore> paramCommandBuffer, @Nonnull Ref<EntityStore> paramRef, float paramFloat1, float paramFloat2);
/*     */   
/*     */   void selectTargetEntities(@Nonnull CommandBuffer<EntityStore> paramCommandBuffer, @Nonnull Ref<EntityStore> paramRef, BiConsumer<Ref<EntityStore>, Vector4d> paramBiConsumer, Predicate<Ref<EntityStore>> paramPredicate);
/*     */   
/*     */   void selectTargetBlocks(@Nonnull CommandBuffer<EntityStore> paramCommandBuffer, @Nonnull Ref<EntityStore> paramRef, @Nonnull TriIntConsumer paramTriIntConsumer);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\selector\Selector.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */