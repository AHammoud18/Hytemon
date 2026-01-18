/*     */ package com.hypixel.hytale.server.core.modules.entity.stamina;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*     */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesSystems;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class StaminaSystems {
/*     */   public static class SprintStaminaEffectSystem extends EntityTickingSystem<EntityStore> {
/*  23 */     private final ComponentType<EntityStore, Player> playerComponentType = Player.getComponentType();
/*  24 */     private final ComponentType<EntityStore, EntityStatMap> entityStatMapComponentType = EntityStatMap.getComponentType();
/*  25 */     private final ComponentType<EntityStore, MovementStatesComponent> movementStatesComponentType = MovementStatesComponent.getComponentType();
/*  26 */     private final ResourceType<EntityStore, SprintStaminaRegenDelay> sprintRegenDelayResourceType = SprintStaminaRegenDelay.getResourceType();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  31 */     private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] { (Query)this.playerComponentType, (Query)this.entityStatMapComponentType, (Query)this.movementStatesComponentType });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  40 */     private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemDependency(Order.BEFORE, MovementStatesSystems.TickingSystem.class), new SystemDependency(Order.BEFORE, EntityStatsModule.PlayerRegenerateStatsSystem.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/*  48 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/*  54 */       return this.dependencies;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/*  60 */       if (!updateResource(store))
/*     */         return; 
/*  62 */       super.tick(dt, systemIndex, store);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  67 */       MovementStatesComponent movementStates = (MovementStatesComponent)archetypeChunk.getComponent(index, this.movementStatesComponentType);
/*     */ 
/*     */       
/*  70 */       if (!(movementStates.getMovementStates()).sprinting && (movementStates.getSentMovementStates()).sprinting) {
/*  71 */         SprintStaminaRegenDelay regenDelay = (SprintStaminaRegenDelay)store.getResource(this.sprintRegenDelayResourceType);
/*     */         
/*  73 */         EntityStatMap statMap = (EntityStatMap)archetypeChunk.getComponent(index, this.entityStatMapComponentType);
/*  74 */         EntityStatValue statValue = statMap.get(regenDelay.getIndex());
/*     */ 
/*     */         
/*  77 */         if (statValue != null && statValue.get() <= regenDelay.getValue())
/*     */           return; 
/*  79 */         statMap.setStatValue(regenDelay.getIndex(), regenDelay.getValue());
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean updateResource(@Nonnull Store<EntityStore> store) {
/*  91 */       SprintStaminaRegenDelay resource = (SprintStaminaRegenDelay)store.getResource(this.sprintRegenDelayResourceType);
/*  92 */       if (resource.validate()) return resource.hasDelay();
/*     */       
/*  94 */       GameplayConfig gameplayConfig = ((EntityStore)store.getExternalData()).getWorld().getGameplayConfig();
/*  95 */       StaminaGameplayConfig staminaConfig = (StaminaGameplayConfig)gameplayConfig.getPluginConfig().get(StaminaGameplayConfig.class);
/*  96 */       if (staminaConfig == null || staminaConfig.getSprintRegenDelay().getIndex() == Integer.MIN_VALUE) {
/*     */         
/*  98 */         resource.markEmpty();
/*  99 */         return false;
/*     */       } 
/*     */       
/* 102 */       StaminaGameplayConfig.SprintRegenDelayConfig regenDelay = staminaConfig.getSprintRegenDelay();
/* 103 */       resource.update(regenDelay.getIndex(), regenDelay.getValue());
/*     */       
/* 105 */       return resource.hasDelay();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\stamina\StaminaSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */