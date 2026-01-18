/*    */ package com.hypixel.hytale.server.npc.blackboard.view.combat;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class EntityRemoved
/*    */   extends HolderSystem<EntityStore>
/*    */ {
/*    */   private final ComponentType<EntityStore, CombatViewSystems.CombatData> combatDataComponentType;
/*    */   private final ResourceType<EntityStore, CombatViewSystems.CombatDataPool> dataPoolResourceType;
/*    */   
/*    */   public EntityRemoved(ComponentType<EntityStore, CombatViewSystems.CombatData> combatDataComponentType, ResourceType<EntityStore, CombatViewSystems.CombatDataPool> dataPoolResourceType) {
/* 56 */     this.combatDataComponentType = combatDataComponentType;
/* 57 */     this.dataPoolResourceType = dataPoolResourceType;
/*    */   }
/*    */ 
/*    */   
/*    */   public Query<EntityStore> getQuery() {
/* 62 */     return (Query)this.combatDataComponentType;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {
/* 72 */     CombatViewSystems.CombatData combatData = (CombatViewSystems.CombatData)holder.getComponent(this.combatDataComponentType);
/* 73 */     CombatViewSystems.CombatDataPool dataPool = (CombatViewSystems.CombatDataPool)store.getResource(this.dataPoolResourceType);
/* 74 */     CombatViewSystems.clearCombatData(combatData, dataPool);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\view\combat\CombatViewSystems$EntityRemoved.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */