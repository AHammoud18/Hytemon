/*    */ package com.hypixel.hytale.builtin.npccombatactionevaluator;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.npccombatactionevaluator.config.CombatBalanceAsset;
/*    */ import com.hypixel.hytale.builtin.npccombatactionevaluator.evaluator.CombatActionEvaluator;
/*    */ import com.hypixel.hytale.builtin.npccombatactionevaluator.memory.TargetMemory;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.dependency.Dependency;
/*    */ import com.hypixel.hytale.component.dependency.Order;
/*    */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.config.balancing.BalanceAsset;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.systems.BalancingInitialisationSystem;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OnAdded
/*    */   extends HolderSystem<EntityStore>
/*    */ {
/*    */   @Nullable
/*    */   private final ComponentType<EntityStore, NPCEntity> componentType;
/*    */   private final ComponentType<EntityStore, CombatActionEvaluatorSystems.CombatConstructionData> combatConstructionDataComponentType;
/*    */   @Nonnull
/*    */   private final Set<Dependency<EntityStore>> dependencies;
/*    */   @Nonnull
/*    */   private final Query<EntityStore> query;
/*    */   
/*    */   public OnAdded(ComponentType<EntityStore, CombatActionEvaluatorSystems.CombatConstructionData> combatConstructionDataComponentType) {
/* 51 */     this.componentType = NPCEntity.getComponentType();
/* 52 */     this.combatConstructionDataComponentType = combatConstructionDataComponentType;
/* 53 */     this.dependencies = Set.of(new SystemDependency(Order.AFTER, BalancingInitialisationSystem.class));
/* 54 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)combatConstructionDataComponentType, (Query)combatConstructionDataComponentType });
/*    */   }
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/*    */     CombatBalanceAsset combatBalance;
/* 59 */     Role role = ((NPCEntity)holder.getComponent(this.componentType)).getRole();
/* 60 */     if (role.getBalanceAsset() == null)
/*    */       return; 
/* 62 */     BalanceAsset balancingAsset = (BalanceAsset)BalanceAsset.getAssetMap().getAsset(role.getBalanceAsset());
/* 63 */     if (balancingAsset instanceof CombatBalanceAsset) { combatBalance = (CombatBalanceAsset)balancingAsset; }
/*    */     else
/*    */     { return; }
/*    */     
/* 67 */     CombatActionEvaluatorSystems.CombatConstructionData constructionData = (CombatActionEvaluatorSystems.CombatConstructionData)holder.getComponent(this.combatConstructionDataComponentType);
/* 68 */     CombatActionEvaluator evaluator = new CombatActionEvaluator(role, combatBalance.getEvaluatorConfig(), constructionData);
/* 69 */     evaluator.setupNPC(holder);
/* 70 */     Objects.requireNonNull(evaluator); role.getPositionCache().addExternalPositionCacheRegistration(evaluator::setupNPC);
/*    */ 
/*    */ 
/*    */     
/* 74 */     holder.putComponent(TargetMemory.getComponentType(), (Component)new TargetMemory(combatBalance.getTargetMemoryDuration()));
/* 75 */     holder.putComponent(CombatActionEvaluator.getComponentType(), (Component)evaluator);
/* 76 */     holder.ensureComponent(InteractionModule.get().getChainingDataComponent());
/* 77 */     holder.removeComponent(this.combatConstructionDataComponentType);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 87 */     return this.query;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Set<Dependency<EntityStore>> getDependencies() {
/* 93 */     return this.dependencies;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npccombatactionevaluator\CombatActionEvaluatorSystems$OnAdded.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */