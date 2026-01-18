/*    */ package com.hypixel.hytale.server.npc.blackboard.view.combat;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.server.core.modules.entity.AllLegacyEntityTypesQuery;
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
/*    */ public class Ensure
/*    */   extends HolderSystem<EntityStore>
/*    */ {
/*    */   private final ComponentType<EntityStore, CombatViewSystems.CombatData> combatDataComponentType;
/*    */   
/*    */   public Ensure(ComponentType<EntityStore, CombatViewSystems.CombatData> combatDataComponentType) {
/* 32 */     this.combatDataComponentType = combatDataComponentType;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 38 */     return (Query<EntityStore>)AllLegacyEntityTypesQuery.INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 43 */     holder.ensureComponent(this.combatDataComponentType);
/*    */   }
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\view\combat\CombatViewSystems$Ensure.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */