/*     */ package com.hypixel.hytale.server.core.modules.entitystats;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.dependency.SystemTypeDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.protocol.EntityStatUpdate;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionChain;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.modules.entity.AllLegacyLivingEntityTypesQuery;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.floats.FloatList;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class Changes
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   private final ComponentType<EntityStore, EntityStatMap> componentType;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/* 181 */   private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemDependency(Order.BEFORE, EntityStatsSystems.EntityTrackerUpdate.class), new SystemTypeDependency(Order.AFTER, 
/*     */         
/* 183 */         EntityStatsModule.get().getStatModifyingSystemType()));
/*     */ 
/*     */   
/*     */   public Changes(ComponentType<EntityStore, EntityStatMap> componentType) {
/* 187 */     this.componentType = componentType;
/* 188 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)componentType, (Query)InteractionModule.get().getInteractionManagerComponent(), (Query)AllLegacyLivingEntityTypesQuery.INSTANCE });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 194 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 200 */     return this.query;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 206 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 211 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*     */     
/* 213 */     EntityStatMap entityStatMapComponent = (EntityStatMap)archetypeChunk.getComponent(index, this.componentType);
/* 214 */     assert entityStatMapComponent != null;
/*     */     
/* 216 */     InteractionManager interactionManagerComponent = (InteractionManager)archetypeChunk.getComponent(index, InteractionModule.get().getInteractionManagerComponent());
/* 217 */     assert interactionManagerComponent != null;
/*     */     
/* 219 */     boolean isDead = archetypeChunk.getArchetype().contains(DeathComponent.getComponentType());
/*     */     
/* 221 */     Int2ObjectMap<List<EntityStatUpdate>> statChanges = entityStatMapComponent.getSelfUpdates();
/* 222 */     Int2ObjectMap<FloatList> statValues = entityStatMapComponent.getSelfStatValues();
/*     */     
/* 224 */     for (int statIndex = 0; statIndex < entityStatMapComponent.size(); statIndex++) {
/* 225 */       List<EntityStatUpdate> updates = (List<EntityStatUpdate>)statChanges.get(statIndex);
/* 226 */       if (updates != null && !updates.isEmpty()) {
/*     */         
/* 228 */         FloatList statChangeList = (FloatList)statValues.get(statIndex);
/*     */         
/* 230 */         EntityStatValue entityStatValue = entityStatMapComponent.get(statIndex);
/* 231 */         if (entityStatValue != null) {
/*     */           
/* 233 */           EntityStatType entityStatType = (EntityStatType)EntityStatType.getAssetMap().getAsset(statIndex);
/*     */           
/* 235 */           for (int i = 0; i < updates.size(); i++) {
/* 236 */             EntityStatUpdate update = updates.get(i);
/* 237 */             float statPrevious = statChangeList.getFloat(i * 2);
/* 238 */             float statValue = statChangeList.getFloat(i * 2 + 1);
/*     */ 
/*     */             
/* 241 */             if (testMaxValue(statValue, statPrevious, entityStatValue, entityStatType.getMaxValueEffects())) {
/* 242 */               runInteractions(ref, interactionManagerComponent, entityStatType.getMaxValueEffects(), (ComponentAccessor<EntityStore>)commandBuffer);
/*     */             }
/*     */ 
/*     */             
/* 246 */             if (testMinValue(statValue, statPrevious, entityStatValue, entityStatType.getMinValueEffects())) {
/* 247 */               runInteractions(ref, interactionManagerComponent, entityStatType.getMinValueEffects(), (ComponentAccessor<EntityStore>)commandBuffer);
/*     */             }
/*     */             
/* 250 */             if (!isDead && statIndex == DefaultEntityStatTypes.getHealth() && update.value <= 0.0F)
/*     */             {
/* 252 */               if (statValue <= entityStatValue.getMin()) {
/*     */ 
/*     */                 
/* 255 */                 DeathComponent.tryAddComponent(commandBuffer, archetypeChunk.getReferenceTo(index), new Damage(Damage.NULL_SOURCE, DamageCause.COMMAND, 0.0F));
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 260 */                 isDead = true;
/*     */               }  } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } private static boolean testMaxValue(float value, float previousValue, @Nonnull EntityStatValue stat, @Nullable EntityStatType.EntityStatEffects valueEffects) {
/* 267 */     if (valueEffects == null) return false;
/*     */ 
/*     */     
/* 270 */     if (valueEffects.triggerAtZero() && stat.getMax() > 0.0F) return (previousValue < 0.0F && value >= 0.0F);
/*     */ 
/*     */     
/* 273 */     return (previousValue != stat.getMax() && value == stat.getMax());
/*     */   }
/*     */   
/*     */   private static boolean testMinValue(float value, float previousValue, @Nonnull EntityStatValue stat, @Nullable EntityStatType.EntityStatEffects valueEffects) {
/* 277 */     if (valueEffects == null) return false;
/*     */ 
/*     */     
/* 280 */     if (valueEffects.triggerAtZero() && stat.getMin() < 0.0F) return (previousValue > 0.0F && value < 0.0F);
/*     */ 
/*     */     
/* 283 */     return (previousValue != stat.getMin() && value == stat.getMin());
/*     */   }
/*     */   
/*     */   private static void runInteractions(@Nonnull Ref<EntityStore> ref, @Nonnull InteractionManager interactionManager, @Nullable EntityStatType.EntityStatEffects valueEffects, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 287 */     if (valueEffects == null)
/*     */       return; 
/* 289 */     String interactions = valueEffects.getInteractions();
/* 290 */     if (interactions == null)
/*     */       return; 
/* 292 */     InteractionContext context = InteractionContext.forInteraction(interactionManager, ref, InteractionType.EntityStatEffect, componentAccessor);
/* 293 */     InteractionChain chain = interactionManager.initChain(InteractionType.EntityStatEffect, context, RootInteraction.getRootInteractionOrUnknown(interactions), true);
/* 294 */     interactionManager.queueExecuteChain(chain);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entitystats\EntityStatsSystems$Changes.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */